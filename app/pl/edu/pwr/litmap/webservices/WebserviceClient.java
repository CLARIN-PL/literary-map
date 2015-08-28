package pl.edu.pwr.litmap.webservices;

import java.io.IOException;

public interface WebserviceClient {
	public String process(String content) throws IOException;
}
