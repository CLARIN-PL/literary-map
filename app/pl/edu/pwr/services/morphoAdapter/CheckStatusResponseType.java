
package pl.edu.pwr.services.morphoAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;

public class CheckStatusResponseType implements ICheckStatusResponseType {
	protected final pl.edu.pwr.services.morpho.CheckStatusResponseType checkStatusResponseType;

	public CheckStatusResponseType(pl.edu.pwr.services.morpho.CheckStatusResponseType checkStatusResponseType) {
		this.checkStatusResponseType = checkStatusResponseType;
	}
	
	@Override
	public String getStatus() {
		return checkStatusResponseType.getStatus();
	}
	
	public pl.edu.pwr.services.morpho.CheckStatusResponseType getCheckStatusResponseType() {
		return checkStatusResponseType;
	}

	@Override
	public void setStatus(String value) {
		checkStatusResponseType.setStatus(value);
	}

}
