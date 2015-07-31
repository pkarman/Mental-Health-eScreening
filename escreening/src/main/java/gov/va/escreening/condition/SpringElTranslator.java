package gov.va.escreening.condition;

import gov.va.escreening.dto.template.TemplateAssessmentVariableDTO;
import gov.va.escreening.dto.template.VariableTransformationDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring Expression Language {@link BlockTranslator}
 * 
 * @author Robin Carnow
 *
 */
public class SpringElTranslator extends BlockTranslator {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SpringElTranslator.class);

    @Override
    protected StringBuilder createInitialContent(TemplateAssessmentVariableDTO left){
        StringBuilder translatedVar = new StringBuilder("variable(").append(left.getId()).append(")");
        
        // we are going to apply transformation of variable.
        if (left.getTransformations() != null && !left.getTransformations().isEmpty()) {
            for (VariableTransformationDTO transformation : left.getTransformations()) {
                StringBuilder s = new StringBuilder(transformation.getName());
                s.append("(").append(translatedVar);

                if (transformation.getParams() != null) {
                    for (String param : transformation.getParams()){
                        String trimmed = param.trim();
                        
                        //check to see if we need to quote the parameter value
                        if((trimmed.startsWith("{") && trimmed.endsWith("}"))){
                            //we don't quote when it is an array or map
                            s.append(",").append(param);
                        }
                        else if(trimmed.startsWith("[") && trimmed.endsWith("]")){
                            //replace the [ with { for Spring EL array literals
                            s.append(",{")
                             .append(trimmed.subSequence(1, trimmed.length()-1))
                             .append("}");
                        }
                        //this is done separately from the above statement because we must lower case booleans
                        else if(trimmed.equalsIgnoreCase("true") 
                                || trimmed.equalsIgnoreCase("false")){
                            s.append(",").append(param.toLowerCase());
                        }
                        else{

                            try{//if the param is a number don't put quotes around it
                                Double.parseDouble(param);
                                s.append(",").append(param);
                            }
                            catch(Exception e){
                                s.append(",\"").append(param).append("\"");
                            }
                        }
                    }
                }
                //replace translatedVar with transformed variable
                translatedVar = s.append(")");
            }
        }
        return translatedVar;
    }
        
    /*
     * TODO:
        * Add support for checking whether a table child has an answer? (in templates we have the table block which allows for conditions on a row but for the rule editor we don't)
        ** should we allow table child questions?
        ** we might allow child questions in conditions which will be true if any entry has that value
        ** we need to reference the table if a child is picked.
        ** Possible solution:
        *** 1. see that it is a child question
        *** 2. look up the table parent AV ID (using AssessmentVariableService??)
        *** 3. add a call to a helper function which takes: the table AV, the child AV, some value or answer and returns true if any entry has that 
        *** this means that we will have to add the AVID of the table 
    */
}
