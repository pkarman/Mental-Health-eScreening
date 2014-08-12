package gov.va.escreening.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class AssessmentLoginFormBean implements Serializable {

    private static final long serialVersionUID = -1L;

    @NotEmpty(message = "The last 4 SSN is required")
    @Pattern(regexp = "\\d{4}", message = "The last 4 SSN is required")
    private String lastFourSsn;

    @Size(max = 30, message = "Last name must be no longer than 30 characters")
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @Size(max = 30, message = "Middle name must be no longer than 30 characters")
    private String middleName;

    @Pattern(regexp = "[01]\\d/[0-3]\\d/[12]\\d{3}", message = "Date of Birth must be in the following format MM/DD/YYYY")
    private String birthDate;

    private Boolean additionalFieldRequired;

    public String getLastFourSsn() {
        return lastFourSsn;
    }

    public void setLastFourSsn(String lastFourSsn) {
        this.lastFourSsn = lastFourSsn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getAdditionalFieldRequired() {
        return additionalFieldRequired;
    }

    public void setAdditionalFieldRequired(Boolean additionalFieldRequired) {
        this.additionalFieldRequired = additionalFieldRequired;
    }

    public AssessmentLoginFormBean() {
    }

}
