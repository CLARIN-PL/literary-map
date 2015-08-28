package pl.edu.pwr.services.taggerAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;
import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;
import pl.edu.pwr.services.interfaces.IGetResultRequestType;
import pl.edu.pwr.services.interfaces.IGetResultResponseType;
import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;
import pl.edu.pwr.services.interfaces.IServicePortType;

public class ServicePortType implements IServicePortType {
	
	protected final pl.edu.pwr.services.tagger.TaggerServicePortType taggerServicePortType;

	public ServicePortType(pl.edu.pwr.services.tagger.TaggerServicePortType taggerServicePortType) {
		this.taggerServicePortType = taggerServicePortType;
	}
	
	@Override
	public ISendRequestResponseType sendRequest(
			ISendRequestRequestType sendRequestRequestType) {
		return new pl.edu.pwr.services.taggerAdapter.SendRequestResponseType(
				taggerServicePortType.sendRequest(((pl.edu.pwr.services.taggerAdapter.SendRequestRequestType) sendRequestRequestType).getSendRequestRequestType()));
	}

	@Override
	public ICheckStatusResponseType checkStatus(
			ICheckStatusRequestType checkStatusRequestType) {
		return new pl.edu.pwr.services.taggerAdapter.CheckStatusResponseType(
				taggerServicePortType.checkStatus(((pl.edu.pwr.services.taggerAdapter.CheckStatusRequestType) checkStatusRequestType).getCheckStatusRequestType()));
	}

	@Override
	public IGetResultResponseType getResult(
			IGetResultRequestType getResultRequestType) {
		return new pl.edu.pwr.services.taggerAdapter.GetResultResponseType(
				taggerServicePortType.getResult(((pl.edu.pwr.services.taggerAdapter.GetResultRequestType) getResultRequestType).getGetResultRequestType()));
	}
	
}
