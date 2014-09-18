package gov.va.escreening.service;

import gov.va.escreening.dto.TemplateDTO;

public interface TemplateService {

	void deleteTemplate(Integer templateId);

	TemplateDTO readTemplate(Integer templateId);

	void updateTemplate(TemplateDTO templateDTO);

	void createTemplate(TemplateDTO templateDTO, Integer parentId,
			boolean isSurvey);

}
