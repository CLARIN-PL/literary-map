package pl.edu.pwr.litmap.exceptions;

import java.io.IOException;

public class OverQueryLimitException extends IOException {
	
	public final static String GOOGLE_MSG = "OVER_QUERY_LIMIT";

	public OverQueryLimitException() {
	}

	public OverQueryLimitException(String message) {
		super(message);
	}

	public OverQueryLimitException(Throwable cause) {
		super(cause);
	}

	public OverQueryLimitException(String message, Throwable cause) {
		super(message, cause);
	}

}
