package gov.va.escreening.form;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CreateVeteranFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "The last 4 SSN is required")
    @Pattern(regexp = "\\d{4}", message = "The last 4 SSN is required")
    private String ssnLastFour;

    @Size(max = 30, message = "Veteran First Name must be 30 characters or less")
    private String firstName;

    @Size(max = 30, message = "Veteran Middle Name must be 30 characters or less")
    private String middleName;

    @Size(max = 30, message = "Veteran Last Name must be 30 characters or less")
    @NotEmpty(message = "Last name is required")
    private String lastName;

    private Date birthDate;

    @Size(max = 10, message = "Date of Birth must be 10 characters and MM/DD/YYYY")
    private String birthDateString;

    @Size(max = 10, message = "Phone Number must be 10 characters or less")
    private String phone;

    @Size(max = 10, message = "Office Phone Number must be 10 characters or less")
    private String officePhone;

    @Size(max = 10, message = "Cell Phone Number must be 10 characters or less")
    private String cellPhone;

    @Email(message = "Email must be a valid format")
    private String email;

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public void setBirthDateString(String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreateVeteranFormBean() {

    }

}
