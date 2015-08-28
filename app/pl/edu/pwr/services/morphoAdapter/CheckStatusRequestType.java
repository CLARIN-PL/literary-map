
package pl.edu.pwr.services.morphoAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;

public class CheckStatusRequestType implements ICheckStatusRequestType {
	protected final pl.edu.pwr.services.morpho.CheckStatusRequestType checkStatusRequestType;

	public CheckStatusRequestType(pl.edu.pwr.services.morpho.CheckStatusRequestType checkStatusRequestType) {
		this.checkStatusRequestType = checkStatusRequestType;
	}
	
	@Override
	public void setToken(String value) {
		checkStatusRequestType.setToken(value);
	}
	
	public pl.edu.pwr.services.morpho.CheckStatusRequestType getCheckStatusRequestType() {
		return checkStatusRequestType;
	}

	@Override
	public String getToken() {
		return checkStatusRequestType.getToken();
	}

}
