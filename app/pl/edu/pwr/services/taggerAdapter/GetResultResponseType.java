package pl.edu.pwr.services.taggerAdapter;

import pl.edu.pwr.services.interfaces.IGetResultResponseType;

public class GetResultResponseType implements IGetResultResponseType {
	protected final pl.edu.pwr.services.tagger.GetResultResponseType getResultResponseType;

	public GetResultResponseType(String xml) {
		this.getResultResponseType = new pl.edu.pwr.services.tagger.GetResultResponseType();
		this.getResultResponseType.setXml(xml);
	}

	public GetResultResponseType(pl.edu.pwr.services.tagger.GetResultResponseType getResultResponseType) {
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
