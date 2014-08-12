package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class ReferencedVariableMissingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ReferencedVariableMissingException(ErrorResponse errorResponse) {
        super("Referenced Variable Missing Exception");
        this.errorResponse = errorResponse;
    }

    public ReferencedVariableMissingException(String message) {
        super(message);
    }

    public ReferencedVariableMissingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}