package gov.va.escreening.webservice;

import gov.va.escreening.dto.ae.ErrorResponse;

/**
 * Created by pouncilt on 8/4/14.
 */
public class Response <T> {
    private ResponseStatus status;
    private T payload;
    private ErrorResponse errorResponse;


    public Response() {}

    public Response(ResponseStatus status, T payload){
        this.status = status;
        this.payload = payload;
    }

    //this is a temp solution that should be replaced by using a constructor. This was used to ease the transition to using the errorResponse field to hold errors
    public static Response<ErrorResponse> createError(ErrorResponse errorResponse){
        Response<ErrorResponse> response = new Response<ErrorResponse>(new ResponseStatus(ResponseStatus.Request.Failed), errorResponse);
        response.errorResponse = errorResponse;
        return response;
    }
    
    public ResponseStatus getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }
    
    public ErrorResponse getError(){
    	return errorResponse;
    }
}
