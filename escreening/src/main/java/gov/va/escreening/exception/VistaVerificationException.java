package gov.va.escreening.exception;

public class VistaVerificationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VistaVerificationException(String message) {
        super(message);
    }

    public VistaVerificationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
