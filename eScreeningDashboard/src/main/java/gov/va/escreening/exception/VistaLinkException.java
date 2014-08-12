package gov.va.escreening.exception;

public class VistaLinkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VistaLinkException(String message) {
        super(message);
    }

    public VistaLinkException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
