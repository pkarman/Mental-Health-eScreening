package gov.va.escreening.measure;

import gov.va.escreening.dto.ae.ValidationDataTypeEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Simple Date validation which only works on measures of type DATE
 * 
 * @author Robin Carnow
 *
 */
public class DateValidator extends AnswerProcessor {
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    static{
        format.setLenient(false);
    }
    
    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        if(submission.getMeasureType() == ValidationDataTypeEnum.DATE) {
            String userResponse = submission.getResponse(rowId);
            
            if(userResponse != null){
                try {
                    format.parse(userResponse);
                }
                catch (ParseException e) {
                    submission.addValidationError("Invalid date value.");
                }
            }
        }
        
        processNext(submission, rowId);
    }
}
