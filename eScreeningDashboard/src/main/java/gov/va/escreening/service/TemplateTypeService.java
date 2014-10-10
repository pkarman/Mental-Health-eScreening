package gov.va.escreening.service;

import gov.va.escreening.dto.TemplateTypeDTO;

import java.util.List;

public interface TemplateTypeService {
	
	public List<TemplateTypeDTO> getModuleTemplateTypes(Integer templateId);

	public List<TemplateTypeDTO> getModuleTemplateTypesBySurvey(
			Integer surveyId);

}
