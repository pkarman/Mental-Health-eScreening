package gov.va.escreening.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class MyAccountFormBean implements Serializable {

    private static final long serialVersionUID = -8741010818962591990L;

    @NotEmpty(message = "Current Password is required")
    private String currentPassword;

    @Size(min = 8, max = 30, message = "New Password must be between 8 and 30 characters")
    @NotEmpty(message = "New Password is required")
    @Pattern(regexp = "^.*(?=.{8,})(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%&?]).*$", message = "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character (@#%$^ etc.), and be at least 8 characters.")
    private String newPassword;

    @Size(min = 8, max = 30, message = "Confirmed Password must be between 8 and 30 characters")
    @NotEmpty(message = "Confirmed Password is required")
    @Pattern(regexp = "^.*(?=.{8,})(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%&?]).*$", message = "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character (@#%$^ etc.), and be at least 8 characters.")
    private String confirmedPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public MyAccountFormBean() {

    }
}
