package gov.va.escreening.dto.ae;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Joiner;

public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Random random = new Random(System.currentTimeMillis());
    private static final Joiner errorJoiner = Joiner.on("\n\t ").skipNulls(); 
    private static final String UNKNOWN_ERROR_MSG = "An unexpected error occured. Please contact an administrator for assistance.";

    private final String id;
    private Integer status;
    private Integer code;
    private String property;
    private List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
    private String developerMessage;

    public ErrorResponse() {
        id = System.currentTimeMillis() + "_" + random.nextInt();
    }
    
    /**
     * Generates a detailed log message
     * @return
     */
    public String getLogMessage(){
        return new StringBuilder("Error Message: ").append(developerMessage)
            .append(" \nError ID: ").append(id)
            .append(" \nError Code: ").append(code)
            .append(" \nStatus: ").append(status)
            .append(" \nError Property: ").append(property)
            .append(" \nError Messages: \n\t").append(errorJoiner.join(errorMessages))
            .toString();
    }
    
    /**
     * Generates a user message based on this error response.
     * Really this object should be serialized as json and sent back instead of using this.
     * Our goal is to add the log ID as an extra option in the UI so an admin can get 
     * find the error in the server logs.
     * @param lineDelimiter what to put at the end of each line. Used only if there are multiple errors.
     * @return
     */
    public String getUserMessage(String lineDelimiter){
        if(errorMessages.isEmpty())
            return UNKNOWN_ERROR_MSG;
        else if(errorMessages.size() == 1){
            //almost like Scala ;)
            return errorMessages.get(0).getUserMessage().or(UNKNOWN_ERROR_MSG);
        }
        
        StringBuilder builder = new StringBuilder("The following errors have occured: \n");
        for(ErrorMessage message : errorMessages){
            builder.append(message.getUserMessage().or("")).append(lineDelimiter);
        }
        return builder.toString();
    }
    
    public String getId(){
        return id;
    }
    
    public Integer getStatus() {
        return status;
    }

    public ErrorResponse setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public ErrorResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public ErrorResponse setProperty(String property) {
        this.property = property;
        return this;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }
    
    /**
     * Add a user friendly message to be show to user
     * @return
     */
    public ErrorResponse addErrorMessage(ErrorMessage e){
        errorMessages.add(e);
        return this;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public ErrorResponse setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
        return this;
    }
    
    public ErrorResponse reject(String type, String name, String description) {
        addErrorMessage(new ErrorMessage(type, name, description));
        return this;
    }    
    
    public ErrorResponse addMessage(String shortUserMessage) {
        addErrorMessage(new ErrorMessage(null, null, shortUserMessage));
        return this;
    }
    
    public ErrorResponse addDeveloperMessage(String developerMessage) {
        if (this.developerMessage == null) {
            this.developerMessage = "";
        }
        
        this.developerMessage += " " + developerMessage;
        return this;
    }
    
    @Override
    public String toString() {
        return "ErrorResponse [id=" + id + ", status=" + status + ", code=" + code + ", property=" + property + ", errorMessages="
                + errorMessages + ", developerMessage=" + developerMessage + "]";
    }

}
