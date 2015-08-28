
package pl.edu.pwr.services.taggerAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;

public class CheckStatusRequestType implements ICheckStatusRequestType {
	protected final pl.edu.pwr.services.tagger.CheckStatusRequestType checkStatusRequestType;

	public CheckStatusRequestType(pl.edu.pwr.services.tagger.CheckStatusRequestType checkStatusRequestType) {
		this.checkStatusRequestType = checkStatusRequestType;
	}
	
	@Override
	public void setToken(String value) {
		checkStatusRequestType.setToken(value);
	}

	public pl.edu.pwr.services.tagger.CheckStatusRequestType getCheckStatusRequestType() {
		return checkStatusRequestType;
	}

	@Override
	public String getToken() {
		return checkStatusRequestType.getToken();
	}

}
