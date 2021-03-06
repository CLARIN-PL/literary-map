package pl.edu.pwr.litmap.ccl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Attributes;

public class CclSAXStreamReader extends StreamReader {
	
	private final String TAG_ANN			= "ann";
	private final String TAG_BASE 			= "base";
	private final String TAG_CHAN			= "chan";
	private final String TAG_CTAG			= "ctag";
	private final String TAG_DISAMB			= "disamb";
	private final String TAG_ID 			= "id";
	private final String TAG_ORTH			= "orth";
	private final String TAG_NS				= "ns";
	private final String TAG_PARAGRAPH 		= "chunk";
	private final String TAG_PARAGRAPH_SET	= "chunkSet";
	private final String TAG_SENTENCE		= "sentence";
	private final String TAG_TAG			= "lex";
	private final String TAG_TOKEN 			= "tok";
	private final String TAG_HEAD 			= "head";
	
	class AnnChan {
		
		public String chan = null;
		public String number = null;
		public String head = "0";
		
		public AnnChan(String chan, String number, String head){
			this.chan = chan;
			this.number = number;
			this.head = head;
		}
		
		public String toString(){
			return this.chan + "#" + this.number;
		}
	}

	class CclSaxParser extends DefaultHandler {
		ArrayList<Paragraph> paragraphs;
		Paragraph currentParagraph = null;
		Sentence currentSentence = null;
		HashMap<String,String> chunkMetaData;
		Hashtable<String, Annotation> annotations;
		Token currentToken = null;
		String tmpBase = null;
		String tmpCtag = null;
		InputStream is;
		String tmpValue;
		Boolean tmpDisamb = false;
		int idx =0;
		String chanName;
		String chanHead;
		boolean foundDisamb;
		public CclSaxParser(InputStream is) throws IOException {
			this.is = is;
			paragraphs = new ArrayList<Paragraph>();
			parseDocument();
		}
		private void parseDocument() throws IOException {
		    SAXParserFactory factory = SAXParserFactory.newInstance();
		    try {
		        SAXParser parser = factory.newSAXParser();
		        parser.parse(is,this);
		    } catch (ParserConfigurationException e) {
		    	throw new IOException("Parse error (ParserConfigurationException)");
		    } catch (SAXException e) {
		    	throw new IOException("Parse error (SAXException). Exception: "+e);
		    } catch (IOException e) {
		    	throw new IOException("Parse error (IOException)");
		    }
		}
		
