package gov.va.escreening.expressionevaluator;

import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_FREE_TEXT;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_READ_ONLY;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_SELECT_MULTI;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_SELECT_ONE;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_TABLE;
import static gov.va.escreening.constants.AssessmentConstants.STANDARD_DATE_FORMAT;
import static gov.va.escreening.variableresolver.CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * This class unifies the functions we use to extend both our FreeMarker templates and Spring EL expressions.
 * This allows for the same code to be reused and the same business rule to be followed when manipulating
 * AssessmentVariableDto objects.
 * 
 * @author Robin Carnow
 *
 */
public class ExpressionExtentionUtil {
    public static final String DEFAULT_VALUE = "notset";
    //if this is changed update matrix-transformation.directive.js
    public static final String OTHER_INPUT_PLACEHOLDER = "$other_value$";
    public static final String NUMBER_FORMAT = "%s";
    
    private static final Logger logger = LoggerFactory.getLogger(ExpressionExtentionUtil.class);
    private static final String DEFAULT_PREFIX = "";
    private static final String DEFAULT_LASTPREFIX = "and ";
    private static final String DEFAULT_SUFFIX = ", ";
    private static final Boolean DEFAULT_SUFFIX_AT_END = Boolean.FALSE;
    
    private Map<Integer, AssessmentVariableDto> variableMap = Collections.emptyMap();
    
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
     * @param veteranBirthDate - this is a string because the expression evaluator resolves references before calling this method, but 
     * a better practice would be to use AssessmentVariableDto objects and the yearsFromDate. Since this method is rarely used 
     * and the formula editor does not easily support the use of transformations, we can keep this around.
     * @return a string containing the number of years between today and the given 
     * date ({@code veteranBirthDate} must be in the standard format). 
     */
    public static String calculateAge(String veteranBirthDate) {
        return yearsFromDate(veteranBirthDate);
    }
    
    /**
     * yearsFromDate transformation takes a variable's value and returns the number of years from the current date
     * It is important to note that what we need is the number of years from the date given by the veteran and the date of the
     * assessment. This function assumes that the templates for a veteran will be rendered at the end of the assessment.
     * @param date
     * @return
     */
    public String yearsFromDate(AssessmentVariableDto var){
        return yearsFromDate(getFreeTextAnswer(var, DEFAULT_VALUE));
    }
    
