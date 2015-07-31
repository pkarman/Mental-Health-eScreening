package gov.va.escreening.repository;

import gov.va.escreening.entity.Template;

public interface TemplateRepository extends RepositoryInterface<Template>{
    
    
	boolean updateTemplateWithTemplateFile(Integer templateId, String templateFile);
	
	public Template getTemplateByIdAndTemplateType(Integer surveyId,
			Integer templateTypeId);

}
