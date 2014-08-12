package gov.va.escreening.validation;

import gov.va.escreening.form.AssessmentLoginFormBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AssessmentLoginFormBeanValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AssessmentLoginFormBean.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        AssessmentLoginFormBean assessmentLoginFormBean = (AssessmentLoginFormBean) obj;

        if (assessmentLoginFormBean.getAdditionalFieldRequired()) {

            if (assessmentLoginFormBean.getBirthDate() == null) {
                errors.rejectValue("birthDate", "birthDate", "Date of Birth is required.");
            }
            else {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false);

                try {
                    sdf.parse(assessmentLoginFormBean.getBirthDate());
                }
                catch (ParseException e) {
                    errors.rejectValue("birthDate", "birthDate", "A valid Date of Birth is required.");
                }
            }
        }
    }
}
