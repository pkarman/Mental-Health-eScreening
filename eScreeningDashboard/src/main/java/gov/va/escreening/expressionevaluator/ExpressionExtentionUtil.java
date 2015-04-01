package gov.va.escreening.expressionevaluator;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Strings;

/**
 * This class unifies the functions we use to extend both our FreeMarker templates and Spring EL expressions.
 * This allows for the same code to be reused and the same business rule to be followed when manipulating
 * AssessmentVariableDto objects.
 * 
 * @author Robin Carnow
 *
 */
public class ExpressionExtentionUtil {
    private static final String NUMBER_FORMAT = "%sf";
    public static final String DEFAULT_VALUE = "notset";
    
    public Map<Integer, AssessmentVariableDto> variableMap = Collections.emptyMap();
    
    public ExpressionExtentionUtil setVariableMap(Map<Integer, AssessmentVariableDto> variableMap){
        this.variableMap = variableMap;
        return this;
    }
    
    /**
     * @param avId the variable ID needed
     * @return AssessmentVariableDto for the given ID, or null if that variable is unknown 
     * (not passed in {@link #setVariableMap(Map)}
     */
    public AssessmentVariableDto variable(Integer avId){
        return variableMap.get(avId);
    }
    
    /**
     * Calculates the number of years between now and the given date string (formatted as {@link AssessmentConstants.STANDARD_DATE_FORMAT})
     * @param veteranBirthDate
     * @return 
     */
    public String calculateAge(String veteranBirthDate) {
        DateTime today = DateTime.now();
         
        DateTime birthDate = AssessmentConstants.STANDARD_DATE_FORMAT.parseDateTime(veteranBirthDate);
            
        Interval interval = new Interval(birthDate, today);
        Period period = interval.toPeriod();
        
        int years = period.getYears();
        return String.valueOf(years);
    }
    
    /**
     * Gets the numerical value of the given list-typed variable.
     * For select measures this means the calculation vaule.
     * For text answers it means the veteran entered value
     * @param var
     * @return
     */
    public String getListVariableAsNumber(AssessmentVariableDto var){
        if(var == null){
            return DEFAULT_VALUE;
        }
        String result;
        
        if(var.getMeasureTypeId() != null){
            switch(var.getMeasureTypeId()){
                case AssessmentConstants.MEASURE_TYPE_FREE_TEXT:
                     result = getFreeTextAnswer(var, DEFAULT_VALUE);
                     if(result != null && result != DEFAULT_VALUE){
                         try{
                             Double.valueOf(result);
                             return String.format(NUMBER_FORMAT, result);
                         }
                         catch(Exception e){ return result; }
                     }
                     break;
                case AssessmentConstants.MEASURE_TYPE_SELECT_ONE:
                    AssessmentVariableDto response = getSelectOneAnswerVar(var);
                    if(response == null){
                        return DEFAULT_VALUE;
                    }
                    result = response.getCalculationValue();
                    if(result == null){
                        result = response.getOtherText();
                    }
                    if(result != null){
                        return String.format(NUMBER_FORMAT, result);
                    }
                    break;
                case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI:// we could sum the calculation values
                default: 
                    return "[Error: unsupported question type]";
            }
        }
        else{
            return "[Error: unsupported question type]";
        }
        
        return DEFAULT_VALUE;
    }
    
    public String getVariableValue(AssessmentVariableDto var){
        if(var == null || var.getValue() == null){
            return DEFAULT_VALUE;
        }
        if(var.getValue() == "true" || var.getValue() == "false" ){
            return var.getValue();
        }
        
        try{
            Double.valueOf(var.getValue());
            return String.format(NUMBER_FORMAT, var.getValue());
        }
        catch(Exception e){ /* ignore */}

        return var.getValue();
    }
    
    public String getFreeTextAnswer(AssessmentVariableDto var, String defaultValue){     
        AssessmentVariableDto answer = getFirstAnswer(var);
        if(answer == null){
            return defaultValue;
        }
        
        String result = getResponseText(answer);
        if(result != DEFAULT_VALUE){
            return result;
        }
        
        if(answer.getValue() != null){
            return answer.getValue();
        }
        
        return defaultValue;
    }
    
    /**
     * Extracts a value for a variable (will not work on parent type variables)<br/>
     * Logic taken from Freemarker template helper functions.
     * @param var
     * @return
     */
    public String getResponseText(AssessmentVariableDto var){
        if(var == null){
            return DEFAULT_VALUE;
        }
        
        if(!Strings.isNullOrEmpty(var.getOtherText())){
            return var.getOtherText();
        }
        
        if(!Strings.isNullOrEmpty(var.getOverrideText())){
            return var.getOverrideText();
        }
        
        if(!Strings.isNullOrEmpty(var.getDisplayText())){
            return var.getDisplayText();
        }
        
        return DEFAULT_VALUE;
    }
    
    public AssessmentVariableDto getSelectOneAnswerVar(AssessmentVariableDto var){
        return getFirstAnswer(var);
    }
    
    /**
     * Takes a table question and returns an array of Maps where each entry is a row/entry, and the Map 
     * at that location has a key for every question which was answered for that row.  
     * It was done this way for simple calculation of number of rows used by numberOfEntries function.
     * Note: in the future we can change the way we organize the table question children array so it is not so flat. This would required
     * the update of any templates which were hand made (currently template IDs are: 6,12,14,20,22)
     * @param table
     * @return the hash to be used in templates
     */
    public List<Map<String, AssessmentVariableDto>> createTableHash(AssessmentVariableDto table){
        if(table == null || table.getChildren() == null || table.getChildren().isEmpty()){
            return Collections.emptyList();
        }

        List<Map<String, AssessmentVariableDto>> rows = new ArrayList<>();

        for(AssessmentVariableDto qVar : table.getChildren()){
            if(qVar.getVariableId() != null && qVar.getChildren() != null && !qVar.getChildren().isEmpty()){
                Integer rowIndex = qVar.getChildren().get(0).getRow();
                if(rowIndex != null){
                    ensureSize(rows, rowIndex);
                    rows.get(rowIndex).put(qVar.getVariableId().toString(), qVar);
                }
            }
        }
        return rows;
    }
    
    /**
     * Ensures that rows has a row available for the given row index
     * @param rows
     * @param rowIndex
     */
    private void ensureSize(List<Map<String, AssessmentVariableDto>> rows,
            Integer rowIndex) {
        if(rowIndex >= rows.size()){
            for(int i = rows.size(); i <= rowIndex; i++){
                rows.add(new HashMap<String, AssessmentVariableDto>());
            }
        }
    }
    
    /**
     * Extract the first answer of the variable
     * @param var the parent (list type) AssessmentVariableDto we are working with
     * @return the first answer AssessmentVariableDto or null
     */
    private AssessmentVariableDto getFirstAnswer(AssessmentVariableDto var){
        if(var == null || var.getChildren() == null || var.getChildren().isEmpty()){
            return null;
        }
        
        return var.getChildren().get(0);
    }
    
    
}
