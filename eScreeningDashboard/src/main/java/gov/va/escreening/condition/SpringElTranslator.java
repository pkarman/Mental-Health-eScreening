package gov.va.escreening.condition;

import gov.va.escreening.dto.template.TemplateBaseContent;

import java.util.Set;

public class SpringElTranslator implements BlockTranslator {

    @Override
    public String translateCondition(String operator, TemplateBaseContent inLeft,
            TemplateBaseContent right, Set<Integer> avIds) {
        // TODO Auto-generated method stub
        return "condition placeholder";
        
        // transformations allowed:
        //free text with a date format
        
        //allow table child questions
        
        /*questions
        how will we support table: was answered?
        how are table question values resolved
        do we have to support checking if a child has an answer? (in templates we have the table block which allows for conditions on a row)
        ** we might allow child questions in conditions which will be true if any entry has that value
        ** we need to reference the table if a child is picked.
        * So we have to:
        ** see that it is a child question
        ** look up the table parent AV ID (using AssessmentVariableService??)
        ** add a call to a helper function which takes: the table AV, the child AV, some value or answer and returns true if any entry has that 
        ** this means that we will have to add the AVID of the table 
           
                
        */
        
        //not allowed:
        //matrix questions
        //entry count for a table
        
    }

}
