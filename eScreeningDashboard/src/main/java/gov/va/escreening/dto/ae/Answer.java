package gov.va.escreening.dto.ae;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Strings;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureAnswerBaseProperties;
import gov.va.escreening.entity.SurveyMeasureResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


public class Answer implements Serializable, MeasureAnswerBaseProperties {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(Answer.class);
    
    /* Possible answer types
     * These should all be lower case */
    public enum Type{
        NONE, OTHER, HEIGHT_FEET, HEIGHT_INCHES, TABLE_COLUMN;

        public static Type fromString(String name){
            if(name == null || name.isEmpty())
                return null;
            String lowerName = name.toLowerCase();
            if(lowerName.equals("none"))
                return NONE;
            if(lowerName.equals("other"))
                return OTHER;

            if(lowerName.equals("feet"))
                return HEIGHT_FEET;

            if(lowerName.equals("inches"))
                return HEIGHT_INCHES;

            if(lowerName.equals("tablecolumn"))
                return TABLE_COLUMN;

            throw new IllegalArgumentException("Invalid Answer type: " + name);
        }
    }

    private static final long serialVersionUID = 1L;

    private Integer answerId;
    private String answerText;
    private String answerType;
    private String answerResponse;
    private String vistaText;
    private String exportName;
    private String otherAnswerResponse;
    //set to answerResponse when this is a text answer, otherwise set to answerText 
    private String answerDisplayResponse;
    private Integer rowId;
    private String calculationType;
    private String calculationValue;
    private Integer displayOrder;
    private String mhaValue;

    @Override
    public String getCalculationValue() {
        return calculationValue;
    }

    @Override
    public void setCalculationValue(String calculationValue) {
        this.calculationValue = calculationValue;
    }

    
    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    @Override
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String getMhaValue() {
        return mhaValue;
    }

    @Override
    public void setMhaValue(String mhaValue) {
        this.mhaValue = mhaValue;
    }

    @Override
    public String getVistaText() {
        return vistaText;
    }

    @Override
    public void setVistaText(String vistaText) {
        this.vistaText = vistaText;
    }

    @Override
    public String getExportName() {
        return exportName;
    }

    @Override
    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    @Override
    public Integer getAnswerId() {
        return answerId;
    }

    @Override
    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    @Override
    public String getAnswerText() {
        return answerText;
    }

    @Override
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String getAnswerType() {
        return answerType;
    }

    @Override
    public void setAnswerType(String type) {
        this.answerType = type;
    }

    @Override
    public String getAnswerResponse() {
        return answerResponse;
    }

    @Override
    public void setAnswerResponse(String answerResponse) {
        this.answerResponse = answerResponse;
    }
    
    @Override
    public String getOtherAnswerResponse() {
        return otherAnswerResponse;
    }

    @Override
    public void setOtherAnswerResponse(String otherAnswerResponse) {
        this.otherAnswerResponse = otherAnswerResponse;
    }

    @Override
    public Integer getRowId() {
        return rowId;
    }
    
    @Override
    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    /**
     * Pseudo field used for AV Dto resolution.
     * Takes care of what an answer's display text should be 
     * when used in templates.
     * @return set to answerResponse when this is a text answer, 
     * otherwise set to answerText. Returns null if no response 
     * has been set.
     */
    public String getAnswerDisplayResponse() {
        return answerDisplayResponse;
    }
    
    private void setAnswerDisplayResponse(MeasureAnswer measureAnswer){
        if(measureAnswer.getMeasure() == null){
            throw new IllegalArgumentException("Answer " + measureAnswer.getMeasureAnswerId() 
                    + " is not associated with a measure. This means it was deleted using our forms editor. The call to this method should have checked for that.");
        }
        
        if(measureAnswer.getMeasure().getMeasureType().getMeasureTypeId() 
                == AssessmentConstants.MEASURE_TYPE_FREE_TEXT){
            answerDisplayResponse = answerResponse;
        }
        else{
            //The constraint has been removed which would set null here if the answer is of type none. Template functions do not assume
            //this business rule but it is possible that the handwritten templates do.  This constraint was lifted because it causes the
            //delimited output of select multi to throw error since null was being returned here for the display text.  PO would like to
            //show the text of the None answer so null should not be returned.
            answerDisplayResponse = answerText;
        }
        
        //logger.debug("Set answerDisplayResponse to: {}", answerDisplayResponse);
    }
    
