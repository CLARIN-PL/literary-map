
package pl.edu.pwr.services.serelAdapter;

import pl.edu.pwr.services.interfaces.IObjectFactory;

public class ObjectFactory implements IObjectFactory {
	protected pl.edu.pwr.services.serel.ObjectFactory objectFactory = new pl.edu.pwr.services.serel.ObjectFactory();

	@Override
	public CheckStatusRequestType createCheckStatusRequestType() {
		return new CheckStatusRequestType(objectFactory.createCheckStatusRequestType());
	}

	@Override
	public GetResultRequestType createGetResultRequestType() {
		return new GetResultRequestType(objectFactory.createGetResultRequestType());
	}

	@Override
	public SendRequestRequestType createSendRequestRequestType() {
		return new SendRequestRequestType(objectFactory.createSendRequestRequestType());
	}

}
