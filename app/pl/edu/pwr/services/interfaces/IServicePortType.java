package pl.edu.pwr.services.interfaces;

import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;

public interface IServicePortType {
	
	public ISendRequestResponseType sendRequest(ISendRequestRequestType sendRequestRequestType);
    public ICheckStatusResponseType checkStatus(ICheckStatusRequestType checkStatusRequestType);
    public IGetResultResponseType getResult(IGetResultRequestType getResultRequestType);
}
