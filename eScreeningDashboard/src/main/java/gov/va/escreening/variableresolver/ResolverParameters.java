package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.CouldNotResolveVariableException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * A class to contain the settings and cached content needed to resolved 
 * AssessmentVariables into AssessmentVariableDto.<br/>
 * The purpose of this class is to greatly simplify our resolver code, and
 * to make it more efficient.<br/>
 * This class is not threadsafe.<br/>
 * 
 * @author Robin Carnow
 */
public class ResolverParameters {
    private static final Logger logger = LoggerFactory.getLogger(ResolverParameters.class);
                    
    private final Integer assessmentId;
    private final NullValueHandler nullHandler;
    //map from MeasureAnswer ID to Answer (DTO)
    private final ListMultimap<Integer, Answer> answerMap = LinkedListMultimap.create();
    //map from MeasureAnswer ID to AssessmentVariable
    private Map<Integer, AssessmentVariable> measureAnswerHash = Collections.emptyMap();
    //map from assessment variable ID to measureAnswer
    private Map<Integer, MeasureAnswer> avIdToMeasureAnswerMap = Collections.emptyMap();
    //Table of Answers: Row is Measure ID, Column is Row ID
    private final Table<Integer, Integer, List<Answer>> measureResponseTable = HashBasedTable.create();
    //map from AssessmentVariable ID to resolved AssessmentVariableDto.
    //This acts as a cache to accumulate resolved AVs
    private final Map<Integer, AssessmentVariableDto> avMap;
    //Set to track AVs that cannot be resolved
    private final Set<Integer> unresolvable = new HashSet<>();
    //Stores answer override text (this should be removed when we remove the use of override text)
    private final Map<Integer, Optional<String>> overrideMap = new HashMap<>();
    
    /**
     * @param veteranAssessmentId the assessment to resolve against
     * @param nullHandler the null handler to be used when a value is null
     * @param assessmentVariables the dependent assessment variables for the operation
     * (e.g. with rules AV dependencies are found in the rule_assessment_variable table)
     */
    public ResolverParameters(int veteranAssessmentId,
            NullValueHandler nullHandler,
            Collection<AssessmentVariable> assessmentVariables){
        assessmentId = veteranAssessmentId;
        this.nullHandler = nullHandler;
        
        createAnswerHash(assessmentVariables);
        avMap = Maps.newHashMapWithExpectedSize(assessmentVariables.size());
    }
    
    /**
     * @param veteranAssessmentId the assessment to resolve against
     * @param nullHandler the null handler to be used when a value is null
     * @param variableTemplates the dependent assessment variables for the operation
     * (e.g. with rules AV dependencies are found in the rule_assessment_variable table)
     */
    public ResolverParameters(int veteranAssessmentId,
            NullValueHandler nullHandler,
            List<VariableTemplate> variableTemplates){
        assessmentId = veteranAssessmentId;
        this.nullHandler = nullHandler;
        
        createAnswerHash(variableTemplates);
        avMap = Maps.newHashMapWithExpectedSize(measureAnswerHash.size());
    }
    
    /**
     * Sets responses into this object.  These can be retrieved with calls to the two 
     * {@link #getMeasureResponses(Integer)} and {@link #getMeasureResponses(Integer, Integer)} 
     * methods. <br/>
     * <b>Please note:</b> this method should be called before a call to {@link #addUnsavedResponses(List)}
     * @param responses 
     */
    public void addResponses(Collection<SurveyMeasureResponse> responses){
        if(responses == null){
            logger.warn("Null response collection was passed in.");
            return;
        }
        for(SurveyMeasureResponse response : responses){
            MeasureAnswer ma = response.getMeasureAnswer();
            Answer answer = new Answer(ma, response);
            Integer measureId = ma.getMeasure().getMeasureId();
            
            addMeasureResponse(measureId, answer);
        }
    }
    
    /**
     * Adds the answers found in the list of measures.  These answers supersede answers
     * from the database so calls to this method will clear any previously stored 
     * responses for each measure (either previous called to addResponses or 
     * this method)
     * @param responses list of Measure DTOs received from the client.
     */
    public void addUnsavedResponses(List<Measure> responses){
        if(responses == null){
            logger.warn("Null unsaved response list was passed in.");
            return;
        }
        
        //Measures can repeat in this list so we have to first 
        // iterate through the list and remove measures (constant time operation for rows)
        for(Measure response : responses){
            measureResponseTable.row(response.getMeasureId()).clear();
        }
        
        //add responses
        for(Measure response : responses){
            Integer measureId = response.getMeasureId();
            List<Answer> answers = response.getAnswers();
            if(answers != null){
                for(Answer answer : response.getAnswers()){
                    addMeasureResponse(measureId, answer);  
                }
            }
        }
    }
    
