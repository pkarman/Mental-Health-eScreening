package gov.va.escreening.webservice;

/**
 * Created by pouncilt on 10/9/14.
 */
public class RequestError {
    private String userFriendlyMessage;
    private String exceptionMessage;

    public RequestError(String userFriendlyMessage, String exceptionMessage) {
        this.userFriendlyMessage = userFriendlyMessage;
        this.exceptionMessage = exceptionMessage;
    }

    public String getUserFriendlyMessage() {
        return userFriendlyMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
