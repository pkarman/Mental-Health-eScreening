package gov.va.escreening.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class VeteranSearchFormBean implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer veteranId;

    @Size(max = 30, message = "Veteran Last Name must be 30 characters or less")
    private String lastName;

    @Size(max = 4, message = "SSN-4 must be 4 characters or less")
    private String ssnLastFour;

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public VeteranSearchFormBean() {

    }

    @Override
    public String toString() {
        return "VeteranSearchFormBean [veteranId=" + veteranId + ", lastName=" + lastName + ", ssnLastFour="
                + ssnLastFour + "]";
    }

}
