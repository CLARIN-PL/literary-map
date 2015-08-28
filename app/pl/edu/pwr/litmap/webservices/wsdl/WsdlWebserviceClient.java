package pl.edu.pwr.litmap.webservices.wsdl;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.edu.pwr.litmap.objectrecognize.ObjectRecognize;
import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;
import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;
import pl.edu.pwr.services.interfaces.IGetResultRequestType;
import pl.edu.pwr.services.interfaces.IGetResultResponseType;
import pl.edu.pwr.services.interfaces.IObjectFactory;
import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;
import pl.edu.pwr.services.interfaces.IServicePortType;

public abstract class WsdlWebserviceClient {
	
    public static final String STATUS_READY = "READY";
    public static final String STATUS_PROCESSING = "PROCESSING";
    public static final String STATUS_QUEUED = "QUEUED";
    public static final String STATUS_ERROR = "ERROR";
    private String token;
    private String lastStatus = "";
    
    public abstract String getServiceName();
    
    protected abstract IServicePortType getServicePort();
    
    protected abstract IObjectFactory getObjectFactory();
    
    protected abstract int getServiceStatusCheckInterval();// in milliseconds
    
    protected abstract int getServiceWaitForResultMaxTime(); // in milliseconds

	public String getResponse(String content) throws IOException {
		System.out.println(this.getServiceName()+" service start");
        String result = null;
        boolean resultsReady = false;
        try {
			ISendRequestRequestType sendRequestRequest = this.getObjectFactory().createSendRequestRequestType();
			sendRequestRequest.setContent(content);
			
			ISendRequestResponseType sendRequestResponse = this.getServicePort().sendRequest(sendRequestRequest);
			this.token = sendRequestResponse.getToken();
			System.out.println(this.getServiceName()+" service token = "+token);
			
	        long timeStart = Calendar.getInstance().getTimeInMillis();
	        while (!(resultsReady = isResultReady()) && (Calendar.getInstance().getTimeInMillis() - timeStart < getServiceWaitForResultMaxTime())) {
					Thread.sleep(getServiceStatusCheckInterval());
	        }
			System.out.println("\n"+this.getServiceName()+" service stop. Execution time: "+(Calendar.getInstance().getTimeInMillis() - timeStart)+ " ms.");
		} catch (InterruptedException e) {
			Logger.getLogger(ObjectRecognize.class.getName()).log(Level.SEVERE, null, e);
		}
        if (resultsReady) {
            result = getResult();
        } else {
            throw new IOException("Too long time waiting for results "+this.getServiceName()+" service. Maximum time is now "+getServiceWaitForResultMaxTime()+" ms. Last status: "+lastStatus+".");
        }
        
        return result;
	}
        
    private boolean isResultReady() throws IOException {
    	ICheckStatusRequestType checkStatusRequest = this.getObjectFactory().createCheckStatusRequestType();
    	checkStatusRequest.setToken(token);
        ICheckStatusResponseType checkStatusResponse = this.getServicePort().checkStatus(checkStatusRequest);
        
        String currentStatus = checkStatusResponse.getStatus();
        if (currentStatus.equals(lastStatus)) {
            System.out.print(".");
        } else {
        	System.out.print("\n"+getServiceName()+" service check status = "+currentStatus+" ");
        }
        lastStatus = currentStatus;
       
        return lastStatus.equals(STATUS_READY);
    }
    
    private String getResult() throws IOException {
    	IGetResultRequestType getResultRequest = this.getObjectFactory().createGetResultRequestType();
    	getResultRequest.setToken(token);
        IGetResultResponseType getResultResponse = this.getServicePort().getResult(getResultRequest);
        String resultXml = getResultResponse.getXml();
        
        return resultXml;
    }

}
