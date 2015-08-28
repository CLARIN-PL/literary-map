package pl.edu.pwr.litmap.tasksManager;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class TokenGenerator {
	private SecureRandom random = new SecureRandom();

	public String nextId() {
		return new BigInteger(130, random).toString(32);
	}
}
