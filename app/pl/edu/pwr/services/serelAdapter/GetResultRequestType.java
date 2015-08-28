
package pl.edu.pwr.services.serelAdapter;

import pl.edu.pwr.services.interfaces.IGetResultRequestType;

public class GetResultRequestType implements IGetResultRequestType {
	protected final pl.edu.pwr.services.serel.GetResultRequestType getResultRequestType;

	public GetResultRequestType(pl.edu.pwr.services.serel.GetResultRequestType getResultRequestType) {
		this.getResultRequestType = getResultRequestType;
	}
	
	@Override
	public void setToken(String value) {
		getResultRequestType.setToken(value);
	}

	public pl.edu.pwr.services.serel.GetResultRequestType getGetResultRequestType() {
		return getResultRequestType;
	}

	@Override
	public String getToken() {
		return getResultRequestType.getToken();
	}

}
