package pl.edu.pwr.services.nerAdapter;

import pl.edu.pwr.litmap.webservices.wsdl.WsdlWebserviceClient;
import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;
import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;
import pl.edu.pwr.services.interfaces.IGetResultRequestType;
import pl.edu.pwr.services.interfaces.IGetResultResponseType;
import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;
import pl.edu.pwr.services.interfaces.IServicePortType;
import pl.edu.pwr.services.ner.Format;
import pl.edu.pwr.services.ner.LinerResponse;
import pl.edu.pwr.services.ner.OperationFaultMsg;

public class ServicePortType implements IServicePortType {
	
    private static final int STATUS_READY = 3;
    private static final int STATUS_ERROR = 4;
    private static final String MODEL = "56nam";
//    private static final String MODEL = "n82"; // nowe kategorie np. "NAM_LIV_PERSON"
    private LinerResponse linerResponse;
	
	protected final pl.edu.pwr.services.ner.Nerws nerServicePort;

	public ServicePortType(pl.edu.pwr.services.ner.Nerws nerServicePort) {
		this.nerServicePort = nerServicePort;
	}
	
	@Override
	public ISendRequestResponseType sendRequest(
			ISendRequestRequestType sendRequestRequestType) {
		LinerResponse linerResponse;
		String token;
		try {
			linerResponse = nerServicePort.annotate(Format.CCL, Format.CCL, MODEL, sendRequestRequestType.getContent());
			token = linerResponse.getMsg();
		} catch (OperationFaultMsg e) {
			System.out.println("Error during send request to Ner service: ");
			e.printStackTrace();
			token = null;
		}
		pl.edu.pwr.services.nerAdapter.SendRequestResponseType sendRequestResponse = new SendRequestResponseType(token);

		return sendRequestResponse;
	}

	@Override
	public ICheckStatusResponseType checkStatus(
			ICheckStatusRequestType checkStatusRequestType) {
		int resultStatus;
		try {
			linerResponse = nerServicePort.getResult(checkStatusRequestType.getToken());
			resultStatus = linerResponse.getStatus();
			if (resultStatus == STATUS_ERROR) {
	        	String errorMsg = linerResponse.getMsg();
	        	System.out.println("Ner service error message: "+errorMsg);
			}
		} catch (OperationFaultMsg e) {
			resultStatus = STATUS_ERROR;
			e.printStackTrace();
		}
        String status;
        if (resultStatus == STATUS_READY) {
        	status = WsdlWebserviceClient.STATUS_READY;
        } else if (resultStatus == STATUS_ERROR) {
        	status = WsdlWebserviceClient.STATUS_ERROR;
        } else {
        	status = WsdlWebserviceClient.STATUS_PROCESSING;
        }
        pl.edu.pwr.services.nerAdapter.CheckStatusResponseType checkStatusResponse = new CheckStatusResponseType(status);
        
		return checkStatusResponse;
	}

	@Override
	public IGetResultResponseType getResult(
			IGetResultRequestType getResultRequestType) {
		pl.edu.pwr.services.nerAdapter.GetResultResponseType getResultResponseType = new GetResultResponseType(linerResponse.getMsg());
		return getResultResponseType;
	}
	
}
