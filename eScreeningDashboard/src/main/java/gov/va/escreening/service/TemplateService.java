package gov.va.escreening.service;

import java.util.List;

import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.dto.template.TemplateFileDTO;

public interface TemplateService {

	void deleteTemplate(Integer templateId);

	TemplateDTO getTemplate(Integer templateId);

	TemplateDTO updateTemplate(TemplateDTO templateDTO);

	TemplateDTO createTemplate(TemplateDTO templateDTO, Integer templateTypeId, 
							   Integer parentId,  boolean isSurvey);

	void addVariableTemplate(Integer templateId, Integer variableTemplateId);

	void addVariableTemplates(Integer templateId,
			List<Integer> variableTemplateIds);

	void removeVariableTemplateFromTemplate(Integer templateId,
			Integer variableTemplateId);

	void removeVariableTemplatesFromTemplate(Integer templateId,
			List<Integer> variableTemplateIds);

	void setVariableTemplatesToTemplate(Integer templateId,
			List<Integer> variableTemplateIds);

	 TemplateDTO getTemplateBySurveyAndTemplateType(Integer surveyId,
			Integer templateTypeId);

	TemplateFileDTO getTemplateFileAsTree(Integer templateId);

	Integer saveTemplateFileForSurvey(Integer surveyId, Integer templateTypeId, TemplateFileDTO templateFile);
	
	Integer saveTemplateFileForBattery(Integer batteryId, Integer templateTypeId, TemplateFileDTO templateFile);

	void updateTemplateFile(Integer templateId, TemplateFileDTO templateFile);

	

}
