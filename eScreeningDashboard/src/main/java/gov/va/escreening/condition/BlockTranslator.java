package gov.va.escreening.condition;

import gov.va.escreening.dto.template.TemplateBaseContent;

import java.util.Set;

public interface BlockTranslator {
    
    /**
     * Translates a condition into a string
     * @param operator 
     * @param inLeft the left operand
     * @param right the right operand
     * @param avIds the set of AssessmentVariable IDs which should be added to when an AV is found in the condition.
     * @return string representing the condition
     */
    public String translateCondition(String operator, TemplateBaseContent inLeft, TemplateBaseContent right, Set<Integer> avIds);
}
