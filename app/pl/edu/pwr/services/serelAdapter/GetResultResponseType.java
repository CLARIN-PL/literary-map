
package pl.edu.pwr.services.serelAdapter;

import pl.edu.pwr.services.interfaces.IGetResultResponseType;

public class GetResultResponseType implements IGetResultResponseType {
	protected final pl.edu.pwr.services.serel.GetResultResponseType getResultResponseType;

	public GetResultResponseType(String xml) {
		this.getResultResponseType = new pl.edu.pwr.services.serel.GetResultResponseType();
		this.getResultResponseType.setXml(xml);
	}

	public GetResultResponseType(pl.edu.pwr.services.serel.GetResultResponseType getResultResponseType) {
		this.getResultResponseType = getResultResponseType;
	}
	
	@Override
	public String getXml() {
		return getResultResponseType.getXml();
	}

	@Override
	public void setXml(String xml) {
		getResultResponseType.setXml(xml);
	}

}
