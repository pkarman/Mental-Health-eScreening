package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class ReferencedFormulaMissingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ReferencedFormulaMissingException(ErrorResponse errorResponse) {
        super("Referenced Formula Missing Exception");
        this.errorResponse = errorResponse;
    }

    public ReferencedFormulaMissingException(String message) {
        super(message);
    }

    public ReferencedFormulaMissingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
