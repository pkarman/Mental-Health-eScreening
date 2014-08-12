package gov.va.escreening.exception;

public class BadUseridException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadUseridException() {
	}

	public BadUseridException(String message) {
		super(message);
	}
}
