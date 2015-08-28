
package pl.edu.pwr.services.serelPhpAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusResponseType;

public class CheckStatusResponseType implements ICheckStatusResponseType {
	
	protected String status;

	public CheckStatusResponseType(String status) {
		this.status = status;
	}
	
	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String value) {
		this.status = value;
	}

}
