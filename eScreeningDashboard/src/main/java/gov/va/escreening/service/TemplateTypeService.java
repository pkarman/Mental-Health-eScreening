package gov.va.escreening.service;

import gov.va.escreening.dto.ModuleTemplateTypeDTO;

import java.util.List;

public interface TemplateTypeService {
	
	public List<ModuleTemplateTypeDTO> getModuleTemplateTypes(Integer templateId);

	public List<ModuleTemplateTypeDTO> getModuleTemplateTypesBySurvey(
			Integer surveyId);

}
