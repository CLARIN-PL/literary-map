
package pl.edu.pwr.services.serelAdapter;

import pl.edu.pwr.services.interfaces.ISendRequestResponseType;

public class SendRequestResponseType implements ISendRequestResponseType {
	protected final pl.edu.pwr.services.serel.SendRequestResponseType sendRequestResponseType;
	
	public SendRequestResponseType(String token) {
		this.sendRequestResponseType = new pl.edu.pwr.services.serel.SendRequestResponseType();
		this.sendRequestResponseType.setToken(token);
	}
	
	public SendRequestResponseType(pl.edu.pwr.services.serel.SendRequestResponseType sendRequestResponseType) {
		this.sendRequestResponseType = sendRequestResponseType;
	}

	@Override
	public String getToken() {
		return sendRequestResponseType.getToken();
	}

	@Override
	public void setToken(String token) {
		sendRequestResponseType.setToken(token);
	}
}