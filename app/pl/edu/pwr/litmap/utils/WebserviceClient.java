package pl.edu.pwr.litmap.utils;

import java.io.IOException;

public interface WebserviceClient {
	public String process(String content) throws IOException;
}
