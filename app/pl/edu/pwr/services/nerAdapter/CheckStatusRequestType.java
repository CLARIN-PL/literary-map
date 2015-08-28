
package pl.edu.pwr.services.nerAdapter;

import pl.edu.pwr.services.interfaces.ICheckStatusRequestType;

public class CheckStatusRequestType implements ICheckStatusRequestType {
	protected String token;
	
	@Override
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String getToken() {
		return token;
	}

}
