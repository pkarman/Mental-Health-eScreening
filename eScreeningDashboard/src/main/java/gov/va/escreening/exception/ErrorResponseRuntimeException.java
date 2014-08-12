package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

/**
 * This is the RuntimeException derived class for ErrorResponseException. 
 * Unfortunately we are not using Scala which would have avoided the definition of classes for Runtime and Checked exceptions.
 * 
 * @author Robin Carnow
 */
public class ErrorResponseRuntimeException extends RuntimeException implements ErrorResponseException{
    private static final long serialVersionUID = 1L;
    protected final ErrorResponse response;
    
    public ErrorResponseRuntimeException(){
        response = new ErrorResponse();
    }
    
    public ErrorResponseRuntimeException(ErrorResponse error){
        response = error;
    }
    
    //TODO: this constructor shouldn't be used since we want to push developers to use the ErrorBuilder class and provide both developer message and user message.
    public ErrorResponseRuntimeException(String message) {
        
        //Sets both developer message and user message to the given string in the Error Response
        response = new ErrorResponse()
            .setDeveloperMessage(message)
            .addMessage(message);
    }
    
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
