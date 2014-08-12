package gov.va.escreening.dto;

import java.io.Serializable;
import java.util.Date;

public class SelectVeteranResultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer veteranId;
    private String veteranIen;
    private String lastName;
    private String middleName;
    private String firstName;
    private String ssnLastFour;
    private Date birthDate;

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public String getVeteranIen() {
        return veteranIen;
    }

    public void setVeteranIen(String veteranIen) {
        this.veteranIen = veteranIen;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public SelectVeteranResultDto() {

    }

    @Override
    public String toString() {
        return "SelectVeteranFormBean [veteranId=" + veteranId + ", veteranIen=" + veteranIen + ", lastName="
                + lastName + ", middleName=" + middleName + ", firstName=" + firstName + ", ssnLastFour=" + ssnLastFour
                + ", birthDate=" + birthDate + "]";
    }

}
