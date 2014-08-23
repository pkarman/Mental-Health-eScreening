package gov.va.escreening.dto.ae;

import java.io.Serializable;

public class Validation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Validation(Integer id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Validation [id=" + id + ", name=" + name + ", value=" + value + "]";
    }
}
