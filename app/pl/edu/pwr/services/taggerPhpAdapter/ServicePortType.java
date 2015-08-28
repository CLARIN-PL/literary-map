package pl.edu.pwr.services.taggerPhpAdapter;

import java.io.IOException;

import pl.edu.pwr.litmap.webservices.php.PhpTaggerWebserviceClient;
import pl.edu.pwr.litmap.webservices.wsdl.WsdlWebserviceClient;
import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;
import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;
import pl.edu.pwr.services.interfaces.IGetResultRequestType;
import pl.edu.pwr.services.interfaces.IGetResultResponseType;
import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;
import pl.edu.pwr.services.interfaces.IServicePortType;

public class ServicePortType implements IServicePortType {
	
	protected final PhpTaggerWebserviceClient phpTaggerWebserviceClient;

	public ServicePortType(PhpTaggerWebserviceClient phpTaggerWebserviceClient) {
		this.phpTaggerWebserviceClient = phpTaggerWebserviceClient;
	}
	
	@Override
	public ISendRequestResponseType sendRequest(
			ISendRequestRequestType sendRequestRequestType) {
		String token;
		try {
			phpTaggerWebserviceClient.sendCcl(sendRequestRequestType.getContent());
			token = phpTaggerWebserviceClient.getToken();
		} catch (IOException e) {
			System.out.println("Error during send request to Tagger (PHP) service: ");
			e.printStackTrace();
			token = null;
		}
		pl.edu.pwr.services.taggerAdapter.SendRequestResponseType sendRequestResponse = new pl.edu.pwr.services.taggerAdapter.SendRequestResponseType(token);

		return sendRequestResponse;
	}

	@Override
	public ICheckStatusResponseType checkStatus(
			ICheckStatusRequestType checkStatusRequestType) {
		String status;
		try {
			status = PhpTaggerWebserviceClient.getResultStatus(checkStatusRequestType.getToken());
		} catch (IOException e) {
			System.out.println("Error during check status of Tagger (PHP) service: ");
			e.printStackTrace();
			status = null;
		}
		pl.edu.pwr.services.taggerAdapter.CheckStatusResponseType checkStatusResponse = new pl.edu.pwr.services.taggerAdapter.CheckStatusResponseType(status);

		return checkStatusResponse;
	}

	@Override
	public IGetResultResponseType getResult(
			IGetResultRequestType getResultRequestType) {
		String xml = "";
		try {
			xml = PhpTaggerWebserviceClient.getResult(getResultRequestType.getToken());
		} catch (IOException e) {
			System.out.println("Error during get result from Tagger (PHP) service: ");
			e.printStackTrace();
		}
		pl.edu.pwr.services.taggerAdapter.GetResultResponseType getResultResponseType = new pl.edu.pwr.services.taggerAdapter.GetResultResponseType(xml);
		return getResultResponseType;
	}
	
}
