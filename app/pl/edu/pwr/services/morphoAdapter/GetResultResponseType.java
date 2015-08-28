
package pl.edu.pwr.services.morphoAdapter;

import pl.edu.pwr.services.interfaces.IGetResultResponseType;

public class GetResultResponseType implements IGetResultResponseType {
	protected final pl.edu.pwr.services.morpho.GetResultResponseType getResultResponseType;

	public GetResultResponseType(pl.edu.pwr.services.morpho.GetResultResponseType getResultResponseType) {
		this.getResultResponseType = getResultResponseType;
	}
	
	@Override
	public String getXml() {
		return getResultResponseType.getXml();
	}
	
	public pl.edu.pwr.services.morpho.GetResultResponseType getGetResultResponseType() {
		return getResultResponseType;
	}

	@Override
	public void setXml(String xml) {
		getResultResponseType.setXml(xml);
	}

}