		@Override
		public InputSource resolveEntity (String publicId, String systemId){
				return new InputSource(new StringReader(""));
		}
		@Override
		public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
			tmpValue = "";
		    if (elementName.equalsIgnoreCase(TAG_PARAGRAPH)) {
		    	chunkMetaData = new HashMap<String,String>();
		    	for(int i=0; i<attributes.getLength(); i++) {
					if ( !attributes.getQName(i).toString().equals(TAG_ID) ){
						if(!attributes.getQName(i).toString().contains(":href"))
							chunkMetaData.put(attributes.getQName(i).toString(), attributes.getValue(i));
						else 
							chunkMetaData.put("xlink:href", attributes.getValue(i));
					}
				}
		    	currentParagraph = new Paragraph(attributes.getValue(TAG_ID));
		    	currentParagraph.setChunkMetaData(chunkMetaData);
		    	currentParagraph.setAttributeIndex(attributeIndex);
		    }
		    if (elementName.equalsIgnoreCase(TAG_SENTENCE)) {
		        currentSentence = new Sentence();
		        annotations = new Hashtable<String, Annotation>();
		        idx =0;
		        currentSentence.setId(attributes.getValue(TAG_ID));
		        
		    }
		    if (elementName.equalsIgnoreCase(TAG_TOKEN)) {
		        currentToken = new Token();
		        currentToken.setId(attributes.getValue(TAG_ID));
		    }
		    if(elementName.equalsIgnoreCase(TAG_TAG)){
		    	if (attributes.getValue(TAG_DISAMB) == null)
		    		tmpDisamb = false;
		    	else
		    		if (Integer.parseInt(attributes.getValue(TAG_DISAMB)) == 0)
		    			tmpDisamb = false;
		    		else
		    			tmpDisamb = true;
		    }
		    if (elementName.equalsIgnoreCase(TAG_ANN)) {
		    	chanName = attributes.getValue(TAG_CHAN);
				chanHead = attributes.getValue(TAG_HEAD) != null ?
						attributes.getValue(TAG_HEAD) : "0";
		    }
		    if (elementName.equalsIgnoreCase(TAG_NS)) {
		        if (currentToken != null){
		        	currentToken.setNoSpaceAfter(true);
		        }
		    }
		}
		@Override
		public void endElement(String s, String s1, String element) throws SAXException {
		    if (element.equals(TAG_PARAGRAPH)) {
		        paragraphs.add(currentParagraph);
		    }
		    if (element.equalsIgnoreCase(TAG_SENTENCE)) {
		    	for (Annotation chunk : annotations.values())
					currentSentence.addChunk(chunk);
		        currentParagraph.addSentence(currentSentence);
		    }
		    if (element.equalsIgnoreCase(TAG_TOKEN)) {
		    	ArrayList<Tag> tags = currentToken.getTags();
		    	foundDisamb = false;
				for (Tag tag : tags) {
					if (tag.getDisamb()) {
						currentToken.setAttributeValue(1, tag.getBase());
						currentToken.setAttributeValue(2, tag.getCtag());
					}
				}
				if (!foundDisamb){
					currentToken.setAttributeValue(1, tags.get(0).getBase());
					currentToken.setAttributeValue(2, tags.get(0).getCtag());
				}
		        currentSentence.addToken(currentToken);
		        idx++;
		    }
		    if(element.equalsIgnoreCase(TAG_ORTH)){
		        currentToken.setAttributeValue(0,tmpValue);
		    }
		    if(element.equalsIgnoreCase(TAG_TAG)){
		    	currentToken.addTag(new Tag(tmpBase,tmpCtag,tmpDisamb));
		    }
		    if(element.equalsIgnoreCase(TAG_BASE)){
		    	tmpBase = tmpValue;
		    }
		    if(element.equalsIgnoreCase(TAG_CTAG)){
		    	tmpCtag = tmpValue;
		    }
		    if(element.equalsIgnoreCase(TAG_ANN)){
		    	String chanNumber = tmpValue.trim();
		    	if (!chanNumber.equals("0")){
		    		AnnChan ann = new AnnChan(chanName, chanNumber, chanHead);
		    		if (annotations.containsKey(ann.toString())){
						annotations.get(ann.toString()).addToken(idx);
					}
					else {
						annotations.put(ann.toString(),
							new Annotation(idx, ann.chan, Integer.parseInt(chanNumber), currentSentence));		
					}
					if(ann.head.equals("1"))
						annotations.get(ann.toString()).setHead(idx);
		    		
		    	}
		    }
		}
		@Override
		public void characters(char[] ac, int start, int length) throws SAXException {
			for(int i=start;i<start+length;i++)
					tmpValue += ac[i];
		}
	}

	
	private TokenAttributeIndex attributeIndex;
	private int currIndex=0;
	private CclSaxParser parser_out;
	
	public CclSAXStreamReader(InputStream is) throws IOException {
		this.attributeIndex = new TokenAttributeIndex();
		this.attributeIndex.addAttribute("orth");
		this.attributeIndex.addAttribute("base");
		this.attributeIndex.addAttribute("ctag");
		this.parser_out = new CclSaxParser(is);		
	}
	
	@Override
	public TokenAttributeIndex getAttributeIndex() {
		return this.attributeIndex;
	}
	
	@Override
	public void close() throws IOException {
		
	}	
		
	@Override
	public boolean paragraphReady() throws IOException {
		if (currIndex<parser_out.paragraphs.size())
			return true;
		else
			return false;
	}
	
	@Override
	protected Paragraph readRawParagraph() throws IOException {
		if (!paragraphReady())
			return null;
		return parser_out.paragraphs.get(currIndex++);
	}
	
}

