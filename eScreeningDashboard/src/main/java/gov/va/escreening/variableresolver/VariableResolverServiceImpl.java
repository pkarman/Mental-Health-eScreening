package gov.va.escreening.variableresolver;

import gov.va.escreening.context.VeteranAssessmentSmrList;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

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

	@Resource(name = "veteranAssessmentSmrList")
	VeteranAssessmentSmrList smrLister;

	@Resource(name = "templateSmrNullHandler")
    NullValueHandler templateSmrNullHandler;
	@Resource(name = "exportDataSmrNullHandler")
    NullValueHandler exportDataSmrNullHandler;

    private static final Logger logger = LoggerFactory.getLogger(VariableResolverServiceImpl.class);
    
    //TODO: If we don't need to support the use of template ID and we can just use a template object that we already have queried, that would make this faster.
    @Override
	public List<AssessmentVariableDto> resolveVariablesForTemplateAssessment(Integer veteranAssessmentId, Integer templateId) {
    	
    	List<AssessmentVariableDto> assessmentVariableDtos = new ArrayList<AssessmentVariableDto>();
    	
    	Template template = templateRepository.findOne(templateId);
    	if(template != null){ //process the requested templateType
    	    
    	    //Initialize the resolver parameters
            List<VariableTemplate> variableTemplates = template.getVariableTemplateList();
            ResolverParameters params = new ResolverParameters(veteranAssessmentId, exportDataSmrNullHandler, variableTemplates);
            
            //add all assessment responses
            VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
            params.addResponses(assessment.getSurveyMeasureResponseList());
            
            for(VariableTemplate variableTemplate : variableTemplates) {
                AssessmentVariable assessmentVariable = variableTemplate.getAssessmentVariableId();
                assessmentVariableDtos.addAll(resolveAssessmentVariable(assessmentVariable, params).asSet());
            }
    	}
    	
		return assessmentVariableDtos;
	}
    
    @Override
    public Iterable<AssessmentVariableDto> resolveVariablesFor(Integer veteranAssessmentId, 
            Collection<AssessmentVariable> dbVariables) {
        List<AssessmentVariableDto> assessmentVariableDtos = new ArrayList<AssessmentVariableDto>();
        
        // clear the smr cache before resolving variable for every assessment 
        smrLister.clearSmrFromCache();
        
        ResolverParameters params = new ResolverParameters(veteranAssessmentId, exportDataSmrNullHandler, dbVariables);
        
        //add responses from assessment
        VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
        params.addResponses(assessment.getSurveyMeasureResponseList());
        
        for(AssessmentVariable dbVariable : dbVariables){
            assessmentVariableDtos.addAll(resolveAssessmentVariable(dbVariable, params).asSet());
        }
        
        return assessmentVariableDtos;
    }
    
    @Override
    public Optional<AssessmentVariableDto> resolveAssessmentVariable(
            AssessmentVariable assessmentVariable, 
            ResolverParameters params){
        try {
            AssessmentVariableDto variableDto = assessmentVariableFactory.resolveAssessmentVariable(assessmentVariable, params);
            
            if(variableDto != null) {
                return Optional.of(variableDto);
            }
            else {
                throw new CouldNotResolveVariableException(
                        String.format("Could not resolve AssessmentVariable with id: %s for the VeteranAssessment id: %s", 
                                assessmentVariable.getAssessmentVariableId(), params.getAssessmentId()));
            }
        }
        catch(UnsupportedOperationException uoe) {
            logger.error(String.format("UnsupportedOperationException for assessmentVariableId: %s and Veteran Assessment id: %s, exception message was: %s", 
                    assessmentVariable.getAssessmentVariableId(), params.getAssessmentId(), uoe.getMessage()));
        }
        catch(AssessmentVariableInvalidValueException avive) {
            logger.error(String.format("AssessmentVariableInvalidValueException for assessmentVariableId: %s and Veteran Assessment id: %s, exception message was: %s", 
                    assessmentVariable.getAssessmentVariableId(), params.getAssessmentId(), avive.getErrorResponse().getLogMessage()));
        }
        catch(CouldNotResolveVariableException cnrve) {
            //This exception is not an error, it just means that a variable could not be resolved which is legitimate
            logger.debug(String.format("CouldNotResolveVariableException for assessmentVariableId: %s and Veteran Assessment id: %s, exception message was: %s", 
                    assessmentVariable.getAssessmentVariableId(), params.getAssessmentId(), cnrve.getMessage()));
        }
        return Optional.absent();
    }
}