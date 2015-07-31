package gov.va.escreening.measure;

import gov.va.escreening.dto.ae.Answer;

/**
 * Validates the number of feet in a field. Checks to make sure number is positive.
 * @author robin
 *
 */
public class FootValidator extends AnswerProcessor {

    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        if(submission.getAnswerType() == Answer.Type.HEIGHT_FEET){
            Long userResponse = submission.getSurveyMeasureResponse().getNumberValue(); 
            
            if(userResponse != null){
                if(userResponse <= 0){
                    submission.addValidationError("Height must larger than zero");
                }
            }
        }
        
        processNext(submission, rowId);
    }

}
