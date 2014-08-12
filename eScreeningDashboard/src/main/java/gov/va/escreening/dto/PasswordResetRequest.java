package gov.va.escreening.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordResetRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    @Size(min = 8, max = 50, message = "Password must be at least 8 characters and less than 50 characters")
    @Pattern(regexp = "^.*(?=.{8,})(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%&?]).*$", message = "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character (@#%$^ etc.), and be at least 8 characters.")
    private String password;
    private String passwordConfirmed;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    public PasswordResetRequest() {
        // default constructor.
    }

    @Override
    public String toString() {
        return "PasswordResetRequest [userId=" + userId + ", password=" + password + ", passwordConfirmed="
                + passwordConfirmed + "]";
    }

}
