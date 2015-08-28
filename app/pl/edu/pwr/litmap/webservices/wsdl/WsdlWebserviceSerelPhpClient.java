package pl.edu.pwr.litmap.webservices.wsdl;

import pl.edu.pwr.litmap.webservices.php.PhpSerelWebserviceClient;
import pl.edu.pwr.services.interfaces.IObjectFactory;
import pl.edu.pwr.services.interfaces.IServicePortType;

public class WsdlWebserviceSerelPhpClient extends WsdlWebserviceClient {

	private PhpSerelWebserviceClient service = new PhpSerelWebserviceClient();
	private IServicePortType servicePort = new pl.edu.pwr.services.serelPhpAdapter.ServicePortType(service);
	private final static String SERVICE_NAME = "Serel (PHP)";
    protected static final int SERVICE_STATUS_CHECK_INTERVAL = 700; // in milliseconds
    protected static final int SERVICE_WAIT_FOR_RESULTS_MAX_TIME = 90000; // in milliseconds

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	protected IServicePortType getServicePort() {
		return servicePort;
	}

	@Override
	protected IObjectFactory getObjectFactory() {
		return new pl.edu.pwr.services.serelAdapter.ObjectFactory();
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
