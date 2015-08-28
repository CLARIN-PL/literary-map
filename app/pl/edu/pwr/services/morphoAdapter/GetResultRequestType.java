
package pl.edu.pwr.services.morphoAdapter;

import pl.edu.pwr.services.interfaces.IGetResultRequestType;

public class GetResultRequestType implements IGetResultRequestType {
	protected final pl.edu.pwr.services.morpho.GetResultRequestType getResultRequestType;

	public GetResultRequestType(pl.edu.pwr.services.morpho.GetResultRequestType getResultRequestType) {
		this.getResultRequestType = getResultRequestType;
	}
	
	@Override
	public void setToken(String value) {
		getResultRequestType.setToken(value);
	}
	
	public pl.edu.pwr.services.morpho.GetResultRequestType getGetResultRequestType() {
		return getResultRequestType;
	}

	@Override
	public String getToken() {
		return getResultRequestType.getToken();
	}

}
