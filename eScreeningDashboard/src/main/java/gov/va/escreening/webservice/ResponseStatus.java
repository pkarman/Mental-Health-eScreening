package gov.va.escreening.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pouncilt on 8/4/14.
 */
public class ResponseStatus {
    public enum Request {
        Succeeded("succeeded"),
        Failed("failed");

        private final String value;

        public String getValue() {
            return value;
        }

        Request(String value) {
            this.value = value;
        }
    }

    private String message;
    @JsonProperty("status")
    private Request requestStatus;
    private Exception exception;

    public ResponseStatus() {

    }

    public ResponseStatus(Request requestStatus) {
        this.requestStatus = requestStatus;
    }

    public ResponseStatus(Request requestStatus, String message) {
        this.message = message;
        this.requestStatus = requestStatus;
    }

    public ResponseStatus(Request requestStatus, String message, Exception exception) {
        this.message = message;
        this.requestStatus = requestStatus;
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public Request getRequestStatus() {
        return requestStatus;
    }

    public Exception getException() {
        return exception;
    }
}