
package pl.edu.pwr.services.taggerAdapter;

import pl.edu.pwr.services.interfaces.IGetResultRequestType;

public class GetResultRequestType implements IGetResultRequestType {
	protected final pl.edu.pwr.services.tagger.GetResultRequestType getResultRequestType;

	public GetResultRequestType(pl.edu.pwr.services.tagger.GetResultRequestType getResultRequestType) {
		this.getResultRequestType = getResultRequestType;
	}
	
	@Override
	public void setToken(String value) {
		getResultRequestType.setToken(value);
	}

	public pl.edu.pwr.services.tagger.GetResultRequestType getGetResultRequestType() {
		return getResultRequestType;
	}

	@Override
	public String getToken() {
		return getResultRequestType.getToken();
	}

}
