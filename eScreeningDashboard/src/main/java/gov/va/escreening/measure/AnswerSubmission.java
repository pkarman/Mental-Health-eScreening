package gov.va.escreening.measure;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.Answer.Type;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.ae.ValidationDataTypeEnum;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Facade class to contain all data needed to process an answer submission (validate and persistence)
 * 
 * @author Robin Carnow
 *
 */
public class AnswerSubmission {
    
    private final Answer userAnswer;
    private final Type answerType;
    private final Integer measureId;
    private final boolean isVisible;
    private final ValidationDataTypeEnum measureType;
    private final Multimap<Integer, Answer> userAnswerMap;
    private final Map<Integer, MeasureAnswer> dbAnswerMap;
    private final Map<String, MeasureValidation> validations;
    private final SurveyMeasureResponse measureResponse;
    private final ErrorResponse errorResponse;

    private AnswerSubmission(Answer userAnswer,
    		Multimap<Integer, Answer> userAnswerMap,
            Map<Integer, MeasureAnswer> dbAnswerMap,
            Map<String, MeasureValidation> validations,
            Integer measureId,
            ValidationDataTypeEnum measureType, 
            SurveyMeasureResponse surveyMeasureResponse, 
            ErrorResponse errorResponse, 
            boolean isVisible){
        
        this.userAnswer = userAnswer;
        if(!dbAnswerMap.containsKey(userAnswer.getAnswerId())){
            throw new IllegalStateException("Invalid answer ID given");
        }

        MeasureAnswer dbAnswer = dbAnswerMap.get(userAnswer.getAnswerId());
        this.answerType = Answer.Type.fromString(dbAnswer.getAnswerType());
        this.measureId = measureId; 
        this.measureType = measureType;
        
        this.userAnswerMap = userAnswerMap;
        this.dbAnswerMap = dbAnswerMap;
        this.validations = validations;
        this.measureResponse = surveyMeasureResponse;
        this.errorResponse = errorResponse;
        this.isVisible = isVisible;
    }
    
    public Integer getAnswerId(){
        return userAnswer.getAnswerId();
    }
    
    public Answer.Type getAnswerType(){
        return answerType;
    }
    
    public ValidationDataTypeEnum getMeasureType(){
        return measureType;
    }
    
    public boolean isVisible(){
        return isVisible;
    }
    
    public String getResponse(Integer rowId){
    	List<Answer> answers = getUserAnswers(getAnswerId());
    	if(rowId == 0)
    		return answers.get(0).getAnswerResponse();
    	
    	for(Answer answer : answers) {
    		if(answer.getRowId() == rowId) 
    			return answer.getAnswerResponse();
    	}
    	
    	//TODO throw an exception here!!!
    	return "";
    }
    
    public Answer getUserAnswer(Integer id, Integer index) {
    	List<Answer> answers = getUserAnswers(id);
    	return answers.get(index);
    }
    
    public List<Answer> getUserAnswers(Integer id) {
    	Collection<Answer> answers = userAnswerMap.get(id);
    	Object[] answerObjs = answers.toArray();
    	
    	List<Answer> convertedAnswers = new ArrayList<Answer>();
    	for(Object answerObj : answerObjs) {
    		convertedAnswers.add((Answer)answerObj);
    	}

    	return convertedAnswers;
    }
    
    public MeasureAnswer getDbAnswer(Integer id){
        return dbAnswerMap.get(id);
    }
    
    public Set<Integer> getDbAnswerIds(){
        return dbAnswerMap.keySet();
    }
    
    public boolean containsValidation(String name){
        return validations.containsKey(name.toLowerCase());
    }
    
    public MeasureValidation getValidation(String name){
        return validations.get(name.toLowerCase());
    }
    
    public SurveyMeasureResponse getSurveyMeasureResponse(){
        return measureResponse;
    }
    
    public void addValidationError(String description) {
        errorResponse.reject("measure", measureId.toString(), description);
    }
    
    //TODO: Add precondition checks on all set methods.
    public static class Builder{
        private Answer userAnswer;
        private Integer measureId;
        private final Map<Integer, Boolean> measureVisibilityMap;
        private ValidationDataTypeEnum measureType;
        //private Map<Integer, Answer> userAnswerMap;
        private Multimap<Integer, Answer> userAnswerMap;
        private Map<Integer, MeasureAnswer> dbAnswerMap;
        
        private Map<String, MeasureValidation> validations;
        private ErrorResponse errorResponse;
        private SurveyMeasureResponse surveyMeasureResponse;
        
