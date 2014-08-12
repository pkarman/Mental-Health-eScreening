package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

/**
 * Exception that indicates a Veteran is not logged into the site
 * @author robin
 *
 */
public class InvalidAssessmentContextException extends RuntimeException{
    private String exceptionMsg;
    private ErrorResponse errorResponse;
    
    private static final long serialVersionUID = 1L;
    
    public InvalidAssessmentContextException(String message) {
        exceptionMsg = message;
    }
    
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public ErrorResponse getErrorResponse() {
        if(errorResponse == null)
            errorResponse = new ErrorResponse().reject("Context", "Unauthorized", "Invalid Veteran Context");
        return errorResponse;
    }
    
    public void setErrorResponse(ErrorResponse errorResponse){
        this.errorResponse = errorResponse;
    }
}
