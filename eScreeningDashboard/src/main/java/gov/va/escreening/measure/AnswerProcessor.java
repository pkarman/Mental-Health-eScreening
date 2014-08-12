package gov.va.escreening.measure;

/**
 * This class represents a link in a chain of processors each with the task of validating
 * and setting the correct value in a SurveyMeasureResponse.  If a given link is not 
 * responsible for processing a given {@link AnswerSubmission} then the submission is passed
 * to the next link.  This continues until the submission is processed (i.e. chain of responsibility pattern).
 * 
 * @author Robin Carnow
 *
 */
public abstract class AnswerProcessor {
    private AnswerProcessor nextProcessor;
    
    /**
     * Processes a submission by applying validations and setting 
     * values to be saved to database.
     * 
     * @param submission
     */
    public abstract void process(AnswerSubmission submission, Integer rowId);
    
    /**
     * Sets the given processor as the next in the chain of processors (after this one)
     * @return the same processor that was passed into this method.  This allows for simple chaining expressions.
     */
    public final AnswerProcessor setNext(AnswerProcessor processor){
        this.nextProcessor = processor;
        return processor;
    }
    
    protected final void processNext(AnswerSubmission submission, Integer rowId){
        if(nextProcessor != null)
            nextProcessor.process(submission, rowId);
    }

}
