
package pl.edu.pwr.services.nerAdapter;

import pl.edu.pwr.services.interfaces.IGetResultResponseType;

public class GetResultResponseType implements IGetResultResponseType {

	protected String xml;
	
	public GetResultResponseType(String xml) {
		this.xml = xml;
	}
	
	@Override
	public String getXml() {
		return this.xml;
	}

	@Override
	public void setXml(String xml) {
		this.xml = xml;
	}

}
