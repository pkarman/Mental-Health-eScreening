package gov.va.escreening.template.function;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import gov.va.escreening.expressionevaluator.ExpressionExtentionUtil;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
/**
 * Freemarker helper function takes a table question and returns an array of Maps where each entry is a row/entry, and the Map 
 * at that location has a key for every question which was answered for that row.  
 * It was done this way for simple calculation of number of rows used by numberOfEntries function.
 * Note: in the future we can change the way we organize the table question children array so it is not so flat. This would required
 * the update of any templates which were hand made (currently template IDs are: 6,12,14,20,22)
 * 
 * @author Robin Carnow
 *
 */
public class TableHashCreator extends TemplateFunction implements TemplateMethodModelEx{
    private static ExpressionExtentionUtil extentionUtil = new ExpressionExtentionUtil();
    
    @Override
    public List<Map<String, AssessmentVariableDto>> exec(@SuppressWarnings("rawtypes") List params) 
            throws TemplateModelException{

        if(params == null || params.isEmpty()){
            return Collections.emptyList();
        }

        AssessmentVariableDto table = unwrapParam(params.get(0), AssessmentVariableDto.class);

        return extentionUtil.createTableHash(table);
    }
}
