
package pl.edu.pwr.services.morphoAdapter;

import pl.edu.pwr.services.interfaces.IObjectFactory;

public class ObjectFactory implements IObjectFactory {
	protected pl.edu.pwr.services.morpho.ObjectFactory objectFactory = new pl.edu.pwr.services.morpho.ObjectFactory();

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
