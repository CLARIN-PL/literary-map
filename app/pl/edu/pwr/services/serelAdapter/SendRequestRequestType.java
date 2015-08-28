
package pl.edu.pwr.services.serelAdapter;

import pl.edu.pwr.services.interfaces.ISendRequestRequestType;

public class SendRequestRequestType implements ISendRequestRequestType {
	protected final pl.edu.pwr.services.serel.SendRequestRequestType sendRequestRequestType;
	
	public SendRequestRequestType(pl.edu.pwr.services.serel.SendRequestRequestType sendRequestRequestType) {
		this.sendRequestRequestType = sendRequestRequestType;
	}

	@Override
	public void setContent(String value) {
		sendRequestRequestType.setContent(value);
	}

	public pl.edu.pwr.services.serel.SendRequestRequestType getSendRequestRequestType() {
		return sendRequestRequestType;
	}

	@Override
	public String getContent() {
		return sendRequestRequestType.getContent();
	}

}
