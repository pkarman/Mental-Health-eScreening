package gov.va.escreening.measure;

import gov.va.escreening.dto.ae.Answer;

import org.apache.commons.lang3.StringUtils;

/**
 * Validates and adds options' Other values
 * 
 * @author Robin Carnow
 *
 */
public class OtherOptionAnswerProcessor extends AnswerProcessor {

    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        //set other as text of response if found
        if(submission.getAnswerType() == Answer.Type.OTHER) {
            Answer answer = submission.getUserAnswer(submission.getAnswerId(), rowId);
            //only take first 1000 chars
            String otherResponse = StringUtils.left( answer.getOtherAnswerResponse(), 1000);
            submission.getSurveyMeasureResponse().setOtherValue(otherResponse);
        }
        
        processNext(submission, rowId);
    }

}
