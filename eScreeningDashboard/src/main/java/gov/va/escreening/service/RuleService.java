package gov.va.escreening.service;

import gov.va.escreening.dto.EventDto;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

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

	public boolean evaluate(
			Rule r,
			Map<Integer, Pair<gov.va.escreening.entity.Measure, gov.va.escreening.dto.ae.Measure>> responseMap);
    
	/**
	 * Get all events of the give type
	 * @param type 
	 */
	public List<EventDto> getEventsByType(int type);
	
	public List<EventDto> getAllEvents();
}
