package gov.va.escreening.measure;

/**
 * Validates number based responses to make sure the response lands within a range. It is assumed 
 * that the user response is a numberic value save in the SurveyMeasureResponse.
 * 
 * @author Robin Carnow
 *
 */
public class RangeValidator extends AnswerProcessor {

    @Override
    public void process(AnswerSubmission submission, Integer rowId) {

        Long userResponse = submission.getSurveyMeasureResponse().getNumberValue(); 

        if(userResponse != null){
            if (submission.containsValidation("minvalue")) {
                long minValue = submission.getValidation("minvalue").getNumberValue();
                if (userResponse < minValue) {
                    submission.addValidationError("The value must be greater than or equal to " + minValue);
                }
            }
            
            if (submission.containsValidation("maxvalue")) {
                long maxValue = submission.getValidation("maxvalue").getNumberValue();
                if (userResponse > maxValue) {
                    submission.addValidationError("The value must be less than or equal to " + maxValue);
                }
            }
        }
        
        //send to next every time
        processNext(submission, rowId);
    }
}
