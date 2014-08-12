package gov.va.escreening.exception;

public class BadPasswordException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadPasswordException() {
	}

	public BadPasswordException(String message) {
		super(message);
	}
}
