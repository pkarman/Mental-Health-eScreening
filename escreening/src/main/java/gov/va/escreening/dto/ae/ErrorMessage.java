package gov.va.escreening.dto.ae;

import java.io.Serializable;

import com.google.common.base.Optional;

public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String name;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ErrorMessage() {

    }

    public ErrorMessage(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    /**
     * @return a user message (gives a simple message)
     */
    public Optional<String> getUserMessage(){
        String message = getDescription();
        if(message == null || message.trim().isEmpty()){
            message = getName();
            if(message == null || message.trim().isEmpty()){
                return Optional.absent();
            }
        }
        return Optional.of(message);
    }
    
    @Override
    public String toString() {
        return "ErrorMessage [type=" + type + ", name=" + name + ", description=" + description + "]";
    }

}
