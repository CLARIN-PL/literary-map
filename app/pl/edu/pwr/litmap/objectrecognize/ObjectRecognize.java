/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pwr.litmap.objectrecognize;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pl.edu.pwr.litmap.ccl.CclSAXStreamReader;
import pl.edu.pwr.litmap.ccl.Paragraph;
import pl.edu.pwr.litmap.exceptions.UnrecognizedWebserviceResponseException;
import pl.edu.pwr.litmap.relations.RelationType;
import pl.edu.pwr.litmap.textobjects.Text;
import pl.edu.pwr.litmap.webservices.WebserviceClient;
import pl.edu.pwr.litmap.webservices.php.PhpSerelWebserviceClient;
import pl.edu.pwr.litmap.webservices.wsdl.WsdlWebservicesListClient;
import pl.edu.pwr.services.serel.SerelServiceRs;
import play.api.libs.Codecs;
import play.cache.Cache;

/**
 *
 * @author wgawel
 */
public class ObjectRecognize {

    public Text parseText(String plainText) throws IOException {
        System.out.println("Start parse text - "+new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        System.out.println("Text length: "+plainText.length());
        System.out.println("Text SHA-1: "+Codecs.sha1(plainText));
        System.out.println("Text fragment: \""+plainText.substring(0, (plainText.length() >= 30 ? 30 : plainText.length()))+"(...)"+plainText.substring(plainText.length()-(plainText.length() >= 20 ? 20 : plainText.length()), plainText.length()-(plainText.length() >= 20 ? 20 : plainText.length())+(plainText.length() >= 20 ? 20 : plainText.length()))+"\"");
        
    	String cacheKey = "controller.ObjectRecognize.parseText."+Codecs.sha1(plainText);
    	Text resultText = (Text) Cache.get(cacheKey);
    	
        System.out.println("Exists in cache: "+((resultText == null) ? "no." : "yes."));
    	
    	if (resultText == null) {
	        
    		long timeStart = Calendar.getInstance().getTimeInMillis();

            System.out.println("Load WsdlWebservicesListClient...");
//	        WebserviceClient webserviceClient = new PhpWebserviceClient();
//	        WebserviceClient webserviceClient = new WsdlWebservicesListClient();
            System.out.println("Process plainText in WsdlWebservicesListClient...");
			String result = SerelServiceRs.process(plainText);
			long totalTime = Calendar.getInstance().getTimeInMillis() - timeStart;
	        System.out.println("Total time waiting for webservices result: "+totalTime+" ms.");
	        
	       //System.out.println("Returned XML: \n"+result);
	        
	        resultText = getText(result);

	        Cache.set(cacheKey, resultText);
    	}
    	return resultText;
    }
    
    public static Text getText(String annotatedXml) throws UnrecognizedWebserviceResponseException, IOException {
        Text text = new Text();
        
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(annotatedXml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ObjectRecognize.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CclSAXStreamReader cssr = new CclSAXStreamReader(is);
        
        while (cssr.paragraphReady()) {
            try {
                Paragraph paragraph = cssr.readParagraph();
                text.addSentences(paragraph.getSentences());
            } catch (Exception ex) {
                Logger.getLogger(ObjectRecognize.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // read relations
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // ignore dtd
        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        try {
			dbf.setFeature("http://xml.org/sax/features/namespaces", false);
	        dbf.setFeature("http://xml.org/sax/features/validation", false);
	        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        } catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
        DocumentBuilder db;
        org.w3c.dom.Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(
					new InputSource(
							new StringReader(annotatedXml)));
		} catch (SAXException | ParserConfigurationException ex) {
			throw new UnrecognizedWebserviceResponseException(ex.toString());
		}
        if(doc == null) {
        	throw new UnrecognizedWebserviceResponseException("Expecting Xml data");
        } else {
        	try {
	            doc.getDocumentElement().normalize();
				Element docEle = doc.getDocumentElement();
	        	NodeList relations = docEle.getElementsByTagName("relations");

				if (relations.item(0).getNodeType() != Node.ELEMENT_NODE) {
                	throw new UnrecognizedWebserviceResponseException("XML: Tag 'relations' is not element node.");
                }

                Element relationsElement = (Element) relations.item(0);
	        	NodeList relationsList = relationsElement.getElementsByTagName("rel");
	        	
	        	if (relationsList != null && relationsList.getLength() > 0) {
	                for (int i = 0; i < relationsList.getLength(); i++) {
	
	                    Node nodeRelation = relationsList.item(i);
	
	                    if (nodeRelation.getNodeType() == Node.ELEMENT_NODE) {
	                        Element nodeRelationElement = (Element) nodeRelation;
	                        String relationName = nodeRelationElement.getAttribute("name");
	                        RelationType relationType;
	                        if (relationName.equals(RelationType.LOCATION.toString().toLowerCase())) {
	                        	relationType = RelationType.LOCATION;
	                        } else if (relationName.equals(RelationType.ORIGIN.toString().toLowerCase())) {
	                        	relationType = RelationType.ORIGIN;
	                        } else {
	                        	// TODO: Dodać obsługę innych relacji
	                        	relationType = RelationType.OTHER;
	                        	continue;
	                        	//throw new UnrecognizedWebserviceResponseException("Unknown relation name = "+relationName);
	                        }
	                        
	                        NodeList nodeFrom = nodeRelationElement.getElementsByTagName("from");
	                        Element e_from = (Element) nodeFrom.item(0);
	                        String from_sentence_id = e_from.getAttribute("sent");
	                        String from_chan = e_from.getAttribute("chan");
	                        int from_ann_nr;
	                        try {
	                        	from_ann_nr = Integer.parseInt(e_from.getTextContent());
	                        } catch (NumberFormatException nfe) {
	                        	throw new UnrecognizedWebserviceResponseException("Invalid text content of element (FROM: ann nr). Exception: "+nfe+". Value = "+(e_from.getNodeValue() == null ? "null" : e_from.getNodeValue())+"\nNode relation element text content: \n"+nodeRelationElement.getTextContent()+"\nElement from text content:\n"+e_from.getTextContent());
	                        }
	
	                        NodeList nodeTo = nodeRelationElement.getElementsByTagName("to");
	                        Element e_to = (Element) nodeTo.item(0);
	                        String to_sentence_id = e_to.getAttribute("sent");
	                        String to_chan = e_to.getAttribute("chan");
	                        int to_ann_nr;
	                        try {
	                        	to_ann_nr = Integer.parseInt(e_to.getTextContent());
	                        } catch (NumberFormatException nfe) {
	                        	throw new UnrecognizedWebserviceResponseException("Invalid text content of element (TO: ann nr). Exception: "+nfe+". Value = "+(e_from.getNodeValue() == null ? "null" : e_from.getNodeValue())+"\nNode relation element text content: \n"+nodeRelationElement.getTextContent()+"\nElement from text content:\n"+e_from.getTextContent());
	                        }

	                        text.addRelationData(relationType, from_sentence_id, from_chan, from_ann_nr, to_sentence_id, to_chan, to_ann_nr);
	                    } else {
	                    	throw new UnrecognizedWebserviceResponseException("Expecting Xml data. Node relation text content = "+nodeRelation.getTextContent());
	                    }
	                }
	        	}
        	} catch (Exception e) {
        		StringWriter errors = new StringWriter();
        		e.printStackTrace(new PrintWriter(errors));
        		throw new UnrecognizedWebserviceResponseException("Error XML parsing during read relations. Exception stack trace: "+errors.toString());
        	}
        }
        
        return text;
    }

	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc),
				new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}
}
