package gov.va.escreening.measure;

import gov.va.escreening.dto.ae.Answer;

/**
 * Validates that the number of inches for a height are from 0-11.
 * 
 * @author Robin Carnow
 *
 */
public class InchValidator extends AnswerProcessor {

    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        if(submission.getAnswerType() == Answer.Type.HEIGHT_INCHES){
            Long userResponse = submission.getSurveyMeasureResponse().getNumberValue();
            
            if(userResponse != null){
                if(userResponse < 0){
                    submission.addValidationError("The number of inches in a height must greater than or equal to zero");
                }
                else if(userResponse >= 12){
                    submission.addValidationError("Valid inches are from 0-11");
                }
            }
        }
        
        processNext(submission, rowId);
    }

}
