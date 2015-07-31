package gov.va.escreening.exception;

public class VistaLockedAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VistaLockedAccountException(String message) {
        super(message);
    }

    public VistaLockedAccountException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
