package gov.va.escreening.measure;

/**
 * Validates min and max lengths of responses (i.e. character count). 
 * If the response is null that is treated as a zero length response.
 * 
 * author Robin Carnow
 *
 */
public class LengthValidator extends AnswerProcessor {

    @Override
    public void process(AnswerSubmission submission, Integer rowId) {

        String userResponse = submission.getSurveyMeasureResponse().getTextValue();
        int responseLength = 0;
        if(userResponse != null)
            responseLength = userResponse.length();
        
        if (submission.containsValidation("minlength")){
            int minLength = submission.getValidation("minlength").getNumberValue();
            if (responseLength < minLength){
                submission.addValidationError("The number of characters must be greater than or equal to " + minLength);
            }
        }
        
        if (submission.containsValidation("maxlength")){
            int maxLength = submission.getValidation("maxlength").getNumberValue();
            if (responseLength > maxLength){
                submission.addValidationError("The number of characters must be less than or equal to " + maxLength);
            }
        }
        
        if (submission.containsValidation("exactlength")){
            int exactlength = submission.getValidation("exactlength").getNumberValue();
            if (responseLength != exactlength){
                submission.addValidationError("The acceptable number of characters is " + exactlength);
            }
        }
        
        //send to next processor
        processNext(submission, rowId);
    }
}
