package gov.va.escreening.exception;

public class UnregisteredDataTableColumnException extends RuntimeException {

	private static final long serialVersionUID = -1;

	public UnregisteredDataTableColumnException(String message) {
		super(message);
	}

	public UnregisteredDataTableColumnException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
