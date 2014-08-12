package gov.va.escreening.dto.ae;

import java.io.Serializable;

public class Validation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Validation() {

    }

    public Validation(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Validation [name=" + name + ", value=" + value + "]";
    }

}
