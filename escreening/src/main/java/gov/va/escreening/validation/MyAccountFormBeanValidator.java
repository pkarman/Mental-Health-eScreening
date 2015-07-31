package gov.va.escreening.validation;

import gov.va.escreening.form.MyAccountFormBean;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MyAccountFormBeanValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MyAccountFormBean.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        MyAccountFormBean myAccountFormBean = (MyAccountFormBean) obj;

        if (myAccountFormBean.getNewPassword() != null
                && !myAccountFormBean.getNewPassword().equals(myAccountFormBean.getConfirmedPassword())) {

            errors.rejectValue("confirmedPassword", "myaccount.changepass.confirmedPassword.mismatch",
                    "The new password does not match the confirmed password.");
        }
    }
}
