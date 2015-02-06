package gov.va.escreening.dto.ae;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureBaseProperties;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ListMultimap;


public class Measure implements Serializable, MeasureBaseProperties {

    /** These are the measure_type_id of measures we count as needing a response.
     * Be careful not to use this by setting it as a query parameter (hybernate does something stupid with it)*/
    public static final String COUNTED_MEASURE_TYPES = "1,2,3,4,6,7";
    
    private static final long serialVersionUID = 1L;

    private Integer measureId;
    private String measureText;
    private String measureType;
    private Integer displayOrder;
    private Boolean isRequired;
    private Boolean isVisible;
    private List<Answer> answers;
    private List<Validation> validations;
	private List<Measure> childMeasures;
    private List<List<Answer>> tableAnswers;
    private String vistaText;
    private String variableName;
    private Boolean isPPI;
    private Boolean isMha;

    public String getVistaText() {
        return vistaText;
    }

    public void setVistaText(String vistaText) {
        this.vistaText = vistaText;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Boolean getIsPPI() {
        return isPPI;
    }

    public void setIsPPI(Boolean isPPI) {
        this.isPPI = isPPI;
    }

    public Boolean getIsMha() {
        return isMha;
    }

    public void setIsMha(Boolean isMha) {
        this.isMha = isMha;
    }

	public Integer getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }

    public String getMeasureText() {
        return measureText;
    }

