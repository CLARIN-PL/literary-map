
package pl.edu.pwr.services.nerAdapter;

import pl.edu.pwr.services.interfaces.ISendRequestResponseType;

public class SendRequestResponseType implements ISendRequestResponseType {
	
	protected String token;
	
	public SendRequestResponseType(String token) {
		this.token = token;
	}
	
	@Override
	public String getToken() {
		return token;
	}

	@Override
	public void setToken(String token) {
		this.token = token;
	}
}