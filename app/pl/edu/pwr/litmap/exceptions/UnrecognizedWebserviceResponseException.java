package pl.edu.pwr.litmap.exceptions;

import java.io.IOException;

public class UnrecognizedWebserviceResponseException extends IOException {

	public UnrecognizedWebserviceResponseException() {
	}

	public UnrecognizedWebserviceResponseException(String message) {
		super(message);
	}

	public UnrecognizedWebserviceResponseException(Throwable cause) {
		super(cause);
	}

	public UnrecognizedWebserviceResponseException(String message, Throwable cause) {
		super(message, cause);
	}

}
