package gov.va.escreening.controller;

import java.util.List;

import gov.va.escreening.dto.ModuleTemplateTypeDTO;
import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.service.TemplateTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	
	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public boolean deleteTemplate(
			@PathVariable("templateId") Integer templateId,
			@CurrentUser EscreenUser escreenUser) {
		templateService.deleteTemplate(templateId);
		return true;
	}

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public TemplateDTO getTemplate(
			@PathVariable("templateId") Integer templateId,
			@CurrentUser EscreenUser escreenUser) {
		return templateService.getTemplate(templateId);
	}

	@RequestMapping(value = "/services/template/{templateId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public TemplateDTO updateTemplate(@PathVariable("templateId") Integer templateId,
			@RequestBody TemplateDTO templateDTO,
			@CurrentUser EscreenUser escreenUser) {
		return templateService.updateTemplate(templateDTO);
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

}
