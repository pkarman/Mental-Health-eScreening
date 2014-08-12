package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class CouldNotResolveVariableValueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public CouldNotResolveVariableValueException(ErrorResponse errorResponse) {
        super("Could not resolve variable value exception");
        this.errorResponse = errorResponse;
    }

    public CouldNotResolveVariableValueException(String message) {
        super(message);
    }

    public CouldNotResolveVariableValueException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
