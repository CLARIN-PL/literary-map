package pl.edu.pwr.litmap.webservices.wsdl;

import java.io.IOException;

import pl.edu.pwr.litmap.webservices.WebserviceClient;
import pl.edu.pwr.litmap.webservices.php.PhpSerelWebserviceClient;

public class WsdlWebservicesListClient implements WebserviceClient {
	
	private static final boolean DEBUG_LOAD = true;

//	private WsdlWebserviceClient morpho;

	private WsdlWebserviceClient tagger;

	private WsdlWebserviceClient ner;
	
	private WsdlWebserviceClient serel;
	
	public WsdlWebservicesListClient() {
//		morpho = new WsdlWebserviceMorphoClient();
		
		if (DEBUG_LOAD) {
			System.out.println("Load WsdlWebserviceTaggerClient...");
		}
			
		tagger = new WsdlWebserviceTaggerClient();
//		tagger = new WsdlWebserviceTaggerPhpClient();
		
		if (DEBUG_LOAD) {
			System.out.println("Load WsdlWebserviceNerClient...");
		}
		
		ner = new WsdlWebserviceNerClient();
		
		if (DEBUG_LOAD) {
			System.out.println("Load WsdlWebserviceSerelPhpClient...");
		}

//		serel = new WsdlWebserviceSerelClient();
		serel = new WsdlWebserviceSerelPhpClient();
	}
	
	@Override
	public String process(String content) throws IOException {
		String result = null;

//		result = ner.getResponse(tagger.getResponse(morpho.getResponse(content)));
		// wgawel: wyłączenie Morpho, teraz plaint text jest przetwarzany przez Taggera, 2014.12.10
		
		result = serel.getResponse(ner.getResponse(tagger.getResponse(content)));
		
		return result;
	}

}