    /**
     * Ensures that there is a List in the given cell and then appends {@code answer} to it.
     * Will default row index to 0 if it is null in {@code answer}
     * @param measureId the ID of the measure this answer represents
     * @param answer the answer to add
     */
    private void addMeasureResponse(Integer measureId, Answer answer){
        Integer row = answer.getRowId() == null ? 0 : answer.getRowId();
        List<Answer> responseList = measureResponseTable.get(measureId, row);
        if(responseList == null){
            responseList = new LinkedList<>();
        }
        responseList.add(answer);
        
        measureResponseTable.put(measureId, row, responseList);
        answerMap.put(answer.getAnswerId(), answer);
    }
    
    /**
     * This method assumes that there is no row value set (this is not a table question)
     * @param measureId
     * @return a collection of answers for the given measure or an empty list if
     * that measure has not answers recorded.
     */
    public List<Answer> getMeasureResponses(Integer measureId){
        return getMeasureResponses(measureId, 0);
    }
    
    /**
     * Look up the set of answers for a measure. If the measure is a child of a
     * table question, use this method.
     * @param measureId ID of the measure to look up
     * @param rowIndex is the Table question row to look up
     * @return a collection of answers for the given measure at the given row or 
     * an empty list if that measure/row combination has not answers recorded.
     */
    public List<Answer> getMeasureResponses(Integer measureId, Integer rowIndex){        
        List<Answer> responseList = measureResponseTable.get(measureId, rowIndex);
        if(responseList == null){
            return Collections.emptyList();
        }
        return responseList;
    }
    
    /**
     * @param measureId ID of the measure 
     * @return the maximum number of rows for the given measure (zero if no result was recorded)
     */
    public Integer getMeasureRowCount(Integer measureId){
        return measureResponseTable.row(measureId).size();
    }
    
    /**
     * Because of table rows, for any measureAnswerId there can be a list of answers,
     * each with a unique row ID value.  This method returns this list (in insertion order)
     * of the answers for the given MeasureAnswer ID
     * @param measureAnswerId ID of the MeasureAnswer
     * @return List of answers which will be empty if no answer has been recorded. 
     * Null is not returned.
     */
    public List<Answer> getAnswerResponse(Integer measureAnswerId){
        return answerMap.get(measureAnswerId);
    }
    
    public AssessmentVariable getAnswerAv(Integer measureAnswerId){
        return measureAnswerHash.get(measureAnswerId);
    }

    /**
     * @param avId variable ID for a MeasureAnswer-typed AV
     * @return List of answers which will be empty if no answers were recorded
     * or if the given AV ID does not represent an MeasureAnswer.
     */
    public List<Answer> getVariableAnswers(Integer avId){
        MeasureAnswer ma = avIdToMeasureAnswerMap.get(avId);
        if(ma != null){
            return getAnswerResponse(ma.getMeasureAnswerId());
        }
        return Collections.emptyList();
    }
    
    
    public Integer getAssessmentId(){
        return assessmentId;
    }
    
    public NullValueHandler getNullValueHandler(){
        return nullHandler;
    }
    
    public void addResolvedVariable(AssessmentVariableDto variable){
        avMap.put(variable.getVariableId(), variable);
    }
    
    /**
     * @param avId AssessmentVariable ID to search for
     * @return if the given ID represents an assessment variable that 
     * has already been resolved, then the resolved dto will be returned.
     */
    public AssessmentVariableDto getResolvedVariable(Integer avId){
        return avMap.get(avId);
    }
    
    /**
     * @return an ImmutableMap from assessment variable ID to resolved {@link AssessmentVariableDto}
     */
    public Map<Integer, AssessmentVariableDto> getResolvedVariableMap(){
        return ImmutableMap.copyOf(avMap);
    }
    
    public void addUnresolvableVariable(Integer assessmentVariableId){
        unresolvable.add(assessmentVariableId);
    }
    
