package pl.edu.pwr.services.serelAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;
import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;
import pl.edu.pwr.services.interfaces.IGetResultRequestType;
import pl.edu.pwr.services.interfaces.IGetResultResponseType;
import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;
import pl.edu.pwr.services.interfaces.IServicePortType;

public class ServicePortType implements IServicePortType {
	
	protected final pl.edu.pwr.services.serel.SerelServicePortType serelServicePortType;

	public ServicePortType(pl.edu.pwr.services.serel.SerelServicePortType serelServicePortType) {
		this.serelServicePortType = serelServicePortType;
	}
	
	@Override
	public ISendRequestResponseType sendRequest(
			ISendRequestRequestType sendRequestRequestType) {
		return new pl.edu.pwr.services.serelAdapter.SendRequestResponseType(
				serelServicePortType.sendRequest(((pl.edu.pwr.services.serelAdapter.SendRequestRequestType) sendRequestRequestType).getSendRequestRequestType()));
	}

	@Override
	public ICheckStatusResponseType checkStatus(
			ICheckStatusRequestType checkStatusRequestType) {
		return new pl.edu.pwr.services.serelAdapter.CheckStatusResponseType(
				serelServicePortType.checkStatus(((pl.edu.pwr.services.serelAdapter.CheckStatusRequestType) checkStatusRequestType).getCheckStatusRequestType()));
	}

	@Override
	public IGetResultResponseType getResult(
			IGetResultRequestType getResultRequestType) {
		return new pl.edu.pwr.services.serelAdapter.GetResultResponseType(
				serelServicePortType.getResult(((pl.edu.pwr.services.serelAdapter.GetResultRequestType) getResultRequestType).getGetResultRequestType()));
	}
	
}
