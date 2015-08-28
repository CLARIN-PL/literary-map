
package pl.edu.pwr.services.serelAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;

public class CheckStatusResponseType implements ICheckStatusResponseType {
	protected final pl.edu.pwr.services.serel.CheckStatusResponseType checkStatusResponseType;

	public CheckStatusResponseType(String status) {
		this.checkStatusResponseType = new pl.edu.pwr.services.serel.CheckStatusResponseType();
		this.checkStatusResponseType.setStatus(status);
	}

	public CheckStatusResponseType(pl.edu.pwr.services.serel.CheckStatusResponseType checkStatusResponseType) {
		this.checkStatusResponseType = checkStatusResponseType;
	}
	
	@Override
	public String getStatus() {
		return checkStatusResponseType.getStatus();
	}

	@Override
	public void setStatus(String value) {
		checkStatusResponseType.setStatus(value);
	}

}
