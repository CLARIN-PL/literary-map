package pl.edu.pwr.litmap.exceptions;

import java.io.IOException;

public class ZeroResultsException extends IOException {
	
	public final static String GOOGLE_MSG = "ZERO_RESULTS";

	public ZeroResultsException() {
	}

	public ZeroResultsException(String message) {
		super(message);
	}

	public ZeroResultsException(Throwable cause) {
		super(cause);
	}

	public ZeroResultsException(String message, Throwable cause) {
		super(message, cause);
	}

}