    public void setMeasureText(String measureText) {
        this.measureText = measureText;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
    
    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * @return A non-null list of answers
     */
    public List<Answer> getAnswers() {
        if(answers != null)
            return answers;
        return Collections.emptyList();
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Validation> getValidations() {
        return validations;
    }

    public void setValidations(List<Validation> validations) {
        this.validations = validations;
    }

    public List<Measure> getChildMeasures() {
        return childMeasures;
    }

    public void setChildMeasures(List<Measure> childMeasures) {
        this.childMeasures = childMeasures;
    }

    public List<List<Answer>> getTableAnswers() {
		return tableAnswers;
	}

	public void setTableAnswers(List<List<Answer>> tableAnswers) {
		this.tableAnswers = tableAnswers;
	}
	
    public Measure() {

    }
    
    public Measure(gov.va.escreening.entity.Measure dbMeasure, 
            @Nullable ListMultimap<Integer, SurveyMeasureResponse> surveyMeasureResponseMap, 
            Map<Integer, Boolean> measureVisibilityMap){
        measureId = dbMeasure.getMeasureId();
        measureText = dbMeasure.getMeasureText();
        measureType = dbMeasure.getMeasureType() != null ? dbMeasure.getMeasureType().getName() : null;
        isRequired = dbMeasure.getIsRequired();
        displayOrder = dbMeasure.getDisplayOrder();
        vistaText = dbMeasure.getVistaText();
        variableName = dbMeasure.getVariableName();
        isPPI = dbMeasure.getIsPatientProtectedInfo();
        isMha = dbMeasure.getIsMha();
        
        isVisible = measureVisibilityMap != null? measureVisibilityMap.get(dbMeasure.getMeasureId()):null;
        if(isVisible == null) //if only java had a default option in Map
            isVisible = Boolean.TRUE;
        
        gov.va.escreening.entity.Measure parent = dbMeasure.getParent();
        boolean parentIsTable = parent != null 
                && parent.getMeasureType().getMeasureTypeId() != null
                && parent.getMeasureType().getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_TABLE;
        
        // Populate the data validation rules.
        if (dbMeasure.getMeasureValidationList().size() > 0) {
            validations= new ArrayList<Validation>();

            for (MeasureValidation measureValidation : dbMeasure.getMeasureValidationList()) {
                Validation validation = new Validation();
                validation.setId(measureValidation.getMeasureValidationId());
                validation.setName(measureValidation.getValidation().getCode());

                if ("boolean".equalsIgnoreCase(measureValidation.getValidation().getDataType())
                        && measureValidation.getBooleanValue() != null) {
                    validation.setValue(measureValidation.getBooleanValue().toString());
                }
                else if ("number".equalsIgnoreCase(measureValidation.getValidation().getDataType())
                        && measureValidation.getNumberValue() != null) {
                    validation.setValue(measureValidation.getNumberValue().toString());
                }
                else {
                    validation.setValue(measureValidation.getTextValue());
                }

                validations.add(validation);
            }
        }
        
        //add tableAnswers if this is a child question and it has not been initialized 
        if(parentIsTable && tableAnswers == null){
            tableAnswers = new ArrayList<List<Answer>>();
        }
   
    	//add answers
        List<MeasureAnswer> dbAnswers = dbMeasure.getMeasureAnswerList();
        if(dbAnswers != null && !dbAnswers.isEmpty()){
            answers = new ArrayList<Answer>(dbAnswers.size());
            
            for (MeasureAnswer measureAnswer : dbAnswers) {
                List<SurveyMeasureResponse> answerResponses = surveyMeasureResponseMap == null ? null 
                        : surveyMeasureResponseMap.get(measureAnswer.getMeasureAnswerId());
                
                addAnswer(measureAnswer, answerResponses, parentIsTable);
                if(getVariableName() == null && dbMeasure.getMeasureType().getMeasureTypeId() != 
                        AssessmentConstants.MEASURE_TYPE_SELECT_MULTI 
                        && dbMeasure.getMeasureType().getMeasureTypeId() != 
                        AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX)
                {
                    variableName = measureAnswer.getExportName();
                }
            }
        }
        
        //add child measures
        if(dbMeasure.getChildren() != null && !dbMeasure.getChildren().isEmpty()){
            childMeasures = new ArrayList<Measure>(dbMeasure.getChildren().size());
            for(gov.va.escreening.entity.Measure childDbMeasure : dbMeasure.getChildren()){
                childMeasures.add(new Measure(childDbMeasure, surveyMeasureResponseMap, measureVisibilityMap));
            }
        }
    }
    
    /**
     * For each combination of MeasureAnswer and SurveyMeasureResponse, an answer is created and added to either answers or tableAnswers.
     * @param measureAnswer
     * @param answerResponses if null then the answer will not have response data.
     * @param addToTableAnswers if true then the answer will be added to tableAnswers else it will be added to answers.
     */
    private final void addAnswer(MeasureAnswer measureAnswer, 
            @Nullable List<SurveyMeasureResponse> answerResponses,
            boolean addToTableAnswers){
        
        if(answerResponses == null || answerResponses.isEmpty()){
            Answer answer = new Answer(measureAnswer, null);
            if(addToTableAnswers){
                addTableAnswer(0, answer);
            }
            else{
                answers.add(answer);
            }
        }
        else{
            //for this answer add each row's response to tableAnswers
            for(int i = 0; i < answerResponses.size(); i++){
                if(i >= answerResponses.size())
                    throw new IllegalArgumentException("Invalid measure answer submission.  Each cell in a row must be given");
                Answer answer = new Answer(measureAnswer, answerResponses.get(i));
                if(addToTableAnswers)
                    addTableAnswer(i, answer);
                else
                    answers.add(answer);
            }
        }
    }
    
    private void addTableAnswer(int row, Answer answer){
        //expand TableAnswers list as needed
        while(row >= tableAnswers.size()){
            tableAnswers.add(new ArrayList<Answer>());
        }
        tableAnswers.get(row).add(answer);
    }
    
	@Override
	public String toString() {
	    String children = "";
	    if(childMeasures != null){
	        for(Measure child : childMeasures){
	            children += child.toString() + "\n";
	        }
	    }
		return new StringBuilder("Measure [measureId=").append(measureId) 
		        .append(", measureText=").append(measureText) 
		        .append(", measureType=").append(measureType)
				.append(", isRequired=").append(isRequired)
				.append(", isVisibile=").append(isVisible)
				.append(", displayOrder=").append(displayOrder) 
				.append(", answers=").append(answers)
				.append(", validations=").append(validations)
				.append(", children:\n").append(children)
				.append("]").toString();
	}
}