    /**
     * @return true if the response is set and can be interpreted as a boolean
     */
    public boolean isTrue(){
        return Boolean.valueOf(answerResponse);
    }
    
    public Answer() {}

    public Answer(MeasureAnswer measureAnswer){        
        answerId = measureAnswer.getMeasureAnswerId();
        answerText = measureAnswer.getAnswerText();
        answerType = measureAnswer.getAnswerType();
        vistaText = measureAnswer.getVistaText();
        exportName = measureAnswer.getExportName();
        //TODO: Remove this when the use of calculation type is removed
        calculationType=measureAnswer.getCalculationType()==null?null:measureAnswer.getCalculationType().getName();
        calculationValue=measureAnswer.getCalculationValue();
        displayOrder =  measureAnswer.getDisplayOrder();
        mhaValue = measureAnswer.getMhaValue();
    }
    
    public Answer(MeasureAnswer measureAnswer, SurveyMeasureResponse measureResponse){
        this(measureAnswer);
        //logger.debug("Creating answer using measure answer {} with response {}", measureAnswer, measureResponse);
        
        checkNotNull(measureResponse, "measureResponse is required");
        
        //set user response
        rowId = measureResponse.getTabularRow();

        if (StringUtils.isNotBlank(measureResponse.getOtherValue())) {
            otherAnswerResponse = measureResponse.getOtherValue();
        }

        if (measureResponse.getNumberValue() != null) {                   
            answerResponse = measureResponse.getNumberValue().toString();
        }
        else if (measureResponse.getBooleanValue() != null) {
            answerResponse = measureResponse.getBooleanValue().toString();
        }
        else if (!Strings.isNullOrEmpty(measureResponse.getTextValue())) {
            answerResponse = measureResponse.getTextValue();
        }
  
        setAnswerDisplayResponse(measureAnswer);
    }
    
    /**
     * Constructor which builds an answer with the DB field values given by the measureAnswer and then applies any 
     * responses to the object.
     * @param measureAnswer contains the meta data fields from the database.
     * @param otherAnswer the other Answer object to pull response values from
     */
    public Answer(MeasureAnswer measureAnswer, Answer otherAnswer){
        this(measureAnswer);
        //logger.debug("Creating answer using measure answer {} and other answer {}", measureAnswer, otherAnswer);
        
        checkNotNull(otherAnswer, "otherAnswer is required");
        setRowId(otherAnswer.getRowId());
        setAnswerResponse(otherAnswer.getAnswerResponse());
        
        if (StringUtils.isNotBlank(otherAnswer.getOtherAnswerResponse())) {
            setOtherAnswerResponse(otherAnswer.getOtherAnswerResponse());
        }
        
        if(otherAnswer.getAnswerDisplayResponse() != null){
            answerDisplayResponse = otherAnswer.getAnswerDisplayResponse();
        }
        else{
            setAnswerDisplayResponse(measureAnswer);
        }
    }

    public Answer(Integer answerId, String answerText, String answerResponse) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.answerResponse = answerResponse;
        //logger.debug("Creating with: answerId {}, answerText {}, answerResponse {}", new Object[]{ answerId, answerText, answerResponse});
    }

    @Override
    public String toString() {
        return "Answer [answerId=" + answerId + ", answerText=" + answerText + ", hasOther=" + answerType
                + ", answerResponse=" + answerResponse + ", otherAnswerResponse=" + otherAnswerResponse + ", rowId:" + rowId 
                + ", displayOrder=" + displayOrder + ", calculationValue=" + calculationValue + ", mhaValue=" + mhaValue + "]";
    }

}
