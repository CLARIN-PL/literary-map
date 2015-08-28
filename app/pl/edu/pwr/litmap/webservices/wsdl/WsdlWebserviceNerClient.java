package pl.edu.pwr.litmap.webservices.wsdl;

import pl.edu.pwr.services.interfaces.IObjectFactory;
import pl.edu.pwr.services.interfaces.IServicePortType;

public class WsdlWebserviceNerClient extends WsdlWebserviceClient {

	private pl.edu.pwr.services.ner.Nerws_Service nerService = new pl.edu.pwr.services.ner.Nerws_Service();
	private IServicePortType ner = new pl.edu.pwr.services.nerAdapter.ServicePortType(nerService.getNerwsSOAP());
//	private pl.edu.pwr.services.ner_old.Nerws_Service nerService = new pl.edu.pwr.services.ner_old.Nerws_Service();
//	private IServicePortType ner = new pl.edu.pwr.services.nerAdapter_old.ServicePortType(nerService.getNerwsSOAP());
	private final static String SERVICE_NAME = "Ner";
    protected static final int SERVICE_STATUS_CHECK_INTERVAL = 400; // in milliseconds
    protected static final int SERVICE_WAIT_FOR_RESULTS_MAX_TIME = 30000; // in milliseconds

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	protected IServicePortType getServicePort() {
		return ner;
	}

	@Override
	protected IObjectFactory getObjectFactory() {
		return new pl.edu.pwr.services.nerAdapter.ObjectFactory();
	}

	@Override
	protected int getServiceStatusCheckInterval() {
		return SERVICE_STATUS_CHECK_INTERVAL;
	}

	@Override
	protected int getServiceWaitForResultMaxTime() {
		return SERVICE_WAIT_FOR_RESULTS_MAX_TIME;
	}

}