    /**
     * Converts formatted date string into number of years since that date
     * @param date 
     * @return a string containing the number of years between today and the given 
     * date ({@code date} must be in the standard format). 
     */
    public static String yearsFromDate(String date){
        if(date == null || DEFAULT_VALUE.equals(date)){
            return DEFAULT_VALUE;
        }
        
        DateTime today = DateTime.now();
        DateTime startDate = null;
        try{
            startDate = STANDARD_DATE_FORMAT.parseDateTime(date);
        }
        catch(Exception e){
            //The reason this is ignored is because of our old system of using override_display_value we are moving away from it but for now this code has to be robust enough to deal with this case
            logger.warn("Invalid date given: {}", date);
            return date;
        }
            
        Interval interval = new Interval(startDate, today);
        Period period = interval.toPeriod();
        
        int years = period.getYears();
        return String.valueOf(years);
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
    
    //This not used in templates or rules
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
        //TODO: We should be able to make the row map into an Integer key
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
     * Allows for generic testing of values to see if the given value is defined.
     * This is important in some cases where a boolean expressions are used
     * @param value the value to test. Object is used here because the type can be anything.
     * @return true if number is defined.
     */
    public boolean isDefined(Object value){
        return value != null && !DEFAULT_VALUE.equals(value);
    }
    
    /**
     * Checks to see if the first question variable was answered with the given answer variable
     * @param questionVariable the question variable which contains responses
     * @param answerVariable the answer variable we are looking for
     * @return true if the answer given is one of the responses in the of the question
     */
    public boolean isSelectedAnswer(AssessmentVariableDto questionVariable, 
                                    AssessmentVariableDto answerVariable){
        if(questionVariable == null || answerVariable == null || answerVariable.getVariableId() == null
                || questionVariable.getChildren()==null || questionVariable.getChildren().isEmpty()){
            return false;
        }
        
        for(AssessmentVariableDto responseVariable : questionVariable.getChildren()){
            if(answerVariable.getVariableId().equals(responseVariable.getVariableId())){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns a string which is generated using the given list of values and delimiter properties 
     * If there is only one element in the list it will be output without any prefix or suffix
     * @param parts the strings to join
     * @param prefix the prefix to use before every value except the last one
     * @param lastPrefix the prefix to use before the last value
     * @param suffix the suffix to be used 
     * @param includeSuffixAtEnd
     * @param defaultValue
     * @return
     */
    public String delimitList(@Nullable List<String> parts,
            @Nullable String prefix,
            @Nullable String lastPrefix,
            @Nullable String suffix, 
            @Nullable Boolean includeSuffixAtEnd,
            @Nullable String defaultValue){
        
        StringBuilder result = null;
        
        if(parts != null && !parts.isEmpty()){
            if(parts.size() == 1){
                return parts.get(0);
            }
            //do this only when we need it
            result = new StringBuilder();
            
            //set default values
            if(prefix == null){
                 prefix = DEFAULT_PREFIX;
            }
            if(lastPrefix == null){
               lastPrefix = DEFAULT_LASTPREFIX; 
            }
            if(suffix== null){
                suffix = DEFAULT_SUFFIX;
            }
            if(includeSuffixAtEnd == null){
                includeSuffixAtEnd = DEFAULT_SUFFIX_AT_END;
            }
            
            Iterator<String> partIter = parts.iterator();
            while(partIter.hasNext()){
                String val = partIter.next();
                
                result.append(partIter.hasNext() ? prefix : lastPrefix).append(val);
                if(partIter.hasNext() || includeSuffixAtEnd){
                    result.append(suffix);
                }
            }
        }
        if(result == null || result.length() == 0){
            return defaultValue == null ? DEFAULT_VALUE : defaultValue;
        }
        return result.toString();
    }
    
    public String delimitTableField(
            @Nullable AssessmentVariableDto table,
            @Nullable Number childQuestionId,
            @Nullable String prefix,
            @Nullable String lastPrefix,
            @Nullable String suffix,
            @Nullable Boolean includeSuffixAtEnd,
            @Nullable String defaultValue){
        
        String defaultVal = defaultValue == null ? DEFAULT_VALUE : defaultValue;
        
        if(childQuestionId==null || table == null 
                || table.getChildren() == null 
                || table.getChildren().isEmpty()){
            return defaultVal; 
        }
        
        //set default values
        if(prefix == null){
             prefix = DEFAULT_PREFIX;
        }
        if(lastPrefix == null){
           lastPrefix = DEFAULT_LASTPREFIX; 
        }
        if(suffix== null){
            suffix = DEFAULT_SUFFIX;
        }
        if(includeSuffixAtEnd == null){
            includeSuffixAtEnd = DEFAULT_SUFFIX_AT_END;
        }
        
        List<String> responses = collectColumnValues(table, childQuestionId);
        return delimitList(responses, prefix, lastPrefix, suffix, includeSuffixAtEnd, defaultVal);
    }
    
    /**
     * This transformation takes a table question and returns the number of entries given by the veteran
     * @param table
     * @return
     */
    public Integer numberOfEntries(@Nullable AssessmentVariableDto table){
        if(table == null){
            return 0;
        }
        return createTableHash(table).size();
    }
    
    /**
     * This is a helper function which replaces raw references to a table's child fields to a lookup 
     * in a tableHash which gives the value of the needed entry for the given rowIndex.  Normally
     * we are looping through table entries and the rowIndex gives the context needed to get the 
     * correct assessment variable object.
     * @param tableHash table hash created from a call to {@link #createTableHash(AssessmentVariableDto)}
     * @param tableChild the AV for the table's child question
     * @param rowIndex the row index to pull the actual AV containing the response for the row
     * @return the AV for the given child question and row; or null if no response exists
     */
    public AssessmentVariableDto getTableVariable(
            List<Map<String, AssessmentVariableDto>> tableHash, 
            @Nullable AssessmentVariableDto tableChildVar, 
            @Nullable Number rowIndex){

        Integer index = rowIndex == null ? 0 : rowIndex.intValue();
        
        if(tableChildVar != null && tableChildVar.getVariableId() != null){

            Map<String, AssessmentVariableDto> row = tableHash.get(index);
            if(row != null){
                Integer childVarId = tableChildVar.getVariableId();
                if(childVarId != null){
                    return row.get(childVarId.toString());
                }
            }        
        }
        return null;
    }
    
    /**
     * This transformation’s goal is to produce a list of text, one per question having at least one required column selected by the veteran
     * @param matrix question's dto
     * @param rowMeasureIdToOutputMap is a map from row measure ID to the text we should output if at least one column was selected by the veteran for the given question
     * @param columnAnswerIdList is a list of column answer IDs which we are testing to see if the veteran gave one of the responses
     * @return
     */
    public String delimitedMatrixQuestions(
            @Nullable AssessmentVariableDto matrix, 
            @Nullable Map<String, String>rowMeasureIdToOutputMap,
            @Nullable List<Number> columnAnswerIdList){
        
        if(matrix == null || rowMeasureIdToOutputMap == null || columnAnswerIdList == null){
            return "";
        }
        List<String> valList = new ArrayList<>();
        Set<Integer> columnSet = Sets.newHashSetWithExpectedSize(columnAnswerIdList.size());
        
        //this is necessary because FreeMarker is sending in a list of BigDecimal for columnAnswerIdList. Isn't type erasure grand! 
        for(Number answerId : columnAnswerIdList){
            columnSet.add(answerId.intValue());
        }
        
        //loop over each of the matrix question's child question assessment variables 
        for(AssessmentVariableDto question : matrix.getChildren()){
            //check current child question to see if its question's AV ID is found in rowVarIds
            if(question.getMeasureId() != null 
                    && question.getChildren() != null
                    && !question.getChildren().isEmpty()){
                String questionOutput = rowMeasureIdToOutputMap.get(question.getMeasureId().toString());
                if(!Strings.isNullOrEmpty(questionOutput)){
                    List<AssessmentVariableDto> responses = getSelectedResponses(question);
                    
                    //check question's response(s) to see one if one of them is in columnAnswerIds
                    for(AssessmentVariableDto response : responses){
                        if(response.getAnswerId() != null 
                            && columnSet.contains(response.getAnswerId())){
                            
                            //append the text found in rowMeasureIdToOutputMap for the current child question
                            if(!Strings.isNullOrEmpty(response.getOtherText())){
                                questionOutput = questionOutput.replace(OTHER_INPUT_PLACEHOLDER, response.getOtherText());
                            }
                            valList.add(questionOutput);
                        }
                    }
                }
            }
        }
        return delimitList(valList, null, null, null, null, "");
    }
    
    /**
     * Delimits given variable.  If there is nothing to delimit the given default value will be returned.
     * Checks variable type to use correct delimiter helper functions.
     * @param var
     * @param prefix
     * @param lastPrefix
     * @param suffix
     * @param includeSuffixAtEnd
     * @param defaultValue
     * @return delimited string of values
     */
    public String delimit(
            @Nullable AssessmentVariableDto var,
            @Nullable String prefix,
            @Nullable String lastPrefix,
            @Nullable String suffix,
            @Nullable Boolean includeSuffixAtEnd,
            @Nullable String defaultValue){
        
        List<String> valList = Collections.emptyList();
        
        if(var != null){
            if(CUSTOM_VETERAN_APPOINTMENTS.equals(var.getVariableId())){
                valList = getChildValues(var);
            }
            else if(var.getMeasureTypeId() != null 
                    && var.getMeasureTypeId().equals(MEASURE_TYPE_SELECT_MULTI)){
                valList = getChildDisplayText(var);
            }
            else{
                return "[Error: unsupported type to delimit]";
            }
        }
        
        return delimitList(valList, prefix, lastPrefix, suffix, includeSuffixAtEnd, defaultValue);
    }
    
    /**
     * Convenience method for when delimiting with default parameters
     * @param var
     * @return
     */
    public String delimit(@Nullable AssessmentVariableDto var, String defaultValue){
        return delimit(var, null, null, null, null, defaultValue);
    }
    
    /**
     * Supports the retrieval of a numerical value from a question (of types 1,2,3) or casting a value to be a number.
     * If measureTypeId is null then just casts the given value as number but if var is null, "not set" is returned.
     * The var is tested to make sure it is not an object.
     * if measureTypeId==2 or 3:  return the calculated value  
     * @param var
     * @return
     */
    public Double asNumber(AssessmentVariableDto var){
        if(var != null){
            if(var.getMeasureTypeId() != null){
                switch(var.getMeasureTypeId()){
                    case MEASURE_TYPE_FREE_TEXT:
                    case MEASURE_TYPE_READ_ONLY:
                         String result = getFreeTextAnswer(var, DEFAULT_VALUE);
                         return result == DEFAULT_VALUE ? null : parseDouble(result);
                    case MEASURE_TYPE_SELECT_ONE:
                    case MEASURE_TYPE_SELECT_MULTI:
                        return sumCalcValues(var);
                }
            }
            else{
                if(!Strings.isNullOrEmpty(var.getValue())){
                    return parseDouble(var.getValue());
                }
                else if(!Strings.isNullOrEmpty(var.getCalculationValue())){
                    return parseDouble(var.getCalculationValue());
                }
                
                else if(!Strings.isNullOrEmpty(var.getOtherText())){
                    return parseDouble(var.getOtherText());
                }
            }
        }
        return null;
    }
    
    /**
     * retrieves a value for the given Measure typed variable which is of the given measure type. 
     * For single select the answer's text should be returned
     * If the answer's type is "other" then the other value should be returned
     * For multi select - returns a comma delimited list
     * @param var
     * @return
     */
    public String getResponse(AssessmentVariableDto var){
        if(var == null){
            return DEFAULT_VALUE;
        }
        
        switch(var.getMeasureTypeId()){
            case MEASURE_TYPE_FREE_TEXT:
            case MEASURE_TYPE_READ_ONLY:
                 return getFreeTextAnswer(var, DEFAULT_VALUE);
            case MEASURE_TYPE_SELECT_ONE:
                return getSelectOneResponse(var);
            case MEASURE_TYPE_SELECT_MULTI:
                return delimit(var, DEFAULT_VALUE);
        }
        return "[Error: unsupported question type]";
    }
    
    /**
     * takes a custom variable and returns its value which can be a string, number, or array.
     * The variable ID is enough to know which custom variable we are getting.
     * @param var
     * @return
     */
    public String getCustomValue(AssessmentVariableDto var){
        if(var == null){
            return DEFAULT_VALUE;
        }
        
        if(CUSTOM_VETERAN_APPOINTMENTS.equals(var.getVariableId())){
            return delimit(var, DEFAULT_VALUE);
        }
        return getResponseText(var);
    }

    
    /**
     * Note: this is a bit different from the corresponding template function
     * @param var
     * @return the numerical value of the response or null if it cannot be evaluated.
     */
    public Double getFormulaValue(AssessmentVariableDto var){
        if(var == null || Strings.isNullOrEmpty(var.getValue())){
            return null;
        }
        
        return parseDouble(var.getValue());
    }
    
    /**
     * returns true if the variable is defined and has a value; false otherwise.
     * If type == 3 then at least one option must be set to true.
     * @param var
     * @return
     */
    public boolean wasAnswered(AssessmentVariableDto var){
        if(var == null){
            return false;
        }
        
        if(var.getMeasureTypeId() != null 
                && var.getMeasureTypeId().equals(MEASURE_TYPE_TABLE)){
            return wasAnswerNone(var) || numberOfEntries(var) > 0;
        }
        
        return getResponse(var) != DEFAULT_VALUE;
    }
    
    /**
     * returns the negation of wasAnswered
     * @param var
     * @return
     */
    public boolean wasntAnswered(AssessmentVariableDto var){
        return !wasAnswered(var);
    }
    
    /**
     * After a translation which turns a variable into a string this would be called
     * @param val
     * @return
     */
    public boolean wasAnswered(String val){
        if(DEFAULT_VALUE.equals(val)){
            return false;
        }
        return !Strings.isNullOrEmpty(val);
    }
    
    /**
     * returns the negation of wasAnswered
     * @param val
     * @return
     */
    public boolean wasntAnswered(String val){
        return ! wasAnswered(val);
    }
    
    /**
     * Returns true if the None typed answer was selected
     * @param var
     * @return
     */
    public boolean wasAnswerNone(AssessmentVariableDto var){
        if(var != null && var.getChildren() != null){
            for(AssessmentVariableDto answer : var.getChildren()){
                if("none".equals(answer.getType()) 
                        && "true".equals(answer.getValue())){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean wasAnswerNone(String var){
        return false;
    }
    
    /**
     * @return  the negation of wasAnswerNone
     */
    public boolean wasntAnswerNone(AssessmentVariableDto var){
        return !wasAnswerNone(var);
    }
    
    public boolean wasntAnswerNone(String var){
        return true;
    }
    
    /**
     * @return true if the formula can be evaluated 
     */
    public boolean formulaHasResult(AssessmentVariableDto var){
        if(var == null){
            return false;
        }
        return getFormulaValue(var) != null;
    }
    
    /**
     * @return the negation of {@link #formulaHasResult(AssessmentVariableDto)}
     */
    public boolean formulaHasNoResult(AssessmentVariableDto var){
        return !formulaHasResult(var);
    }   
    
    /**
     * @return true if the custom variable has some value
     */
    public boolean customHasResult(AssessmentVariableDto var){
        if(var == null){
            return false;
        }
        
        return getCustomValue(var) != DEFAULT_VALUE;
    }
    
    /**
     * @return the negation of customHasResult
     */
    public boolean customHasNoResult(AssessmentVariableDto var){
        return ! customHasResult(var);
    }
    
    /**
     * @return true if the translated (i.e. delimited) custom variable has some value
     */
    public boolean customHasResult(String val){
        if(val == null){
            return false;
        }
        
        return !val.isEmpty() && val != DEFAULT_VALUE;
    }
    
    /**
     * @return the negation of customHasResult
     */
    public boolean customHasNoResult(String val){
        return ! customHasResult(val);
    }
    
    /**
     * @return true if the value given has a value. Currently only supports string values
     */
    public boolean matrixHasResult(AssessmentVariableDto matrix){
        if(matrix != null && matrix.getChildren() != null){
            for(AssessmentVariableDto question : matrix.getChildren()){
                if(!getSelectedResponses(question).isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @return negation of matrixHasResult
     */
    public boolean matrixHasNoResult(AssessmentVariableDto matrix){
        return ! matrixHasResult(matrix);
    }
    
    /**
     * This transformation is what is called when a matrix has been delimited 
     * into a string and the user what's to know if there is any result.
     * @return true if the value given has a value. Currently only supports string values
     */
    public boolean matrixHasResult(String matrix){
        return !Strings.isNullOrEmpty(matrix) && !matrix.equals(DEFAULT_VALUE);
    }
    
    /**
     * @return negation of matrixHasResult
     */
    public boolean matrixHasNoResult(String matrix){
        return ! matrixHasResult(matrix);
    }
    
    /**
     * Eventually this method should be deprecated and the use of the answer DTO object should be used.
     * @param var variable to check
     * @param right answer ID of the answer we are looking for
     * @return true if one of the given variable's responses is equal to right.
     *  measureTypeId can be:
     *  2 - selectOne or
     *  3 - selectMulti.
     *  If var123 is null then false is returned.
     *  param right can be an answer object (not supported in UI right now), or an integer
     */
    public boolean responseIs(AssessmentVariableDto var, Number rightId){
        if(var != null && rightId != null && var.getMeasureTypeId() != null){
            if(var.getMeasureTypeId() == MEASURE_TYPE_SELECT_ONE ||
                    var.getMeasureTypeId() == MEASURE_TYPE_SELECT_MULTI){
                for(AssessmentVariableDto responseVariable : var.getChildren()){
                    //using intValue is needed because FreeMarker uses BigDecimal for numerical values
                    if(rightId.intValue() == responseVariable.getAnswerId().intValue()){
                        return true;
                    }
                }
            }
            else{
                //maybe this should be an exception?
                logger.warn("Unsuppored question type (ID: {})", var.getMeasureTypeId());
            }
        }
        return false;
    }
    
    /**
     * @return the negation of responseIs
     */
    public boolean responseIsnt(AssessmentVariableDto var, Number right){
        return !responseIs(var, right);
    }
    
    /**
     * Preferred method for checking what a select question's answer is
     * @param question the question variable
     * @param answer the answer variable
     * @return true if the given answer was the response found in the question
     */
    public boolean responseIs(AssessmentVariableDto question, AssessmentVariableDto answer){
        return isSelectedAnswer(question, answer);
    }
    
    /**
     * @return the negation of responseIs
     */
    public boolean responseIsnt(AssessmentVariableDto question, AssessmentVariableDto answer){
        return !responseIs(question, answer);
    }
    
    /**
     * @return the response to the select one or default value
     */
    private String getSelectOneResponse(AssessmentVariableDto var){
        if(var != null && var.getChildren() != null && !var.getChildren().isEmpty()){
            AssessmentVariableDto answer = getSelectOneAnswerVar(var);
            if(answer != null){
               return getResponseText(answer); 
            }
        }            
        return DEFAULT_VALUE;
    }
    
    /**
     * Sums all child calculation values if the given child has a value of true.
     * So this should be used with select questions
     * @param var
     * @return
     */
    private Double sumCalcValues(AssessmentVariableDto var){
        Double result = 0.0;
        if(var != null && var.getChildren() != null){
            for(AssessmentVariableDto response : var.getChildren()){
                if(!Strings.isNullOrEmpty(response.getCalculationValue()) 
                        && "true".equals(response.getValue())){
                    try{
                        result += Double.valueOf(response.getCalculationValue());
                    }
                    catch(Exception e){
                        logger.error("Calculation value {} could not be parsed to a number.",
                                response.getCalculationValue());
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * @return the list of AV objects containing for the select responses (i.e. the options set to true by the veteran).
     * The var given can be single or multi select.
     */
    private List<AssessmentVariableDto> getSelectedResponses(AssessmentVariableDto var){
        if(var != null && var.getChildren() != null){
            List<AssessmentVariableDto> result = new ArrayList<>(var.getChildren().size());
            for(AssessmentVariableDto answer : var.getChildren()){
                if("true".equals(answer.getValue())){
                    result.add(answer);
                }
            }
            return result;
        }
        return Collections.emptyList();
    }
    
    /**
     * Extracts a value for a variable (will not work on parent type variables)<br/>
     * Logic taken from Freemarker template helper functions.
     * @param var
     * @return
     */
    private String getResponseText(AssessmentVariableDto var){
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
    
    /**
     * Iterate over table rows and collect answers for the given question AV ID
     * @param table
     * @param columnQuestionId
     * @return
     */
    private List<String> collectColumnValues(
            @Nullable AssessmentVariableDto table, 
            @Nullable Number columnQuestionId){
        
        List<String> result = Collections.emptyList();
        if(table != null 
                && columnQuestionId != null 
                && !table.getChildren().isEmpty() 
                && !wasAnswerNone(table)){
            
            result = new ArrayList<>(table.getChildren().size());
            
            for(AssessmentVariableDto question : table.getChildren()){
                //check to see if the given child question is the one to output 
                if(columnQuestionId.intValue() == question.getMeasureId().intValue()
                        && question.getChildren() != null 
                        && !question.getChildren().isEmpty()){
                    String response = getResponse(question);
                    if(! Strings.isNullOrEmpty(response)){
                        result.add(response);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * @return an array of the 'value' field for each child of the given variable
     */
    private List<String> getChildValues(AssessmentVariableDto var){
        return collectFromChildren(var, valueGetter);
    }
    
    /**
     * Returns an array of the 'displayText' field for each child of the given variable.
     * This uses getResponseText to pull out the value which checks several fields
     * @param var
     * @return
     */
    private List<String> getChildDisplayText(AssessmentVariableDto var){
        return collectFromChildren(var, responseTextGetter);       
    }
    
    private Double parseDouble(String value){
        try{
            return Double.valueOf(value);
        }
        catch(Exception e){
            logger.warn("Unable to parse {} as a number.", value);
            return null;
        }
    }
    
    /**
     * Generic function which <i>maps</i> (from functional programming) a given function over the children of
     * the given parent.
     * @param parent the parent variable which may contain children
     * @param collector function which is applied to every child 
     * @return a list of the same size as the list of children in {@code parent}. The values in the list
     * correspond to the result of applying the collector function to each element.
     */
    private <O> List<O> collectFromChildren(AssessmentVariableDto parent, Function<AssessmentVariableDto,O> collector){
        if(parent != null && parent.getChildren() != null){
            List<O> result = new ArrayList<>(parent.getChildren().size());
            for(AssessmentVariableDto child : parent.getChildren()){
                result.add(collector.apply(child));
            }
            return result;
        }
        return Collections.emptyList();
    }
    
    /**
     * Function to extract a variable's value field value
     */
    private static Function<AssessmentVariableDto, String> valueGetter = new Function<AssessmentVariableDto, String>(){
        @Override
        public String apply(AssessmentVariableDto var) {
            if(var != null && !Strings.isNullOrEmpty(var.getValue())){
                return var.getValue();
            }
            return DEFAULT_VALUE;
        }
    };
    
    /**
     * Function to extract a variable's response text
     */
    private Function<AssessmentVariableDto, String> responseTextGetter = new Function<AssessmentVariableDto, String>(){
        @Override
        public String apply(AssessmentVariableDto var) {
            return getResponseText(var);
        }
    };
    
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
