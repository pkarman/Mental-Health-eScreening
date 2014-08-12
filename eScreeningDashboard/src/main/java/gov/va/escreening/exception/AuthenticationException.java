package gov.va.escreening.exception;

public class AuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthenticationException() {
	}

	public AuthenticationException(String message) {
		super(message);
	}
}
