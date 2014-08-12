package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

@Transactional(noRollbackFor={CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, UnsupportedOperationException.class, Exception.class})
public class VariableResolverServiceImpl implements VariableResolverService {

    @Autowired
    private AssessmentVariableDtoFactory assessmentVariableFactory;
    @Autowired
    private VeteranAssessmentRepository veteranAssessmentRepository;
    @Autowired
    private TemplateRepository templateRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(VariableResolverServiceImpl.class);
    
    
    @Override
	public List<AssessmentVariableDto> resolveVariablesForTemplateAssessment(Integer veteranAssessmentId, Integer templateId) {
    	
    	List<AssessmentVariableDto> assessmentVariableDtos = new ArrayList<AssessmentVariableDto>();
    	
    	Template template = templateRepository.findOne(templateId);
    	if(template != null){
    	    //process the requested templateType
            List<VariableTemplate> variableTemplates = template.getVariableTemplateList();
            
            //Used for resolving override values
            Map<Integer, AssessmentVariable> measureAnswerHash = assessmentVariableFactory.createMeasureAnswerTypeHash(variableTemplates);
            
            for(VariableTemplate variableTemplate : variableTemplates) {
                AssessmentVariable assessmentVariable = variableTemplate.getAssessmentVariableId();
                assessmentVariableDtos.addAll(resolveAssessmentVariable(assessmentVariable, veteranAssessmentId, measureAnswerHash).asSet());
            }
    	}
    	
		return assessmentVariableDtos;
	}
    
    @Override
    public Iterable<AssessmentVariableDto> resolveVariablesFor(Integer veteranAssessmentId, Iterable<AssessmentVariable> dbVariables) {
        List<AssessmentVariableDto> assessmentVariableDtos = new ArrayList<AssessmentVariableDto>();
        
        //Used for resolving override values
        Map<Integer, AssessmentVariable> measureAnswerHash = assessmentVariableFactory.createMeasureAnswerTypeHash(dbVariables);
        
        for(AssessmentVariable dbVariable : dbVariables){
            assessmentVariableDtos.addAll(resolveAssessmentVariable(dbVariable, veteranAssessmentId, measureAnswerHash).asSet());
        }
        
        return assessmentVariableDtos;
    }
    
    /**
     * 
     * @param assessmentVariable
     * @param veteranAssessmentId
     * @param measureAnswerHash
     * @return an {@code Optional} of the resolved dto. Will be absent of an allowable exception occurred.
     * @throws CouldNotResolveVariableException if the assessmentVariable cannot be resolved for the given veteranAssessmentId
     */
    private Optional<AssessmentVariableDto> resolveAssessmentVariable(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash){
        try {
            AssessmentVariableDto variableDto = assessmentVariableFactory.createAssessmentVariableDto(assessmentVariable, veteranAssessmentId, measureAnswerHash);
            
            if(variableDto != null) {
                return Optional.of(variableDto);
            }
            else {
                throw new CouldNotResolveVariableException(
                        String.format("Could not resolve AssessmentVariable with id: %s for the VeteranAssessment id: %s", 
                                assessmentVariable.getAssessmentVariableId(), veteranAssessmentId));
            }
        }
        catch(UnsupportedOperationException uoe) {
            logger.error(String.format("UnsupportedOperationException for assessmentVariableId: %s and Veteran Assessment id: %s, exception message was: %s", 
                    assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, uoe.getMessage()));
        }
        catch(AssessmentVariableInvalidValueException avive) {
            logger.error(String.format("AssessmentVariableInvalidValueException for assessmentVariableId: %s and Veteran Assessment id: %s, exception message was: %s", 
                    assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, avive.getErrorResponse().getLogMessage()));
        }
        catch(CouldNotResolveVariableException cnrve) {
            //This exception is not an error, it just means that a variable could not be resolved which is legitimate
            logger.debug(String.format("CouldNotResolveVariableException for assessmentVariableId: %s and Veteran Assessment id: %s, exception message was: %s", 
                    assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, cnrve.getMessage()));
        }
        return Optional.absent();
    }
}