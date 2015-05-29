package gov.va.escreening.condition;

import gov.va.escreening.dto.template.TemplateAssessmentVariableDTO;
import gov.va.escreening.dto.template.VariableTransformationDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FreeMarker {@link BlockTranslator}
 * 
 * @author Robin Carnow
 *
 */
public class FreeMarkerTranslator extends BlockTranslator {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerTranslator.class);

    @Override
    protected StringBuilder createInitialContent(TemplateAssessmentVariableDTO left){
        //add initial variable dereference value
        StringBuilder translatedVar = new StringBuilder("var").append(left.getId());
        
        // we are going to apply transformation of variable.
        if (left.getTransformations() != null && !left.getTransformations().isEmpty()) {
            for (VariableTransformationDTO transformation : left.getTransformations()) {
                StringBuilder s = new StringBuilder(transformation.getName());
                s.append("(").append(translatedVar);
    
                if (transformation.getParams() != null) {
                    for (String param : transformation.getParams()){
                        String trimmed = param.trim();
                        //check to see if we need to quote the parameter value
                        if((trimmed.startsWith("[") && trimmed.endsWith("]"))
                                || (trimmed.startsWith("{") && trimmed.endsWith("}"))){
                            //we don't quote when it is an array or map
                            s.append(",").append(param);
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
    
}
