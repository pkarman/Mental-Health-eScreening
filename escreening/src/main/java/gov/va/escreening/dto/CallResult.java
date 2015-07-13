package gov.va.escreening.dto;

import java.io.Serializable;

public class CallResult implements Serializable {

    private static final long serialVersionUID = 1L;

    final private boolean hasError;
    final private String userMessage;
    final private String systemMessage;

    public CallResult(boolean hasError, String userMsg, String systemMsg) {
        this.userMessage = userMsg;
        this.systemMessage = systemMsg;
        this.hasError = hasError;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public boolean getHasError() {
        return hasError;
    }
}
