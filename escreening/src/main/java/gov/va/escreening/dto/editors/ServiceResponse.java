package gov.va.escreening.dto.editors;

import java.io.Serializable;

public class ServiceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;
    private Integer code;
    private String userMessage;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public ServiceResponse() {

    }
}
