package pl.edu.pwr.services.serelPhpAdapter;

import java.io.IOException;

import pl.edu.pwr.litmap.webservices.php.PhpSerelWebserviceClient;
import pl.edu.pwr.litmap.webservices.wsdl.WsdlWebserviceClient;
import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;
import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;
import pl.edu.pwr.services.interfaces.IGetResultRequestType;
import pl.edu.pwr.services.interfaces.IGetResultResponseType;
import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;
import pl.edu.pwr.services.interfaces.IServicePortType;

public class ServicePortType implements IServicePortType {
	
	protected final PhpSerelWebserviceClient phpSerelWebserviceClient;

	public ServicePortType(PhpSerelWebserviceClient phpSerelWebserviceClient) {
		this.phpSerelWebserviceClient = phpSerelWebserviceClient;
	}
	
	@Override
	public ISendRequestResponseType sendRequest(
			ISendRequestRequestType sendRequestRequestType) {
		String token;
		try {
			phpSerelWebserviceClient.sendCcl(sendRequestRequestType.getContent());
			token = phpSerelWebserviceClient.getToken();
		} catch (IOException e) {
			System.out.println("Error during send request to Serel (PHP) service: ");
			e.printStackTrace();
			token = null;
		}
		pl.edu.pwr.services.serelAdapter.SendRequestResponseType sendRequestResponse = new pl.edu.pwr.services.serelAdapter.SendRequestResponseType(token);

		return sendRequestResponse;
	}

	@Override
	public ICheckStatusResponseType checkStatus(
			ICheckStatusRequestType checkStatusRequestType) {
		String status;
		try {
			status = PhpSerelWebserviceClient.getResultStatus(checkStatusRequestType.getToken());
		} catch (IOException e) {
			System.out.println("Error during check status of Serel (PHP) service: ");
			e.printStackTrace();
			status = null;
		}
		pl.edu.pwr.services.serelAdapter.CheckStatusResponseType checkStatusResponse = new pl.edu.pwr.services.serelAdapter.CheckStatusResponseType(status);

		return checkStatusResponse;
	}

	@Override
	public IGetResultResponseType getResult(
			IGetResultRequestType getResultRequestType) {
		String xml = "";
		try {
			xml = PhpSerelWebserviceClient.getResult(getResultRequestType.getToken());
		} catch (IOException e) {
			System.out.println("Error during get result from Serel (PHP) service: ");
			e.printStackTrace();
		}
		pl.edu.pwr.services.serelAdapter.GetResultResponseType getResultResponseType = new pl.edu.pwr.services.serelAdapter.GetResultResponseType(xml);
		return getResultResponseType;
	}
	
}