        public Builder(Map<Integer, Boolean> measureVisibilityMap){
            this.measureVisibilityMap = checkNotNull(measureVisibilityMap);
        }
        
        public Builder setUserAnswer(Answer userAnswer){
            if(userAnswer == null)
                throw new NullPointerException("user answer is required");
            this.userAnswer = userAnswer;
            return this;
        }
        
        public boolean answerIdIsValid(){
            return dbAnswerMap.containsKey(userAnswer.getAnswerId());
        }
        
        public Builder setMeasure(Measure measure, 
                gov.va.escreening.entity.Measure dbMeasure){
            
            measureId = measure.getMeasureId();
            
            //create map from answer id to user answer
            userAnswerMap = HashMultimap.create();
            
            if(measure.getAnswers() != null) {
	            for(Answer answer : measure.getAnswers()) {
	                userAnswerMap.put(answer.getAnswerId(), answer);
	            }
            }
            
            //TODO: dbAnswerMap can be cached eventually
            //create map from answer id to database answer 
            dbAnswerMap = new HashMap<Integer, MeasureAnswer>();
            for(MeasureAnswer answer : dbMeasure.getMeasureAnswerList()) {
                dbAnswerMap.put(answer.getMeasureAnswerId(), answer);
            }
            
            //TODO: validations can be cached
            //create map from validation code to validation
            validations = new HashMap<String, MeasureValidation>();
            for(MeasureValidation validation : dbMeasure.getMeasureValidationList()){
                validations.put(validation.getValidation().getCode().toLowerCase(), validation);
            }
            
            // Set the default parsing rule to be String.
            measureType = ValidationDataTypeEnum.STRING;
            String typeName = dbMeasure.getMeasureType().getName();
            
            // Derive the correct parsing rule and storage field.
            if ("freeText".equalsIgnoreCase(typeName)) {
                
                if(validations.containsKey("datatype")){
                    String dataTypeName = validations.get("datatype").getTextValue();
                    
                    if (ValidationDataTypeEnum.BOOLEAN.toString().equalsIgnoreCase(dataTypeName)) {
                        measureType = ValidationDataTypeEnum.BOOLEAN;
                    }
                    else if (ValidationDataTypeEnum.NUMBER.toString().equalsIgnoreCase(dataTypeName)) {
                        measureType = ValidationDataTypeEnum.NUMBER;
                    }
                    else if (ValidationDataTypeEnum.DATE.toString().equalsIgnoreCase(dataTypeName)) {
                        measureType = ValidationDataTypeEnum.DATE;
                    }
                    else if (ValidationDataTypeEnum.EMAIL.toString().equalsIgnoreCase(dataTypeName)) {
                        measureType = ValidationDataTypeEnum.EMAIL;
                    }
                }             
            }
            else if ("selectOne".equalsIgnoreCase(typeName)) {
                measureType = ValidationDataTypeEnum.BOOLEAN;
            }
            else if ("selectMulti".equalsIgnoreCase(typeName)) {
                measureType = ValidationDataTypeEnum.BOOLEAN;
            }
            else if ("selectOneMatrix".equalsIgnoreCase(typeName)) {
                measureType = ValidationDataTypeEnum.BOOLEAN;
            }
            else if ("selectMultiMatrix".equalsIgnoreCase(typeName)) {
                measureType = ValidationDataTypeEnum.BOOLEAN;
            }
            else if ("tableQuestion".equalsIgnoreCase(typeName)) {
                measureType = ValidationDataTypeEnum.BOOLEAN;
            }
            
            return this;
        }
        
         public Builder setSurveyMeasureResponse(SurveyMeasureResponse surveyMeasureResponse){
            this.surveyMeasureResponse = surveyMeasureResponse;
            MeasureAnswer dbAnswer = dbAnswerMap.get(userAnswer.getAnswerId());
            surveyMeasureResponse.setMeasureAnswer(dbAnswer);
            return this;
        }
        
        public Builder setErrorResponse(ErrorResponse errorResponse){
            this.errorResponse = errorResponse;
            return this;
        }
        
        public AnswerSubmission build(){
            if(userAnswer == null || userAnswerMap == null || dbAnswerMap == null
                    || validations == null || measureType == null 
                    || surveyMeasureResponse == null || errorResponse == null)
                throw new IllegalStateException("Builder was not initialized.");
            
            Boolean isVisible = measureVisibilityMap.get(measureId);
            
            return new AnswerSubmission(userAnswer, userAnswerMap, 
                    dbAnswerMap, validations, 
                    measureId, measureType, 
                    surveyMeasureResponse, errorResponse, 
                    isVisible != null ? isVisible : true);
        }
    }
}
