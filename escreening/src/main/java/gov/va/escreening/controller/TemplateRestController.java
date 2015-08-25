package gov.va.escreening.controller;

import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.TemplateTypeDTO;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.exception.EscreeningDataValidationException;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.service.TemplateTypeService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/dashboard")
public class TemplateRestController extends RestController {

	private static final Logger logger = LoggerFactory
			.getLogger(TemplateRestController.class);

	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateTypeService templateTypeService;
	
    //TODO: when we have time it would probably be better to have the templateTypes be retrieved via the survey and battery controllers 
    // so something like this: /services/survey/{surveyId}/templateTypes/{templateTypeId}
    // this would make more sense in (and streamline) the UI code as well.
    
	@RequestMapping(value ="/services/templateTypes", params="surveyId", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TemplateTypeDTO> getModuleTemplateTypesBySurveyId(@RequestParam("surveyId") Integer surveyId, 
			HttpServletRequest request) {
		
		logRequest(logger, request);
		
        if(surveyId == null || surveyId < 0){
            ErrorBuilder.throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin("Could not find the template types with the survey with ID: " + surveyId)
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
                    .throwIt();
        }

        List<TemplateTypeDTO> templateTypes = templateTypeService.getModuleTemplateTypesBySurvey(surveyId);

        if(templateTypes == null || templateTypes.isEmpty()){
            ErrorBuilder.throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin("Could not find the template types with the survey with ID: " + surveyId)
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
                    .throwIt();
        }

        return templateTypes;
	}
	
	
	@RequestMapping(value ="/services/templateTypes", params="batteryId", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TemplateTypeDTO> getModuleTemplateTypesByBatteryId(@RequestParam("batteryId") Integer batteryId, 
			HttpServletRequest request) {
        
		logRequest(logger, request);
		
		if(batteryId == null || batteryId < 0) {
            ErrorBuilder.throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin("Could not find the template types with the battery with ID: " + batteryId)
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
                    .throwIt();
        }

        List<TemplateTypeDTO> templateTypes = templateTypeService.getTemplateTypesByBattery(batteryId);

        if(templateTypes == null || templateTypes.isEmpty()){
            ErrorBuilder.throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin("Could not find the template types with the battery with ID: " + batteryId)
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
                    .throwIt();
        }

        return templateTypes;
	}
	
	@RequestMapping(value="/services/templateTypes/{templateTypeId}/surveys/{surveyId}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Integer createTemplateForSurvey(
			@PathVariable("surveyId") Integer surveyId,
			@PathVariable("templateTypeId") Integer templateTypeId,
			@RequestBody TemplateFileDTO templateFile, 
			HttpServletRequest request){
		
		logRequest(logger, request);
		
		if (templateFile.getName() == null){
			ErrorBuilder.throwing(EntityNotFoundException.class)
            .toUser("Template name can not be null.")
            .toAdmin("Template name can not be null ")
            .setCode(ErrorCodeEnum.DATA_VALIDATION.getValue())
            .throwIt();
		}
		
		return templateService.saveTemplateFileForSurvey(surveyId, templateTypeId, templateFile);
	}
		

	@RequestMapping(value = "/services/templateTypes/{templateTypeId}/batteries/{batteryId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public Integer createTemplateForBattery(
			@PathVariable("batteryId") Integer batteryId,
	        @PathVariable("templateTypeId") Integer templateTypeId,
			@RequestBody TemplateFileDTO templateFile, 		
			HttpServletRequest request) {
		
		logRequest(logger, request);
		
		if (templateFile.getName() == null){
			ErrorBuilder.throwing(EntityNotFoundException.class)
            .toUser("Template name can not be null.")
            .toAdmin("Template name can not be null ")
            .setCode(ErrorCodeEnum.DATA_VALIDATION.getValue())
            .throwIt();
		}
		return templateService.saveTemplateFileForBattery(batteryId, templateTypeId, templateFile);
	}
	
	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public TemplateFileDTO getTemplate(
			@PathVariable("templateId") Integer templateId,
			HttpServletRequest request) {
		
		logRequest(logger, request);
		
		TemplateFileDTO dto = templateService.getTemplateFileAsTree(templateId);
		
		if (dto == null){
			 ErrorBuilder.throwing(EntityNotFoundException.class)
	             .toUser("Could not find the template.")
	             .toAdmin("Could not find the template with ID: " + templateId)
	             .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	             .throwIt();
		}
		
		if (dto.getBlocks()== null){
			ErrorResponse error = new ErrorResponse();
			EntityNotFoundException ex = new EntityNotFoundException(error);
			error.addMessage("The template you are attempting to edit is unsupported.");
			error.setDeveloperMessage("The template you are attempting to edit is unsupported. Template ID: " + templateId);
			error.setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue());
            throw ex;
		}
		return dto;
	}
	
	@RequestMapping(value="/services/template/{templateId}", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Boolean updateTemplateFile(@PathVariable("templateId") Integer templateId, 
			@RequestBody TemplateFileDTO templateFile, 
			HttpServletRequest request){
		
		logRequest(logger, request);
		
		try{
			templateService.updateTemplateFile(templateId, templateFile);
		}catch(IllegalArgumentException e) {
			ErrorBuilder.throwing(EscreeningDataValidationException.class)
            .toUser("An unexpected error while updating template. Please call support.")
            .toAdmin("Error updating template: " + templateId + ". Error: " + e.getLocalizedMessage())
            .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
            .throwIt();
		}
		return Boolean.TRUE;
	}	
	
	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public Boolean deleteTemplate( @PathVariable("templateId") Integer templateId,
			HttpServletRequest request){
		
		logRequest(logger, request);
		
		try {
			templateService.deleteTemplate(templateId);
		} catch(IllegalArgumentException e) {
			ErrorBuilder.throwing(EntityNotFoundException.class)
	            .toUser("Could not find the template.")
	            .toAdmin("Could not find the template with ID: " + templateId)
	            .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	            .throwIt();
		}
		return Boolean.TRUE;
	}
}
