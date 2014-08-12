package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class VistaBrokerUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String duz;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;

    public String getDuz() {
        return duz;
    }

    public void setDuz(String duz) {
        this.duz = duz;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public VistaBrokerUserInfo() {

    }

    @Override
    public String toString() {
        return "VistaBrokerUserInfo [duz=" + duz + ", firstName=" + firstName + ", middleName=" + middleName
                + ", lastName=" + lastName + ", suffix=" + suffix + "]";
    }

}
