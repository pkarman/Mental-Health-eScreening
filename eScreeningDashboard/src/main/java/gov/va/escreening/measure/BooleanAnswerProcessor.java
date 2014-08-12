package gov.va.escreening.measure;

import gov.va.escreening.dto.ae.ValidationDataTypeEnum;

/**
 * Handles processing boolean (multi-select and single) 
 * 
 * @author Robin Carnow
 *
 */
public class BooleanAnswerProcessor extends AnswerProcessor {
    //chain of child processors for string responses
    static private final AnswerProcessor childProcessor;
    static{
        childProcessor = new OtherOptionAnswerProcessor();
        childProcessor.setNext(new NoneOptionValidator());
    }
    
    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        if (submission.getMeasureType() == ValidationDataTypeEnum.BOOLEAN) {
            
            String userResponse = submission.getResponse(rowId);
            if ("true".equalsIgnoreCase(userResponse) 
                || "false".equalsIgnoreCase(userResponse)) {
                
                submission.getSurveyMeasureResponse()
                    .setBooleanValue(Boolean.parseBoolean(userResponse));
                
                //pass to child processors
                childProcessor.process(submission, rowId);
            }
            else {
                submission.addValidationError("Invalid boolean value.");
            }
        }
        else //pass it down the chain
            processNext(submission, rowId);
    }
}
