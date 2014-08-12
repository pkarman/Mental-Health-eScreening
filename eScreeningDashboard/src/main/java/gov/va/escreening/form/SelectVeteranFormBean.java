package gov.va.escreening.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class SelectVeteranFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "The last 4 SSN is required")
    @Pattern(regexp = "\\d{4}", message = "The last 4 SSN is required")
    private String ssnLastFour;

    private String lastName;

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SelectVeteranFormBean() {

    }
}
