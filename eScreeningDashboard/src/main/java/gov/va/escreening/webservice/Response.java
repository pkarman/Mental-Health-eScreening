package gov.va.escreening.webservice;

/**
 * Created by pouncilt on 8/4/14.
 */
public class Response <T> {
    private ResponseStatus status;
    private T payload;


    public Response() {}

    public Response(ResponseStatus status, T payload){
        this.status = status;
        this.payload = payload;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }
}
