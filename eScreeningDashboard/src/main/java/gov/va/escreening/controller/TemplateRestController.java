package gov.va.escreening.controller;

import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.dto.TemplateTypeDTO;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.service.TemplateTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class TemplateRestController {

	private static final Logger logger = LoggerFactory
			.getLogger(TemplateRestController.class);

	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateTypeService templateTypeService;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException enfe) {
        logger.debug(enfe.getMessage());
        return enfe.getErrorResponse();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleIllegalArgumentException(Exception iae) {
        logger.debug(iae.getMessage());
        ErrorResponse er = new ErrorResponse();

        er.setDeveloperMessage(iae.getMessage());
        er.addMessage("Sorry; but we are unable to process your request at this time.  If this continues, please contact your system administrator.");
        er.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return er;
    }
	
	@RequestMapping(value ="/services/templateTypes", params="surveyId", method = RequestMethod.GET/*, consumes = "application/json"*/, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TemplateTypeDTO> getModuleTemplateTypesBySurveyId(@RequestParam("surveyId") Integer surveyId, @CurrentUser EscreenUser escreenUser) {
        if(surveyId == null || surveyId < 0) {
            ErrorBuilder.throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin("Could not find the template types with the survey with ID: " + surveyId)
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
                    .throwIt();
        }

        List<TemplateTypeDTO> templateTypes = templateTypeService.getModuleTemplateTypesBySurvey(surveyId);

        if(templateTypes == null || (templateTypes != null && templateTypes.isEmpty())){
            ErrorBuilder.throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin("Could not find the template types with the survey with ID: " + surveyId)
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
                    .throwIt();
        }

        return templateTypes;
	}
	
	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public Boolean deleteTemplate( @PathVariable("templateId") Integer templateId, @CurrentUser EscreenUser escreenUser) {
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

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public TemplateDTO getTemplate(
			@PathVariable("templateId") Integer templateId,
			@CurrentUser EscreenUser escreenUser) {
		TemplateDTO dto = templateService.getTemplate(templateId);
		if (dto == null)
			 ErrorBuilder.throwing(EntityNotFoundException.class)
	             .toUser("Could not find the template.")
	             .toAdmin("Could not find the template with ID: " + templateId)
	             .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	             .throwIt();
		return dto;
	}
	
	@RequestMapping(value="/services/template/{surveyId}/{templateTypeId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public TemplateDTO getTemplate(@PathVariable("templateTypeId") Integer templateTypeId, @PathVariable("surveyId") Integer surveyId,
			@CurrentUser EscreenUser screenUser)
	{
		TemplateDTO dto = templateService.getTemplateBySurveyAndTemplateType(surveyId, templateTypeId);
		if (dto == null)
			ErrorBuilder.throwing(EntityNotFoundException.class)
	            .toUser("Could not find the template.")
	            .toAdmin("Could not find the template with a type ID of: " + templateTypeId + " for module with ID: " + surveyId)
	            .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	            .throwIt();
		return dto;
	}

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public TemplateDTO updateTemplate(@PathVariable("templateId") Integer templateId,
			@RequestBody TemplateDTO templateDTO,
			@CurrentUser EscreenUser escreenUser) {
		
		TemplateDTO updatedTemplate = null;
		try {
			updatedTemplate = templateService.updateTemplate(templateDTO);
		} catch(IllegalArgumentException e) {
			ErrorBuilder.throwing(EntityNotFoundException.class)
	            .toUser("Could not find the template.")
	            .toAdmin("Could not find the template with ID: " + templateId)
	            .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	            .throwIt();
		}
		return updatedTemplate;
	}

	@RequestMapping(value = "/services/template/{templateId}/surveyId/{surveyId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public TemplateDTO createTemplateForSurvey(
			@RequestBody TemplateDTO templateDTO,
			@PathVariable Integer surveyId, @CurrentUser EscreenUser escreenUser) {
		return templateService.createTemplate(templateDTO, surveyId, true);
	}

	@RequestMapping(value = "/services/template/{templateId}/batteryId/{batteryId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public TemplateDTO createTemplateForBattery(
			@RequestBody TemplateDTO templateDTO,
			@PathVariable Integer batteryId, boolean isSurvey,
			@CurrentUser EscreenUser escreenUser) {
		return templateService.createTemplate(templateDTO, batteryId, false);
	}
	
	
	@RequestMapping(value="/services/template/addVariableTemplate/{templateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public Boolean addVariableTemplateToTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.addVariableTemplates(templateId, variableTemplateIds);
		return Boolean.TRUE;
	}
	
	@RequestMapping(value="/services/template/removeVariableTemplate/{templateId}", method =RequestMethod.DELETE,
			consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public Boolean removeVariableTemplatesFromTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.removeVariableTemplatesFromTemplate(templateId, variableTemplateIds);
		return Boolean.TRUE;
	}
	
	@RequestMapping(value="/services/template/addVariableTemplates/{templateId}/{variableTemplateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public Boolean addVariableTemplateToTemplate(@PathVariable Integer templateId, @PathVariable Integer variableTemplateId, @CurrentUser EscreenUser escreenUser)
	{
		templateService.addVariableTemplate(templateId, variableTemplateId);
		return Boolean.TRUE;
	}
	
	@RequestMapping(value="/services/template/removeVariableTemplate/{templateId}/{variableTemplateId}", method =RequestMethod.DELETE,
			consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public Boolean removeVariableTemplateFromTemplate(@PathVariable Integer templateId, @PathVariable Integer variableTemplateId, @CurrentUser EscreenUser escreenUser)
	{
		templateService.removeVariableTemplateFromTemplate(templateId, variableTemplateId);
		return Boolean.TRUE;
	}
	
	@RequestMapping(value="/services/template/setVariableTemplates/{templateId}", method =RequestMethod.PUT,
			consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public Boolean setVariableTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.setVariableTemplatesToTemplate(templateId, variableTemplateIds);
		return Boolean.TRUE;
	}

}
