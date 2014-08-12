package gov.va.escreening.service;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.constants.RuleConstants;
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
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.repository.ConsultRepository;
import gov.va.escreening.repository.DashboardAlertRepository;
import gov.va.escreening.repository.EventRepository;
import gov.va.escreening.repository.HealthFactorRepository;
import gov.va.escreening.repository.RuleRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.VeteranAssessmentMeasureVisibilityRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.AssessmentVariableDtoFactory;
import gov.va.escreening.variableresolver.VariableResolverService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
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
    private static final Logger logger = LoggerFactory.getLogger(RuleServiceImpl.class);
    
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private ConsultRepository consultRepository;
    @Autowired
    private HealthFactorRepository healthFactorRepository;
    @Autowired
    private DashboardAlertRepository dashboardAlertRepository;
    @Autowired
    private VeteranAssessmentService veteranAssessmentService;
    @Autowired
    private VariableResolverService variableResolverService;
    @Autowired
    private AssessmentVariableDtoFactory assessmentVariableFactory;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private VeteranAssessmentMeasureVisibilityRepository veteranAssessmentMeasureVisibilityRepository;
    @Autowired
    private SurveyMeasureResponseRepository surveyMeasureResponseRepository;
    @Autowired
    private VeteranAssessmentRepository veteranAssessmentRepository;
    
    @Override
    public void processRules(Integer veteranAssessmentId, Collection<SurveyMeasureResponse> responses) {
        if(responses.isEmpty()){
            return;
        }
        Set<Measure> measureCollection = Sets.newHashSetWithExpectedSize(responses.size());
        for(SurveyMeasureResponse r: responses)
        {
            measureCollection.add(r.getMeasure());
        }
        updateVisibilityForQuestions(veteranAssessmentId, measureCollection);
        
        List<Rule> rules = ruleRepository.getRuleForAssessment(veteranAssessmentId);
        
        //TODO: this can be done in parallel
        Set<HealthFactor> healthFactorSet = new HashSet<HealthFactor>();
        Set<Consult> consultSet = new HashSet<Consult>();
        Set<DashboardAlert> alertSet = new HashSet<DashboardAlert>();     
        
        //get Rules that correspond to the given responses
        for(Rule rule : rules){
            
            //evaluate Rule
            logger.debug("evaluating rule: "+rule.toString());
            boolean result = evaluate(veteranAssessmentId, rule);
            
            if(result){
                fireEventsFor(veteranAssessmentId, rule, healthFactorSet, alertSet, consultSet );     	
            }
        }
        VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
       
        veteranAssessment.setHealthFactors(healthFactorSet);
        veteranAssessment.setConsults(consultSet);
        veteranAssessment.setDashboardAlerts(alertSet);
        veteranAssessmentRepository.update(veteranAssessment);
    }
    
    @Override
    public void updateVisibilityForQuestions(Integer veteranAssessmentId, Collection<Measure> questions){
         if(questions.isEmpty())
             return;
        
         VisibilityUpdate update = new VisibilityUpdate(veteranAssessmentId, questions);
         
         //get all events that relate to the given questions
         List<Event> events = eventRepository.getEventByTypeFilteredByObjectIds(RuleConstants.EVENT_TYPE_SHOW_QUESTION, 
                                                                                update.getQuestionIds());
         //collect the rules we should run
         Set<Rule> rules = Collections.emptySet();
         for(Event e : events){
             rules = Sets.union(rules, e.getRules());
         }
         
         //evaluate the rules and update 
         for(Rule r : rules){
             update.addRuleResult(r, evaluate(veteranAssessmentId, r));
         }
         
         update.commitChanges();
    }

    /**
     * Handles the updating of question visibility by composing an adjacency list  
     * to construct the directed graph of questions which are connected by Rules 
     * (i.e. Variables point at parent questions and ShowQuestion typed Events point at child questions)
     * @author Robin Carnow
     *
     */
    private class VisibilityUpdate{
        private final int veteranAssessmentId;
        private final Map<Integer, Boolean> measureVis;
        private final Table<Integer, Integer, Boolean> questionTable;
        private Collection<Integer> allQuestions;
        
        private VisibilityUpdate(int veteranAssessmentId, Iterable<Integer> allQuestionIds){
            this.veteranAssessmentId = veteranAssessmentId;
            questionTable = HashBasedTable.create();
            
            ImmutableList.Builder<Integer> listBuilder = ImmutableList.builder();
            measureVis = Maps.newHashMap();
            for(Integer id : allQuestionIds){
                measureVis.put(id, Boolean.TRUE);
                listBuilder.add(id);
            }
            
            this.allQuestions = listBuilder.build();
        }
        
        private VisibilityUpdate(int veteranAssessmentId, Collection<Measure> allQuestions){
            this.veteranAssessmentId = veteranAssessmentId;
            questionTable = HashBasedTable.create();
            
            ImmutableList.Builder<Integer> listBuilder = ImmutableList.builder();
            measureVis = Maps.newHashMapWithExpectedSize(allQuestions.size());
            for(Measure m : allQuestions){
                if(m != null){ //if we had the WYSIWYG creating measures and adding them with a 0 index to the page this wouldn't happen
                    measureVis.put(m.getMeasureId(), Boolean.TRUE);
                    listBuilder.add(m.getMeasureId());
                }
            }
            
            this.allQuestions = listBuilder.build();
        }
        
        private Collection<Integer> getQuestionIds(){
            return allQuestions;
        }
        
        /**
         * Every rule should be called with the method and its result.
         * @param r
         * @param result
         */
        private void addRuleResult(Rule r, boolean result){
            Set<Event> events = r.getEvents();
            for(Event e : events){
                EventType type = e.getEventType();
                if(type != null && type.getEventTypeId() == RuleConstants.EVENT_TYPE_SHOW_QUESTION){
                    Integer childMeasureId = e.getRelatedObjectId();
                    measureVis.put(childMeasureId, Boolean.FALSE);
                    if(result){
                        Set<AssessmentVariable>variables = r.getAssessmentVariables();
                        //add any measures to our adjacency matrix for assessment_variables that relate to a measure
                        for(AssessmentVariable variable : variables){
                            Measure measure = variable.getMeasure();
                            if(measure == null){
                                MeasureAnswer answer = variable.getMeasureAnswer();
                                if(answer != null){
                                    measure = answer.getMeasure();
                                }
                            }
                            if(measure != null){
                                Integer parentMeasureId = measure.getMeasureId();
                                if(parentMeasureId != null){
                                    //the cell value we set doesn't matter
                                    questionTable.put(childMeasureId, parentMeasureId, Boolean.TRUE);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        /**
         * Should be called when we are in the path finding phase of visibility calculation.
         * It should be called on each measureId.
         * @return
         */
        private Boolean addMeasureVis(Integer measureId, Map<Integer, Boolean> mVis){
            if(Boolean.TRUE.equals(mVis.get(measureId)))
                return Boolean.TRUE;
            
            //row represents the parent measures that are reachable
            Map<Integer, Boolean> row = questionTable.row(measureId);
            for(Integer parentId : row.keySet()){
                if(addMeasureVis(parentId, mVis)){
                    mVis.put(measureId, Boolean.TRUE);
                    return Boolean.TRUE;
                }
            }
            mVis.put(measureId, Boolean.FALSE);
            return Boolean.FALSE;
        }
        
        private void commitChanges(){
            Set<Integer> hiddenMeasures = Sets.newHashSetWithExpectedSize(allQuestions.size());
            for(Integer id : allQuestions){
                if(!addMeasureVis(id, measureVis))
                    hiddenMeasures.add(id);
            }
            
            //update database
            veteranAssessmentMeasureVisibilityRepository.setMeasureVisibilityFor(veteranAssessmentId, measureVis);
            surveyMeasureResponseRepository.deleteResponsesForMeasures(veteranAssessmentId, hiddenMeasures);
        }
    }
    
    
    /**
     * Evaluates the given Rule's logical expression and fires any associative events
     * @param rule
     */
    @Override
    public boolean evaluate(Integer veteranAssessmentId, Rule rule){
        
        /* Should support:
        for single select: a particular option was selected (if each value has a calculated value then that would work)
        for select multi: we have to know if the calculated value of an option was true
         */
        
        //TODO: this is hacky; if the AssessmentVariableDtoFactory is updated to allow for the resolution to the calculated value we can use that directly with expressionEvaluatorService
        AssessmentVariable variable = new AssessmentVariable();
        variable.setAssessmentVariableTypeId(new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA));
        variable.setFormulaTemplate(rule.getExpression());
        variable.setAssessmentVariableId(rule.getRuleId());
        variable.setAssessmentVarChildrenList(createAssessmentVarChildrenList(rule.getAssessmentVariables()));
        
        logger.debug("Evaluating rule {} with expression '{}'", rule, variable.getFormulaTemplate());
        boolean result = false;
        Map<Integer, AssessmentVariable> measureAnswerHash = assessmentVariableFactory.createMeasureAnswerTypeHash(rule.getAssessmentVariables());
        try{
            AssessmentVariableDto evaluatedRule = assessmentVariableFactory.createAssessmentVariableDto(variable, veteranAssessmentId, measureAnswerHash);
        
            result = Boolean.parseBoolean(evaluatedRule.getValue());
        }
        catch(CouldNotResolveVariableException e){
            //  we might want to throw the exception instead of returning false in the future
            logger.debug("Could not resolve variable for rule {}, message {}", rule, e.getMessage());
        }
        
        logger.debug("Rule {} is {}", rule, result);
        
        
        return result;
    }
    
    private List<AssessmentVarChildren> createAssessmentVarChildrenList(Set<AssessmentVariable> variables){
        List<AssessmentVarChildren> formulaDependencies = new ArrayList<AssessmentVarChildren>(variables.size());
        for(AssessmentVariable variable: variables){
            AssessmentVarChildren vc = new AssessmentVarChildren();
            vc.setVariableChild(variable);
            formulaDependencies.add(vc);
        }
        return formulaDependencies;
    }
    
    /**
     * Fires all events for the given rule
     * @param rule
     */
    private void fireEventsFor(Integer veteranAssessmentId, Rule rule, Set<HealthFactor> healthFactorSet,Set<DashboardAlert> alertSet,Set<Consult> consultSet){
        
        //TODO: this can be done in parallel (update VisibilityUpdate to be thread safe if we do this)
        for(Event event : rule.getEvents()){
            switch(event.getEventType().getEventTypeId()){
                    
                case RuleConstants.EVENT_TYPE_CONSULT:
                    //add event's consult to assessment
                    Consult consult = consultRepository.findOne(event.getRelatedObjectId());
                    if(consult == null){
                        String message = String.format("A event (with ID %d) references an invalid consult (with ID %d)", event.getEventId(), event.getRelatedObjectId());
                        logger.error(message);
                        throw new IllegalStateException(message);
                    }
                    logger.debug("Adding consult {} to assessment {}", consult, veteranAssessmentId );
                    consultSet.add(consult);
                    break;
                    
                case RuleConstants.EVENT_TYPE_HEALTH_FACTOR:
                    //add event's health factor to assessment
                    HealthFactor healthFactor = healthFactorRepository.findOne(event.getRelatedObjectId());
                    if(healthFactor == null){
                        String message = String.format("A event (with ID %d) references an invalid health factor (with ID %d)", event.getEventId(), event.getRelatedObjectId());
                        logger.error(message);
                        throw new IllegalStateException(message);
                    }
                    logger.debug("Adding health factor {} to assessment {}", healthFactor, veteranAssessmentId );
                    healthFactorSet.add(healthFactor);
                    break;
                    
                case RuleConstants.EVENT_TYPE_DASHBOARD_ALERT:
                    //add event's dashboard alert system
                    DashboardAlert alert = dashboardAlertRepository.findOne(event.getRelatedObjectId());
                    if(alert == null){
                        String message = String.format("A event (with ID %d) references an invalid dashboard alert (with ID %d)", event.getEventId(), event.getRelatedObjectId());
                        logger.error(message);
                        throw new IllegalStateException(message);
                    }
                    
                    logger.debug("Adding dashboard alert {} to assessment {}", alert, veteranAssessmentId );
                    alertSet.add(alert);
                    break;
            }
        }
    }
}
