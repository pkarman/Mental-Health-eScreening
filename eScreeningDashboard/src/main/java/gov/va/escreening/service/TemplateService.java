package gov.va.escreening.service;

import java.util.List;

import gov.va.escreening.dto.template.TemplateFileDTO;

public interface TemplateService {

	void deleteTemplate(Integer templateId);

	void addVariableTemplate(Integer templateId, Integer variableTemplateId);

	void addVariableTemplates(Integer templateId,
			List<Integer> variableTemplateIds);

	void removeVariableTemplateFromTemplate(Integer templateId,
			Integer variableTemplateId);

	void removeVariableTemplatesFromTemplate(Integer templateId,
			List<Integer> variableTemplateIds);

	void setVariableTemplatesToTemplate(Integer templateId,
			List<Integer> variableTemplateIds);

	TemplateFileDTO getTemplateFileAsTree(Integer templateId);

	Integer saveTemplateFileForSurvey(Integer surveyId, Integer templateTypeId, TemplateFileDTO templateFile);
	
	Integer saveTemplateFileForBattery(Integer batteryId, Integer templateTypeId, TemplateFileDTO templateFile);

	void updateTemplateFile(Integer templateId, TemplateFileDTO templateFile);

}
