package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class DataExportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public DataExportException(ErrorResponse errorResponse) {
        super("Cell Does Not Match Column Exception");

        this.errorResponse = errorResponse;
    }

    public DataExportException(String message) {
        super(message);
    }

    public DataExportException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
