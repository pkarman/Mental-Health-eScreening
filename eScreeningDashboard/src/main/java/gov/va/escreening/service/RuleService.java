package gov.va.escreening.service;

import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.rule.EventDto;
import gov.va.escreening.dto.rule.RuleDto;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.exception.EscreeningDataValidationException;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Service class used to query, manipulate, and evaluate Events and Rules (e.g. evaluate expressions, fire events).
 * 
 * @author Robin Carnow
 *
 */
public interface RuleService {

    /**
     * Evaluates all rules corresponding to the given assessment. Visibility (aka follow-up question) events
     * are not fired since this should be done as the veteran answers questions in real time.<br/>
     * <strong>Please note: survey measure responses which were copied from another assessment will 
     * not be available to the rules engine when these rules are evaluated.</strong>
     * @param veteranAssessmentId - the ID of the assessment to run rules for.
     * 
     */
    public void processRules(Integer veteranAssessmentId);

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

	/**
	 * This method will run each rule given and returns the rules which evaluated to true.<br/>
     * <strong>Please note: survey measure responses which were copied from another assessment will 
     * not be available to the rules engine when these rules are evaluated.</strong>
	 * @param rules all rules to filter
	 * @param veteranAssessmentId
	 * @param assessmentRequest the assessment request we are evaluating rules for
	 * @return a set of rules which evaluate to true
	 */
    public Set<Rule> filterTrue(Collection<Rule> rules, AssessmentRequest assessmentRequest);
    
    /**
     * This method will run each rule given and returns the rules which evaluated to true.<br/>
     * <strong>Please note: survey measure responses which were copied from another assessment will 
     * not be available to the rules engine when these rules are evaluated.</strong>
     * @param rules all rules
     * @param veteranAssessmentId assessment variable ID we are evaluating rules for
     * @return a set of rules which evaluate to true
     */
    public Set<Rule> filterTrue(Collection<Rule> rules, Integer veteranAssessmentId);
    
	/**
	 * Get all events of the give type
	 * @param typeId ID of the type of events to return
	 * @return list of events defined on the system
	 * @throws EscreeningDataValidationException if type is non-null and invalid
	 */
	public List<EventDto> getEventsByType(int typeId);
	
	/**
	 * @return All events on the system (can be many)
	 */
	public List<EventDto> getAllEvents();
    
    /**
     * @return get all rules on the system <b>without the condition field set</b>
     */
    List<RuleDto> getRules();
    
    /**
     * Create a new rule
     * @param rule to create
     * @return the created rule
     */
    RuleDto createRule(RuleDto rule);
    
    /**
     * Retrieve the rule with the given ID
     * @param ruleId
     * @return the rule DTO
     * @throws EntityNotFoundException if not found
     */
    RuleDto getRule(Integer ruleId);
    
    /**
     * Update a rule using the given DTO's fields
     * @param ruleId ID of the rule to update 
     * @param rule the new values
     * @return the updated rule
     * @throws EntityNotFoundException if not found
     */
    RuleDto updateRule(Integer ruleId, RuleDto rule);
    
    /**
     * Delete rule with the given ID
     * @param ruleId 
     * @throws EntityNotFoundException if not found
     */
    void deleteRule(Integer ruleId);
    
    /**
     * Get the events for the given rule
     * @param ruleId ID of the rule
     * @return non-null list of events
     * @throws EntityNotFoundException if not found
     */
    List<EventDto> getRuleEvents(Integer ruleId);
    
    /**
     * Add the given event to the rule with the given ID
     * @param ruleId ID of the rule
     * @param event to add
     * @return the added event
     * @throws EntityNotFoundException if not found
     */
    EventDto addEventToRule(Integer ruleId, EventDto event);
    
    /**
     * Gets a rule's event
     * @param ruleId ID of the rule
     * @param eventId ID of the event 
     * @return the event requested
     * @throws EntityNotFoundException if: rule is not found, or rule does not have the given event
     */
    EventDto getRuleEvent(Integer ruleId, Integer eventId);
    
    /**
     * Remove event from rule
     * @param ruleId ID of rule to remove event from
     * @param eventId ID of event to remove 
     * @throws EntityNotFoundException if: rule is not found, or rule does not have the given event
     */
    void deleteRuleEvent(Integer ruleId, Integer eventId);
	
}