    /**
     * Used to assert that the given AV has not been found to be unresolvable
     * @param assessmentVariableId
     * @throws CouldNotResolveVariableException if the assessment variable with 
     * the given ID has been found to be unresolvable 
     */
    public void checkUnresolvable(Integer assessmentVariableId){
        if(unresolvable.contains(assessmentVariableId)){
            throw new CouldNotResolveVariableException(
                    "Unable to resolve the assessment variable with ID: " + assessmentVariableId);
        }
    }
    
    //TODO: This should be removed (only two template use OverrideText)
    public String getOverrideText(Answer response) {
        Optional<String> overrideValue = overrideMap.get(response.getAnswerId());
        if(overrideValue == null){
            overrideValue = Optional.fromNullable(getOverrideValue(response.getAnswerId()));
            overrideMap.put(response.getAnswerId(), overrideValue);
        }
        return overrideValue.orNull();
    }
    
    //TODO: this should be removed as soon as we stop using override values
    private String getOverrideValue(Integer answerId){
        AssessmentVariable answerVariable = getAnswerAv(answerId);
        if (answerVariable != null && !answerVariable.getVariableTemplateList().isEmpty()) {
            return Strings.emptyToNull(answerVariable.getVariableTemplateList().get(0).getOverrideDisplayValue());
        }
        return null;
    }    
    
    //TODO: Make this obsolete by updating our bookkeeping of AVs for Rules, Formula, and Templates to save not only the AVs used by any dependent AVs.
    //we need variable resolution to be as fast as possible so this calculation should be saved once when these system objects are saved
    private Set<AssessmentVariable> collectAnswerAvs(AssessmentVariable av, Set<AssessmentVariable> answerAvs){
        switch(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId()){
        
            case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
                for(AssessmentVarChildren child : av.getAssessmentVarChildrenList()){
                    collectAnswerAvs(child.getVariableChild(), answerAvs);
                }
                break;
            case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
                for(MeasureAnswer ma : av.getMeasure().getMeasureAnswerList()){
                    answerAvs.add(ma.assessmentVariable());
                }
                break;
            case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
                answerAvs.add(av);
                break;
        }       
        return answerAvs;
    }
    
    /**
     * Initializes the measureAnswerHash which maps from measureAnswerId to AssessmentVariable.<br/>
     * Accessed during AV DTO resolving by {@link #getAnswerAv(Integer)}<br/>
     * Note: This method may become deprecated <br/>
     * @param assessmentVariables Assessment Variables to create map with. This will overwrite previous calls
     */
    private final void createAnswerHash(Collection<AssessmentVariable> assessmentVariables){
        Set<AssessmentVariable> answerAvs = Sets.newHashSet();    
        
        //TODO: Make this loop obsolete by updating our bookkeeping of AVs for Rules, Formula, and Templates to save not only the AVs used by any dependent AVs.
        //we need variable resolution to be as fast as possible so this calculation should be saved once when these system objects are saved
        for(AssessmentVariable av : assessmentVariables){
            collectAnswerAvs(av, answerAvs);
        }
        
        measureAnswerHash = Maps.newHashMapWithExpectedSize(answerAvs.size());
        avIdToMeasureAnswerMap = Maps.newHashMapWithExpectedSize(answerAvs.size());
        for (AssessmentVariable variable : answerAvs) {
            MeasureAnswer measureAnswer = variable.getMeasureAnswer();
            if (measureAnswer != null){
                measureAnswerHash.put(measureAnswer.getMeasureAnswerId(), variable);
                avIdToMeasureAnswerMap.put(variable.getAssessmentVariableId(), measureAnswer);
            }
        } 
    }
    
    /**
     * Initializes the measureAnswerHash which maps from measureAnswerId to AssessmentVariable.<br/>
     * Accessed during AV DTO resolving by {@link #getAnswerAv(Integer)}<br/>
     * Note: This method may become deprecated 
     * @param assessmentVariables Assessment Variables to create map with. This will overwrite previous calls
     */
    private final void createAnswerHash(List<VariableTemplate> variableTemplates){
        Set<AssessmentVariable> variables = Sets.newHashSetWithExpectedSize(variableTemplates.size());
        
        for (VariableTemplate variableTemplate : variableTemplates) {
            variables.add(variableTemplate.getAssessmentVariableId());
        }
        
        createAnswerHash(variables);
    }
    
}
