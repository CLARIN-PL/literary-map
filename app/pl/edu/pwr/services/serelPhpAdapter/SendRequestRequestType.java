
package pl.edu.pwr.services.serelPhpAdapter;

import pl.edu.pwr.services.interfaces.ISendRequestRequestType;

public class SendRequestRequestType implements ISendRequestRequestType {	
	protected String content;

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return content;
	}

}
