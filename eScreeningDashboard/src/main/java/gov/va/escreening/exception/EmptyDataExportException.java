package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class EmptyDataExportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public EmptyDataExportException(ErrorResponse errorResponse) {
        super("Empty Data Export Exception");
        this.errorResponse = errorResponse;
    }

    public EmptyDataExportException(String message) {
        super(message);
    }

    public EmptyDataExportException(String message, Throwable throwable) {
        super(message, throwable);
    }
}