package gov.va.escreening.service;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.constants.RuleConstants;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.rule.EventDto;
import gov.va.escreening.dto.rule.RuleDto;
import gov.va.escreening.dto.template.TemplateIfBlockDTO;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.AssessmentVariableType;
import gov.va.escreening.entity.Consult;
import gov.va.escreening.entity.DashboardAlert;
import gov.va.escreening.entity.Event;
import gov.va.escreening.entity.EventType;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.exception.EscreeningDataValidationException;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.ConsultRepository;
import gov.va.escreening.repository.DashboardAlertRepository;
import gov.va.escreening.repository.EventRepository;
import gov.va.escreening.repository.HealthFactorRepository;
import gov.va.escreening.repository.RuleRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.VeteranAssessmentMeasureVisibilityRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.ResolverParameters;
import gov.va.escreening.variableresolver.VariableResolverService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * Standard implementation of RuleProcessorService
 * 
 * @author Robin Carnow
 * 
 */
@Transactional
@Service
public class RuleServiceImpl implements RuleService {
    private static final Logger logger = LoggerFactory
            .getLogger(RuleServiceImpl.class);

    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private ConsultRepository consultRepository;
    @Autowired
    private HealthFactorRepository healthFactorRepository;
    @Autowired
    private DashboardAlertRepository dashboardAlertRepository;
    @Autowired
    private VariableResolverService variableResolverService;
    @Autowired
    private AssessmentVariableRepository avRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private VeteranAssessmentMeasureVisibilityRepository veteranAssessmentMeasureVisibilityRepository;
    @Autowired
    private SurveyMeasureResponseRepository surveyMeasureResponseRepository;
    @Autowired
    private VeteranAssessmentRepository veteranAssessmentRepository;
    @Autowired
    private AssessmentVariableService avService;
    
    @Override
    public void processRules(Integer veteranAssessmentId) {
        List<Rule> rules = ruleRepository
                .getRuleForAssessment(veteranAssessmentId);

        // TODO: this can be done in parallel
        Set<HealthFactor> healthFactorSet = new HashSet<HealthFactor>();
        Set<Consult> consultSet = new HashSet<Consult>();
        Set<DashboardAlert> alertSet = new HashSet<DashboardAlert>();

        for (Rule rule : filterTrue(rules, veteranAssessmentId)) {
            fireEventsFor(veteranAssessmentId, rule, healthFactorSet,
                    alertSet, consultSet);
        }

        VeteranAssessment veteranAssessment = veteranAssessmentRepository
                .findOne(veteranAssessmentId);

        veteranAssessment.setHealthFactors(healthFactorSet);
        veteranAssessment.setConsults(consultSet);
        veteranAssessment.setDashboardAlerts(alertSet);
        veteranAssessmentRepository.update(veteranAssessment);
    }

    @Override
    public Set<Rule> filterTrue(Collection<Rule> rules, Integer veteranAssessmentId){
        return filterTrue(rules, veteranAssessmentId, null);
    }

    @Override
    public Set<Rule> filterTrue(Collection<Rule> rules, AssessmentRequest assessmentRequest){
        return filterTrue(rules, assessmentRequest.getAssessmentId(), 
                assessmentRequest.getUserAnswers());
    }

    private Set<Rule> filterTrue(Collection<Rule> rules, 
            Integer veteranAssessmentId,
            @Nullable
            List<gov.va.escreening.dto.ae.Measure> unsavedResponses){
        Set<Rule> trueRules = new HashSet<>();

        Set<AssessmentVariable> variables = new HashSet<>();
        for(Rule r : rules){
            variables.addAll(r.getAssessmentVariables());
        }
     
        VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
        ResolverParameters params = new ResolverParameters(assessment, variables);
        
        //add response found for this assessment
        if(assessment.getSurveyMeasureResponseList() != null){
            List<SurveyMeasureResponse> responses = new ArrayList<>(assessment.getSurveyMeasureResponseList().size());
            for(SurveyMeasureResponse smr : assessment.getSurveyMeasureResponseList()){
                //we don't want to include responses copied from another assessment
                if(smr.getCopiedFromVeteranAssessment() == null){
                    responses.add(smr);
                }
            }
            params.addResponses(responses);
        }
        
        //add any newly saved response
        if(unsavedResponses != null)
            params.addUnsavedResponses(unsavedResponses);

        //filter rules
        for(Rule r : rules){
            if(evaluate(r, params)){
                trueRules.add(r);
            }
        }

        return trueRules;
    }

