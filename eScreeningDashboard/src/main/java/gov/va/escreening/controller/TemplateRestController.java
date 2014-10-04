package gov.va.escreening.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;

import gov.va.escreening.dto.ModuleTemplateTypeDTO;
import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.service.TemplateTypeService;

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
	public List<ModuleTemplateTypeDTO> getModuleTemplateTypes(@PathVariable Integer templateId, @CurrentUser EscreenUser escreenUser)
	{
		return templateTypeService.getModuleTemplateTypes(templateId);
	}
	
	@RequestMapping(value ="/services/templateType/survey/{surveyId}")
	@ResponseBody
	public List<ModuleTemplateTypeDTO> getModuleTemplateTypesBySurveyId(@PathVariable Integer surveyId, @CurrentUser EscreenUser escreenUser)
	{
		return templateTypeService.getModuleTemplateTypesBySurvey(surveyId);
	}
	
	
	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public boolean deleteTemplate(
			@PathVariable("templateId") Integer templateId,
			@CurrentUser EscreenUser escreenUser) {
		try
		{
			templateService.deleteTemplate(templateId);
		}
		catch(IllegalArgumentException e)
		{
			throw new NotFoundException("Could not find the template");
		}
		return true;
	}

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public TemplateDTO getTemplate(
			@PathVariable("templateId") Integer templateId,
			@CurrentUser EscreenUser escreenUser) {
		TemplateDTO dto = templateService.getTemplate(templateId);
		if (dto == null)
			throw new NotFoundException("Could not find the template.");
		return dto;
	}
	
	@RequestMapping(value="/services/template/{surveyId}/{templateTypeId}")
	@ResponseBody
	public TemplateDTO getTemplate(@PathVariable("templateTypeId") Integer templateTypeId, @PathVariable("surveyId") Integer surveyId,
			@CurrentUser EscreenUser screenUser)
	{
		TemplateDTO dto = templateService.getTemplateBySurveyAndTemplateType(surveyId, templateTypeId);
		if (dto == null)
			throw new NotFoundException("Could not find the template.");
		return dto;
	}

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public TemplateDTO updateTemplate(@PathVariable("templateId") Integer templateId,
			@RequestBody TemplateDTO templateDTO,
			@CurrentUser EscreenUser escreenUser) {
		try
		{
			return templateService.updateTemplate(templateDTO);
		}
		catch(IllegalArgumentException e)
		{
			throw new NotFoundException("Could not find the template");
		}
	}

	@RequestMapping(value = "/services/template/{templateId}/surveyId/{surveyId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public TemplateDTO createTemplateForSurvey(
			@RequestBody TemplateDTO templateDTO,
			@PathVariable Integer surveyId, @CurrentUser EscreenUser escreenUser) {
		return templateService.createTemplate(templateDTO, surveyId, true);
	}

	@RequestMapping(value = "/services/template/{templateId}/batteryId/{batteryId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public TemplateDTO createTemplateForBattery(
			@RequestBody TemplateDTO templateDTO,
			@PathVariable Integer batteryId, boolean isSurvey,
			@CurrentUser EscreenUser escreenUser) {
		return templateService.createTemplate(templateDTO, batteryId, false);
	}
	
	
	@RequestMapping(value="/services/template/addVariableTemplate/{templateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public boolean addVariableTemplateToTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.addVariableTemplates(templateId, variableTemplateIds);
		return true;
	}
	
	@RequestMapping(value="/services/template/removeVariableTemplate/{templateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public boolean removeVariableTemplatesFromTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.removeVariableTemplatesFromTemplate(templateId, variableTemplateIds);
		return true;
	}
	
	@RequestMapping(value="/services/template/addVariableTemplates/{templateId}/{variableTemplateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public boolean addVariableTemplateToTemplate(@PathVariable Integer templateId, @PathVariable Integer variableTemplateId, @CurrentUser EscreenUser escreenUser)
	{
		templateService.addVariableTemplate(templateId, variableTemplateId);
		return true;
	}
	
	@RequestMapping(value="/services/template/removeVariableTemplate/{templateId}/{variableTemplateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public boolean removeVariableTemplateFromTemplate(@PathVariable Integer templateId, @PathVariable Integer variableTemplateId, @CurrentUser EscreenUser escreenUser)
	{
		templateService.removeVariableTemplateFromTemplate(templateId, variableTemplateId);
		return true;
	}
	
	@RequestMapping(value="/services/template/setVariableTemplates/{templateId}", method =RequestMethod.POST,
			consumes="application/json", produces="application/json")
	@ResponseBody
	public boolean setVariableTemplate(@PathVariable Integer templateId, @RequestBody List<Integer> variableTemplateIds, @CurrentUser EscreenUser escreenUser)
	{
		templateService.setVariableTemplatesToTemplate(templateId, variableTemplateIds);
		return true;
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
