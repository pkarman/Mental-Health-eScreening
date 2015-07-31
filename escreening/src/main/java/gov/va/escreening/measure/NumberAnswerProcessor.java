package gov.va.escreening.measure;

import gov.va.escreening.dto.ae.ValidationDataTypeEnum;

import org.apache.commons.lang3.StringUtils;

/**
 * Processor the deals with number type answers
 * 
 * @author Robin Carnow
 *
 */
public class NumberAnswerProcessor extends AnswerProcessor{
    //chain of child processors for number-type responses
    static private final AnswerProcessor childProcessor;
    static{
        childProcessor = new RangeValidator();
        childProcessor
            .setNext(new FootValidator())
            .setNext(new InchValidator());
    }
    
    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        if(submission.getMeasureType() == ValidationDataTypeEnum.NUMBER) {
            String userResponse = submission.getResponse(rowId);
            
            if (StringUtils.isNumeric(userResponse)) {
                try{
                    submission.getSurveyMeasureResponse().setNumberValue(Long.parseLong(userResponse));
                }
                catch(Exception e){
                    submission.addValidationError("Value is too long");
                }
            }
            else {
                submission.addValidationError("Invalid number value.");
                return;
            }
            
            //send down number-based child processor chain
            childProcessor.process(submission, rowId);
        }
        else{
            processNext(submission, rowId);
        }
    }
}
