package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class CouldNotResolveVariableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public CouldNotResolveVariableException(ErrorResponse errorResponse) {
        super("Could Not Resolve Requested Variable Exception");

        this.errorResponse = errorResponse;
    }

    public CouldNotResolveVariableException(String message) {
        super(message);
    }

    public CouldNotResolveVariableException(String message, Throwable throwable) {
        super(message, throwable);
    }

}