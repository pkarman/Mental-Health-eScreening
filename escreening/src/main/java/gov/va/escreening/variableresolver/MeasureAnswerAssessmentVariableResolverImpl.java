package gov.va.escreening.variableresolver;

import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor={CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class,
		UnsupportedOperationException.class, CouldNotResolveVariableValueException.class, UnsupportedOperationException.class, Exception.class})
public class MeasureAnswerAssessmentVariableResolverImpl implements MeasureAnswerAssessmentVariableResolver {

    //TODO refactor this to use the enum instead.
    public static final int CALCULATION_TYPE_NUMBER = 1;
    public static final int CALCULATION_TYPE_USER_ENTERED_NUMBER = 2;
    public static final int CALCULATION_TYPE_USER_ENTERED_STRING = 3;
    public static final int CALCULATION_TYPE_USER_ENTERED_BOOLEAN = 4;

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(MeasureAnswerAssessmentVariableResolverImpl.class);   
    
    @Override
    public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,
            ResolverParameters params){
        return resolveAssessmentVariable(assessmentVariable, params, null);
    }
    
    @Override
    public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,
            ResolverParameters params, Answer response) {
        //logger.trace("Resolving answer variable with AV ID: {}", assessmentVariable.getAssessmentVariableId());

        Integer avId = assessmentVariable.getAssessmentVariableId();
        params.checkUnresolvable(avId);
        AssessmentVariableDto variableDto = params.getResolvedVariable(avId);
        
        if(variableDto == null 
                || (response != null && response.getRowId() != null && ! response.getRowId().equals(variableDto.getRow()))){
            
            MeasureAnswer measureAnswer = assessmentVariable.getMeasureAnswer();
            if (measureAnswer == null)
                ErrorBuilder.throwing(AssessmentVariableInvalidValueException.class)
                    .toAdmin(String.format("An assessment variable with ID %s does not have an associated measure answer.", assessmentVariable.getAssessmentVariableId()))
                    .toUser("A system inconsistency was detected. Please call support")
                    .throwIt();
    
            if(response == null){
                //logger.trace("Resolving of an answer is not passing in a response object. This will result in only the first row value being used.");
                List<Answer>responses = params.getAnswerResponse(measureAnswer.getMeasureAnswerId());
                //this is a temporary fix until something better is figured out
                if(!responses.isEmpty()){
                    response = responses.get(0);
                }
            }
            
            try{
                if (response == null)
                    throw new CouldNotResolveVariableException(String.format("There is no reponse for MeasureAnswer with ID: %s",
                            measureAnswer.getMeasureAnswerId()));
        
                variableDto = new AssessmentVariableDto(assessmentVariable);
                
                //TODO: This is wrong (remove this after researching if some templates assume this):
                variableDto.setVariableId(getAnswerAssessmentVariableId(assessmentVariable, response.getAnswerId(), params));
                
                variableDto.setResponse(response);
                
                //TODO: remove the use of override text (two hand written templates still use it)
                variableDto.setOverrideText(params.getOverrideText(response));
                
                //add resolved Dto to the cache
                params.addResolvedVariable(variableDto);
                
                
            }
            catch(Exception e){
                params.addUnresolvableVariable(assessmentVariable.getAssessmentVariableId());
                throw e;
            }
        }
        //logger.trace("Resolved answer variable with AV ID: {} to:\n{}", assessmentVariable.getAssessmentVariableId(), variableDto);
        return variableDto;
    }

    private Integer getAnswerAssessmentVariableId(AssessmentVariable parentAssessmentVariable, Integer measureAnswerId,
                                                  ResolverParameters params) {

        //If this answer has a mapped AssessmentVariable use that, otherwise use the parent id.
        AssessmentVariable var = params.getAnswerAv(measureAnswerId);
        if(var != null) {
            return var.getAssessmentVariableId();
        }

        //It hasn't been mapped so use the parent's id
        return parentAssessmentVariable.getAssessmentVariableId();
    }
}