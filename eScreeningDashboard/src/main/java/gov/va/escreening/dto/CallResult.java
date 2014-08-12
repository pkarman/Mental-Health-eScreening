package gov.va.escreening.dto;

import java.io.Serializable;

public class CallResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean hasError;
    private String userMessage;
    private String systemMessage;

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public CallResult() {
        // default constructor.
    }
}