    /**
     * Evaluates the given Rule's logical expression and returns result.
     * @param rule
     * @return returns false if the expression evaluated to false or could not be evaluated
     */
    private boolean evaluate(Rule rule, ResolverParameters params) {
        //logger.debug("Evaluating rule {}", rule);

        AssessmentVariable variable = new AssessmentVariable();
        variable.setAssessmentVariableTypeId(new AssessmentVariableType(
                AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA));
        variable.setFormulaTemplate(rule.getExpression());
        variable.setAssessmentVariableId(-rule.getRuleId());
        variable.setAssessmentVarChildrenList(createAssessmentVarChildrenList(rule
                .getAssessmentVariables()));
        
        Optional<AssessmentVariableDto> evaluatedRule = variableResolverService.resolveAssessmentVariable(variable, params);
        if(evaluatedRule.isPresent()){
            return Boolean.parseBoolean(evaluatedRule.get().getValue());
        }

        return false;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateVisibilityForQuestions(Integer veteranAssessmentId,
            Collection<Measure> questions) {
        if (questions.isEmpty())
            return;

        VisibilityUpdate update = new VisibilityUpdate(veteranAssessmentId,
                questions);

        // get all events that relate to the given questions
        List<Event> events = eventRepository
                .getEventByTypeFilteredByObjectIds(
                        RuleConstants.EVENT_TYPE_SHOW_QUESTION,
                        update.getQuestionIds());

        // collect the rules we should run
        Set<Rule> rules = Collections.emptySet();
        for (Event e : events) {
            rules = Sets.union(rules, e.getRules());
        }

        //evaluate rules and commit
        update.setRules(rules).commitChanges();
    }


    private List<AssessmentVarChildren> createAssessmentVarChildrenList(
            Set<AssessmentVariable> variables) {
        List<AssessmentVarChildren> formulaDependencies = new ArrayList<AssessmentVarChildren>(
                variables.size());
        for (AssessmentVariable variable : variables) {
            AssessmentVarChildren vc = new AssessmentVarChildren();
            vc.setVariableChild(variable);
            formulaDependencies.add(vc);
        }
        return formulaDependencies;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDto> getEventsByType(int type){
        return toEventDtos(eventRepository.getEventByType(type));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDto> getAllEvents(){
        return toEventDtos(eventRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RuleDto> getRules() {
        return ruleRepository.findAllLight();
    }

    @Override
    @Transactional(readOnly = true)
    public RuleDto getRule(Integer ruleId) {
        return new RuleDto(getDbRule(ruleId)); 
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RuleDto createRule(RuleDto ruleDto) {
        Rule dbRule = updateRuleEntity(ruleDto, new Rule());
        ruleRepository.create(dbRule);
        ruleDto.setId(dbRule.getRuleId());
        return ruleDto;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RuleDto updateRule(Integer ruleId, RuleDto ruleDto) {
        Rule dbRule = updateRuleEntity(ruleDto, getDbRule(ruleId));
        ruleRepository.update(dbRule);
        return new RuleDto(dbRule);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteRule(Integer ruleId) {
        Rule rule = getDbRule(ruleId);
        rule.getEvents().clear();
        rule.getAssessmentVariables().clear();
        ruleRepository.update(rule);
        ruleRepository.delete(rule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDto> getRuleEvents(Integer ruleId) {
        Rule dbRule = getDbRule(ruleId);
        if(dbRule.getEvents() == null || dbRule.getEvents().isEmpty()){
            return Collections.emptyList();
        }

        List<EventDto> events = Lists.newArrayListWithExpectedSize(dbRule.getEvents().size());
        for(Event dbEvent : dbRule.getEvents()){
            events.add(new EventDto(dbEvent));
        }
        return events;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventDto addEventToRule(Integer ruleId, EventDto event) {
        Rule dbRule = getDbRule(ruleId);
        Event dbEvent = getDbEvent(event.getId());

        if(dbRule.getEvents() == null){
            dbRule.setEvents(ImmutableSet.of(dbEvent)); 
        }
        else{
            dbRule.getEvents().add(dbEvent);
        }

        ruleRepository.update(dbRule);
        return event;
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto getRuleEvent(Integer ruleId, Integer eventId) {
        Event dbEvent = getRuleEvent(getDbRule(ruleId), eventId);
        return new EventDto(dbEvent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteRuleEvent(Integer ruleId, Integer eventId) {
        Rule dbRule = getDbRule(ruleId);
        Event dbEvent = getRuleEvent(dbRule, eventId);

        dbRule.getEvents().remove(dbEvent);
        ruleRepository.update(dbRule);
    }

    /**
     * Fires all events for the given rule
     * 
     * @param rule
     */
    private void fireEventsFor(Integer veteranAssessmentId, Rule rule,
            Set<HealthFactor> healthFactorSet, Set<DashboardAlert> alertSet,
            Set<Consult> consultSet) {

        // TODO: this can be done in parallel (update VisibilityUpdate to be
        // thread safe if we do this)
        for (Event event : rule.getEvents()) {
            switch (event.getEventType().getEventTypeId()) {

            case RuleConstants.EVENT_TYPE_CONSULT:
                // add event's consult to assessment
                Consult consult = consultRepository.findOne(event
                        .getRelatedObjectId());
                if (consult == null) {
                    String message = String
                            .format("A event (with ID %d) references an invalid consult (with ID %d)",
                                    event.getEventId(),
                                    event.getRelatedObjectId());
                    logger.error(message);
                    throw new IllegalStateException(message);
                }
                //logger.debug("Adding consult {} to assessment {}", consult,
                //        veteranAssessmentId);
                consultSet.add(consult);
                break;

            case RuleConstants.EVENT_TYPE_HEALTH_FACTOR:
                // add event's health factor to assessment
                HealthFactor healthFactor = healthFactorRepository
                .findOne(event.getRelatedObjectId());
                if (healthFactor == null) {
                    String message = String
                            .format("A event (with ID %d) references an invalid health factor (with ID %d)",
                                    event.getEventId(),
                                    event.getRelatedObjectId());
                    logger.error(message);
                    throw new IllegalStateException(message);
                }

                if (healthFactor.getVistaIen() != null) {
                    //logger.debug("Adding health factor {} to assessment {}",
                    //        healthFactor, veteranAssessmentId);
                    healthFactorSet.add(healthFactor);
                }
                break;

            case RuleConstants.EVENT_TYPE_DASHBOARD_ALERT:
                // add event's dashboard alert system
                DashboardAlert alert = dashboardAlertRepository.findOne(event
                        .getRelatedObjectId());
                if (alert == null) {
                    String message = String
                            .format("A event (with ID %d) references an invalid dashboard alert (with ID %d)",
                                    event.getEventId(),
                                    event.getRelatedObjectId());
                    logger.error(message);
                    throw new IllegalStateException(message);
                }

                //logger.debug("Adding dashboard alert {} to assessment {}",
                //        alert, veteranAssessmentId);
                alertSet.add(alert);
                break;
            }
        }
    }


    /**
     * Handles the updating of question visibility by composing an adjacency
     * list to construct the directed graph of questions which are connected by
     * Rules (i.e. Variables point at parent questions and ShowQuestion typed
     * Events point at child questions)
     * 
     * @author Robin Carnow
     * 
     */
    private class VisibilityUpdate {
        private final int veteranAssessmentId;
        private final Map<Integer, Boolean> measureVis;
        private final Table<Integer, Integer, Boolean> questionTable;
        private Collection<Integer> allQuestions;

        private VisibilityUpdate(int veteranAssessmentId,
                Iterable<Integer> allQuestionIds) {
            this.veteranAssessmentId = veteranAssessmentId;
            questionTable = HashBasedTable.create();

            ImmutableList.Builder<Integer> listBuilder = ImmutableList
                    .builder();
            measureVis = Maps.newHashMap();
            for (Integer id : allQuestionIds) {
                measureVis.put(id, Boolean.TRUE);
                listBuilder.add(id);
            }

            this.allQuestions = listBuilder.build();
        }

        /**
         * Evaluates all rules, records results and returns this object for chaining.
         * @param allRules
         * @return
         */
        public VisibilityUpdate setRules(Set<Rule> allRules) {
            // evaluate the rules and update
            Set<Rule> trueRules = filterTrue(allRules, veteranAssessmentId);
            for (Rule r : Sets.difference(allRules, trueRules)) {
                addRuleResult(r, false);
            }

            for(Rule r : trueRules){
                addRuleResult(r, true);
            }

            return this;
        }

        private VisibilityUpdate(int veteranAssessmentId,
                Collection<Measure> allQuestions) {
            this.veteranAssessmentId = veteranAssessmentId;
            questionTable = HashBasedTable.create();

            ImmutableList.Builder<Integer> listBuilder = ImmutableList
                    .builder();
            measureVis = Maps.newHashMapWithExpectedSize(allQuestions.size());
            for (Measure m : allQuestions) {
                if (m != null) { // if we had the WYSIWYG creating measures and
                    // adding them with a 0 index to the page
                    // this wouldn't happen
                    measureVis.put(m.getMeasureId(), Boolean.TRUE);
                    listBuilder.add(m.getMeasureId());
                }
            }

            this.allQuestions = listBuilder.build();
        }

        private Collection<Integer> getQuestionIds() {
            return allQuestions;
        }

        /**
         * Every rule should be called with the method and its result.
         * 
         * @param r
         * @param result
         */
        private void addRuleResult(Rule r, boolean result) {
            Set<Event> events = r.getEvents();
            for (Event e : events) {
                EventType type = e.getEventType();
                if (type != null
                        && type.getEventTypeId() == RuleConstants.EVENT_TYPE_SHOW_QUESTION) {
                    Integer childMeasureId = e.getRelatedObjectId();
                    measureVis.put(childMeasureId, Boolean.FALSE);
                    if (result) {
                        Set<AssessmentVariable> variables = r
                                .getAssessmentVariables();
                        // add any measures to our adjacency matrix for
                        // assessment_variables that relate to a measure
                        for (AssessmentVariable variable : variables) {
                            Measure measure = variable.getMeasure();
                            if (measure == null) {
                                MeasureAnswer answer = variable
                                        .getMeasureAnswer();
                                if (answer != null) {
                                    measure = answer.getMeasure();
                                }
                            }
                            if (measure != null) {
                                Integer parentMeasureId = measure
                                        .getMeasureId();
                                if (parentMeasureId != null) {
                                    // the cell value we set doesn't matter
                                    questionTable.put(childMeasureId,
                                            parentMeasureId, Boolean.TRUE);
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Should be called when we are in the path finding phase of visibility
         * calculation. It should be called on each measureId.
         * 
         * @return
         */
        private Boolean addMeasureVis(Integer measureId,
                Map<Integer, Boolean> mVis) {
            if (Boolean.TRUE.equals(mVis.get(measureId)))
                return Boolean.TRUE;

            // row represents the parent measures that are reachable
            Map<Integer, Boolean> row = questionTable.row(measureId);
            for (Integer parentId : row.keySet()) {
                if (addMeasureVis(parentId, mVis)) {
                    mVis.put(measureId, Boolean.TRUE);
                    return Boolean.TRUE;
                }
            }
            mVis.put(measureId, Boolean.FALSE);
            return Boolean.FALSE;
        }

        private void commitChanges() {
            Set<Integer> hiddenMeasures = Sets
                    .newHashSetWithExpectedSize(allQuestions.size());
            for (Integer id : allQuestions) {
                if (!addMeasureVis(id, measureVis))
                    hiddenMeasures.add(id);
            }

            // update database
            veteranAssessmentMeasureVisibilityRepository
            .setMeasureVisibilityFor(veteranAssessmentId, measureVis);
            surveyMeasureResponseRepository.deleteResponsesForMeasures(
                    veteranAssessmentId, hiddenMeasures);
        }
    }

    /**
     * Retrieves a Rule's Event with some sanity checks
     * @param dbRule
     * @param eventId
     * @return
     */
    private Event getRuleEvent(Rule dbRule, Integer eventId){
        Event dbEvent = getDbEvent(eventId);
        if(!dbRule.getEvents().contains(dbEvent)){
            ErrorBuilder.throwing(EntityNotFoundException.class)
            .toAdmin("Rule (with ID: " + dbRule.getRuleId() + ") does not contain event (with ID: " + eventId + ")")
            .toUser("An invalid event request was made. Please call support.")
            .throwIt();
        }
        return dbEvent;
    }

    /**
     * Retrieves the Event entity with some sanity checks
     * @param eventId
     * @return
     */
    private Event getDbEvent(Integer eventId){
        if(eventId == null){
            ErrorBuilder.throwing(EscreeningDataValidationException.class)
            .toAdmin("Event ID is a required field")
            .toUser("An invalid event request was made. Please call support.")
            .throwIt();
        }

        Event dbEvent = eventRepository.findOne(eventId);
        if(dbEvent == null){
            ErrorBuilder.throwing(EntityNotFoundException.class)
            .toAdmin("There is no event on the system with ID: " + eventId)
            .toUser("An invalid event was requested. Please call support.")
            .throwIt();
        }
        return dbEvent;        
    }

    /**
     * Retrieves the Rule entity with some sanity checks
     * @param ruleId
     * @return
     */
    private Rule getDbRule(Integer ruleId){
        if(ruleId == null){
            ErrorBuilder.throwing(EscreeningDataValidationException.class)
            .toAdmin("Rule ID is a required field")
            .toUser("An invalid rule request was made. Please call support.")
            .throwIt();
        }

        Rule dbRule = ruleRepository.findOne(ruleId);
        if(dbRule == null){
            ErrorBuilder.throwing(EntityNotFoundException.class)
            .toAdmin("There is no rule on the system with ID: " + ruleId)
            .toUser("An invalid rule was requested. Please call support.")
            .throwIt();
        }
        return dbRule;
    }


    /**
     * Translates a list of event entities to a list of event dtos
     * @param dbEvents
     * @return
     */
    private List<EventDto> toEventDtos(List<Event> dbEvents){
        List<EventDto> eventDtos = Lists.newArrayListWithExpectedSize(dbEvents.size());
        for(Event dbEvent : dbEvents){
            eventDtos.add(new EventDto(dbEvent));
        }
        return eventDtos;
    }

    private Rule updateRuleEntity(RuleDto src, Rule dst){
        dst.setName(src.getName());

        TemplateIfBlockDTO condition = src.getCondition();
        //set json in dbRule
        dst.setCondition(condition);

        // translate condition into Spring EL and update expression
        Set<Integer> avIds = new HashSet<>();
        dst.setExpression(condition.translateToSpringEl(new StringBuilder(), avIds).toString());

        //update AVs associated with this rule
        setRuleAssessmentVariables(dst, avIds);

        return dst;
    }

    /**
     * Updates a rule's associated assessment variables to the set given.
     * @param rule
     * @param avIds
     */
    private void setRuleAssessmentVariables(Rule rule, Set<Integer> avIds) {
        //create set with both AVs given and the answer AVs
        Map<Integer, AssessmentVariable> varMap;
        if(rule.getAssessmentVariables() == null){
            varMap = Collections.emptyMap();
            rule.setAssessmentVariables(new HashSet<AssessmentVariable>());
        }
        else{
            varMap = Maps.newHashMapWithExpectedSize(rule.getAssessmentVariables().size());
            for(AssessmentVariable av : rule.getAssessmentVariables()){
                varMap.put(av.getAssessmentVariableId(), av);
            }
            rule.getAssessmentVariables().clear();
        }
        
        //Collect variables and added VariableTemplates to template
        rule.getAssessmentVariables().addAll(avService.collectAssociatedVars(avIds, varMap));
    }
}
