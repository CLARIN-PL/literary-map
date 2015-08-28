package pl.edu.pwr.services.morphoAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;
import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;
import pl.edu.pwr.services.interfaces.IGetResultRequestType;
import pl.edu.pwr.services.interfaces.IGetResultResponseType;
import pl.edu.pwr.services.interfaces.ISendRequestRequestType;
import pl.edu.pwr.services.interfaces.ISendRequestResponseType;
import pl.edu.pwr.services.interfaces.IServicePortType;

public class ServicePortType implements IServicePortType {
	
	protected final pl.edu.pwr.services.morpho.MorphoServicePortType morphoServicePortType;

	public ServicePortType(pl.edu.pwr.services.morpho.MorphoServicePortType morphoServicePortType) {
		this.morphoServicePortType = morphoServicePortType;
	}
	
	@Override
	public ISendRequestResponseType sendRequest(
			ISendRequestRequestType sendRequestRequestType) {
		return new pl.edu.pwr.services.morphoAdapter.SendRequestResponseType(
				morphoServicePortType.sendRequest(((pl.edu.pwr.services.morphoAdapter.SendRequestRequestType) sendRequestRequestType).getSendRequestRequestType()));
	}

	@Override
	public ICheckStatusResponseType checkStatus(
			ICheckStatusRequestType checkStatusRequestType) {
		return new pl.edu.pwr.services.morphoAdapter.CheckStatusResponseType(
				morphoServicePortType.checkStatus(((pl.edu.pwr.services.morphoAdapter.CheckStatusRequestType) checkStatusRequestType).getCheckStatusRequestType()));
	}

	@Override
	public IGetResultResponseType getResult(
			IGetResultRequestType getResultRequestType) {
		return new pl.edu.pwr.services.morphoAdapter.GetResultResponseType(
				morphoServicePortType.getResult(((pl.edu.pwr.services.morphoAdapter.GetResultRequestType) getResultRequestType).getGetResultRequestType()));
	}
	
}
