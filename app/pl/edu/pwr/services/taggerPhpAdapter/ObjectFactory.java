
package pl.edu.pwr.services.taggerPhpAdapter;

import pl.edu.pwr.services.interfaces.IObjectFactory;

public class ObjectFactory implements IObjectFactory {

	@Override
	public CheckStatusRequestType createCheckStatusRequestType() {
		return new CheckStatusRequestType();
	}

	@Override
	public GetResultRequestType createGetResultRequestType() {
		return new GetResultRequestType();
	}

	@Override
	public SendRequestRequestType createSendRequestRequestType() {
		return new SendRequestRequestType();
	}

}
