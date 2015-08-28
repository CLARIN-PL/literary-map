package pl.edu.pwr.litmap.webservices.wsdl;

import pl.edu.pwr.services.interfaces.IObjectFactory;
import pl.edu.pwr.services.interfaces.IServicePortType;
import pl.edu.pwr.services.morpho.MorphoService;

public class WsdlWebserviceMorphoClient extends WsdlWebserviceClient {

	private MorphoService morphoService = new MorphoService();
	private IServicePortType morpho = new pl.edu.pwr.services.morphoAdapter.ServicePortType(morphoService.getMorphoServicePort());
	private final static String SERVICE_NAME = "Morpho";
    protected static final int SERVICE_STATUS_CHECK_INTERVAL = 400; // in milliseconds
    protected static final int SERVICE_WAIT_FOR_RESULTS_MAX_TIME = 30000; // in milliseconds

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	protected IServicePortType getServicePort() {
		return morpho;
	}

	@Override
	protected IObjectFactory getObjectFactory() {
		return new pl.edu.pwr.services.morphoAdapter.ObjectFactory();
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
