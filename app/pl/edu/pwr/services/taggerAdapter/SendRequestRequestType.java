
package pl.edu.pwr.services.taggerAdapter;

import pl.edu.pwr.services.interfaces.ISendRequestRequestType;

public class SendRequestRequestType implements ISendRequestRequestType {
	protected final pl.edu.pwr.services.tagger.SendRequestRequestType sendRequestRequestType;
	
	public SendRequestRequestType(pl.edu.pwr.services.tagger.SendRequestRequestType sendRequestRequestType) {
		this.sendRequestRequestType = sendRequestRequestType;
	}

	@Override
	public void setContent(String value) {
		sendRequestRequestType.setContent(value);
	}

	public pl.edu.pwr.services.tagger.SendRequestRequestType getSendRequestRequestType() {
		return sendRequestRequestType;
	}

	@Override
	public String getContent() {
		return sendRequestRequestType.getContent();
	}

}
