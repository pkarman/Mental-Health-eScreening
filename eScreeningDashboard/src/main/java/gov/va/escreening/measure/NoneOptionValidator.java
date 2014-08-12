package gov.va.escreening.measure;

import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.entity.MeasureAnswer;

/**
 * Validates an answer of type None by checking:<ul>
 * <li>if it the given answer is of type NONE</li>
 * <li>if the given answer is true</li>
 * <li>if the answer is of type NONE and is true, then makes sure that no other answers have also been submitted with a value of true.
 * 
 * @author Robin Carnow
 */
public class NoneOptionValidator extends AnswerProcessor {

    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        
        if(submission.getAnswerType() == Answer.Type.NONE 
            && "true".equalsIgnoreCase(submission.getResponse(rowId))){
        
            for(Integer otherAnswerId : submission.getDbAnswerIds()){

            	MeasureAnswer otherDbAnswer = submission.getDbAnswer(otherAnswerId);
            	
                Answer.Type otherAnswerType = Answer.Type.fromString(otherDbAnswer.getAnswerType());
                Answer otherUserAnswer = submission.getUserAnswer(otherAnswerId, rowId);
                
                boolean answerIsNotNone = otherAnswerType != Answer.Type.NONE;
                boolean userAnswerIsTrue = otherUserAnswer != null &&
                                           "true".equalsIgnoreCase(otherUserAnswer.getAnswerResponse());
                
                if(answerIsNotNone && userAnswerIsTrue){
                    submission.addValidationError("When the None option is marked true, no other options can be true");
                }
            }
        }
        
        processNext(submission, rowId);
    }

}
