package gov.va.escreening.exception;

public class VistaVerificationAlreadyMappedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VistaVerificationAlreadyMappedException(String message) {
        super(message);
    }

    public VistaVerificationAlreadyMappedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
