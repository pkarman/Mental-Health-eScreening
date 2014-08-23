package gov.va.escreening.dto.editors;

/**
 * Created by pouncilt on 8/5/14.
 */
public class ValidationInfo {
    private Integer id;
    private String name;
    private String value;

    public ValidationInfo() {

    }

    public ValidationInfo(Integer id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

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
}
