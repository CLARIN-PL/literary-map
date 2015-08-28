
package pl.edu.pwr.services.nerAdapter;

import pl.edu.pwr.services.interfaces.IGetResultRequestType;

public class GetResultRequestType implements IGetResultRequestType {

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
