package gov.va.escreening.domain;

import java.io.Serializable;

public class NameDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String firstName;
    private String middleName;
    private String lastName;

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

    public NameDto() {

    }

    @Override
    public String toString() {
        return "NameDto [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + "]";
    }

}
