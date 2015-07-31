package gov.va.escreening.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class MapVeteranToVistaRecordFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer veteranId;
    private String selectedVeteranIen;

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public String getSelectedVeteranIen() {
        return selectedVeteranIen;
    }

    public void setSelectedVeteranIen(String selectedVeteranIen) {
        this.selectedVeteranIen = selectedVeteranIen;
    }

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

    public MapVeteranToVistaRecordFormBean() {

    }

    @Override
    public String toString() {
        return "MapVeteranToVistaRecordFormBean [veteranId=" + veteranId + ", selectedVeteranIen=" + selectedVeteranIen
                + ", ssnLastFour=" + ssnLastFour + ", lastName=" + lastName + "]";
    }

}
