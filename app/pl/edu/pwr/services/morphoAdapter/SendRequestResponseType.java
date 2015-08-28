
package pl.edu.pwr.services.morphoAdapter;

import pl.edu.pwr.services.interfaces.ISendRequestResponseType;

public class SendRequestResponseType implements ISendRequestResponseType {
	protected final pl.edu.pwr.services.morpho.SendRequestResponseType sendRequestResponseType;
	
	public SendRequestResponseType(pl.edu.pwr.services.morpho.SendRequestResponseType sendRequestResponseType) {
		this.sendRequestResponseType = sendRequestResponseType;
	}

	@Override
	public String getToken() {
		return sendRequestResponseType.getToken();
	}
	
	public pl.edu.pwr.services.morpho.SendRequestResponseType getSendRequestResponseType() {
		return sendRequestResponseType;
	}

	@Override
	public void setToken(String token) {
		sendRequestResponseType.setToken(token);
		
	}
}