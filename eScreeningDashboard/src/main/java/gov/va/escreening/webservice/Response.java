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

    public Response(ErrorResponse errorResponse){
        this.status = new ResponseStatus(ResponseStatus.Request.Failed);
        this.errorResponse = errorResponse;
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
