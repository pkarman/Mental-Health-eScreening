package gov.va.escreening.service;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Collection;

/**
 * Service class used to query, manipulate, and evaluate Events and Rules (e.g. evaluate expressions, fire events).
 * 
 * @author Robin Carnow
 *
 */
public interface RuleService {

    /**
     * Evaluates all rules corresponding to the given responses.
     * 
     * @param responses usually this will be the set of responses that were just submitted and saved by the user.
     */
    public void processRules(Integer veteranAssessmentId, Collection<SurveyMeasureResponse> responses);

    /**
     * Evaluates any rule that dictates the visibility of the given questions.<br/>
     * If a rule is true then any associated question (i.e. via Event) that was passed in will become visible.<br/>
     * If a question is found to be hidden, then it's visibility will be set to hidden, and the responses for <br/>
     * the question will be removed.<br/>
     * Note: Using this method together with {@link processRules} will execute the same rules more than once.<br/>
     * @param veteranAssessmentId
     * @param questions
     */
    void updateVisibilityForQuestions(Integer veteranAssessmentId, Collection<Measure> questions);

    public abstract boolean evaluate(Integer veteranAssessmentId, Rule rule);
    
}
