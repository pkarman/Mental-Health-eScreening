package gov.va.escreening.measure;

/**
 * Processes the validation and setting of string type responses in SurveyMeasureResponse.<br/>
 * The child processor of this processor are based on String responses.
 * 
 * @author Robin Carnow
 *
 */
public class StringAnswerProcessor extends AnswerProcessor {
    //chain of child processors for string responses
    static private final AnswerProcessor childProcessor;
    static{
        childProcessor = new LengthValidator();
        childProcessor
            .setNext(new DateValidator())
            .setNext(new EmailValidator());
    }
    
    @Override
    public void process(AnswerSubmission submission, Integer rowId) {
        
        switch(submission.getMeasureType()){
            case STRING:
            case DATE:
            case EMAIL:
                submission.getSurveyMeasureResponse().setTextValue(submission.getResponse(rowId));  
                //we don't want to validate a text answer if it is a measure that is not showing currently on the page
                if(submission.isVisible())
                    childProcessor.process(submission, rowId);
                break;
            default:
                processNext(submission, rowId);
        }
    }
}
