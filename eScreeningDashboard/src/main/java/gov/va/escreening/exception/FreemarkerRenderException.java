package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class FreemarkerRenderException extends ErrorResponseRuntimeException{
	
	public FreemarkerRenderException(){
        super();
    }
    
    public FreemarkerRenderException(ErrorResponse error){
        super(error);
    }
    
    //TODO: this constructor shouldn't be used since we want to push developers to use the ErrorBuilder class and provide both developer message and user message.
    public FreemarkerRenderException(String message) {
        
        //Sets both developer message and user message to the given string in the Error Response
        super(message);
    }

}
