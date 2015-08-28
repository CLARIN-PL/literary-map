package pl.edu.pwr.services.interfaces;

public interface IObjectFactory {
	
    public ICheckStatusRequestType createCheckStatusRequestType();
    public IGetResultRequestType createGetResultRequestType();
    public ISendRequestRequestType createSendRequestRequestType();
}
