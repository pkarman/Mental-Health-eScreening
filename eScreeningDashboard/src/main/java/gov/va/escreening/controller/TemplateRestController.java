package gov.va.escreening.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;

import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.ModuleTemplateTypeDTO;
import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.service.TemplateTypeService;
import gov.va.escreening.webservice.Response;
import gov.va.escreening.webservice.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dashboard")
public class TemplateRestController {

	private static final Logger logger = LoggerFactory
			.getLogger(TemplateRestController.class);

	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateTypeService templateTypeService;
	
	@RequestMapping(value ="/services/templateType/{templateId}")
	@ResponseBody
	public Response<List<ModuleTemplateTypeDTO>> getModuleTemplateTypes(@PathVariable Integer templateId, @CurrentUser EscreenUser escreenUser)
	{
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), 
							templateTypeService.getModuleTemplateTypes(templateId));
	}
	
	@RequestMapping(value ="/services/templateType/survey/{surveyId}")
	@ResponseBody
	public Response<List<ModuleTemplateTypeDTO>> getModuleTemplateTypesBySurveyId(@PathVariable Integer surveyId, @CurrentUser EscreenUser escreenUser)
	{
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), 
							templateTypeService.getModuleTemplateTypesBySurvey(surveyId));
	}
	
	
	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response<Boolean> deleteTemplate(
			@PathVariable("templateId") Integer templateId,
			@CurrentUser EscreenUser escreenUser) {
		try
		{
			templateService.deleteTemplate(templateId);
		}
		catch(IllegalArgumentException e)
		{
			ErrorBuilder.throwing(EntityNotFoundException.class)
	            .toUser("Could not find the template.")
	            .toAdmin("Could not find the template with ID: " + templateId)
	            .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	            .throwIt();
		}
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), Boolean.TRUE);
	}

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response<TemplateDTO> getTemplate(
			@PathVariable("templateId") Integer templateId,
			@CurrentUser EscreenUser escreenUser) {
		TemplateDTO dto = templateService.getTemplate(templateId);
		if (dto == null)
			 ErrorBuilder.throwing(EntityNotFoundException.class)
	             .toUser("Could not find the template.")
	             .toAdmin("Could not find the template with ID: " + templateId)
	             .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	             .throwIt();
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), dto);
	}
	
	@RequestMapping(value="/services/template/{surveyId}/{templateTypeId}")
	@ResponseBody
	public Response<TemplateDTO> getTemplate(@PathVariable("templateTypeId") Integer templateTypeId, @PathVariable("surveyId") Integer surveyId,
			@CurrentUser EscreenUser screenUser)
	{
		TemplateDTO dto = templateService.getTemplateBySurveyAndTemplateType(surveyId, templateTypeId);
		if (dto == null)
			ErrorBuilder.throwing(EntityNotFoundException.class)
	            .toUser("Could not find the template.")
	            .toAdmin("Could not find the template with a type ID of: " + templateTypeId + " for module with ID: " + surveyId)
	            .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	            .throwIt();
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), dto);
	}

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response<TemplateDTO> updateTemplate(@PathVariable("templateId") Integer templateId,
			@RequestBody TemplateDTO templateDTO,
			@CurrentUser EscreenUser escreenUser) {
		
		TemplateDTO updatedTemplate = null;
		try
		{
			updatedTemplate = templateService.updateTemplate(templateDTO);
		}
		catch(IllegalArgumentException e)
		{
			ErrorBuilder.throwing(EntityNotFoundException.class)
	            .toUser("Could not find the template.")
	            .toAdmin("Could not find the template with ID: " + templateId)
	            .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
	            .throwIt();
		}
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), updatedTemplate);
	}

	@RequestMapping(value = "/services/template/{templateId}/surveyId/{surveyId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response<TemplateDTO> createTemplateForSurvey(
			@RequestBody TemplateDTO templateDTO,
			@PathVariable Integer surveyId, @CurrentUser EscreenUser escreenUser) {
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), 
							templateService.createTemplate(templateDTO, surveyId, true));
	}

	@RequestMapping(value = "/services/template/{templateId}/batteryId/{batteryId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response<TemplateDTO> createTemplateForBattery(
			@RequestBody TemplateDTO templateDTO,
			@PathVariable Integer batteryId, boolean isSurvey,
			@CurrentUser EscreenUser escreenUser) {
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), 
				templateService.createTemplate(templateDTO, batteryId, false));
	}
	
	
	@RequestMapping(value="/services/template/addVariableTemplate/{templateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public Response<Boolean> addVariableTemplateToTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.addVariableTemplates(templateId, variableTemplateIds);
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), Boolean.TRUE);
	}
	
	@RequestMapping(value="/services/template/removeVariableTemplate/{templateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public Response<Boolean> removeVariableTemplatesFromTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.removeVariableTemplatesFromTemplate(templateId, variableTemplateIds);
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), Boolean.TRUE);
	}
	
	@RequestMapping(value="/services/template/addVariableTemplates/{templateId}/{variableTemplateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public Response<Boolean> addVariableTemplateToTemplate(@PathVariable Integer templateId, @PathVariable Integer variableTemplateId, @CurrentUser EscreenUser escreenUser)
	{
		templateService.addVariableTemplate(templateId, variableTemplateId);
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), Boolean.TRUE);
	}
	
	@RequestMapping(value="/services/template/removeVariableTemplate/{templateId}/{variableTemplateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public Response<Boolean> removeVariableTemplateFromTemplate(@PathVariable Integer templateId, @PathVariable Integer variableTemplateId, @CurrentUser EscreenUser escreenUser)
	{
		templateService.removeVariableTemplateFromTemplate(templateId, variableTemplateId);
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), Boolean.TRUE);
	}
	
	@RequestMapping(value="/services/template/setVariableTemplates/{templateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public Response<Boolean> setVariableTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.setVariableTemplatesToTemplate(templateId, variableTemplateIds);
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), Boolean.TRUE);
	}
	
	@ExceptionHandler(NotFoundException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map handleException(NotFoundException e) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", e.getMessage());
        return map;
    }

}
