package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

/**
 * This is the checked exception-derived class for ErrorResponseException. 
 * Unfortunately we are not using Scala which would have avoided the definition of classes for Runtime and Checked exceptions.
 * 
 * @author Robin Carnow
 */
public class ErrorResponseCheckedException extends Exception implements ErrorResponseException{
    private static final long serialVersionUID = 1L;
    private final ErrorResponse response = new ErrorResponse();
    
    public ErrorResponse getErrorResponse(){
        return response;
    }
    
    @Override
    public String getMessage() {
        return response == null ? "null" : response.getDeveloperMessage();
    }
    
    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
    
}
