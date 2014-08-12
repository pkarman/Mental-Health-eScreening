package gov.va.escreening.repository;

import gov.va.escreening.entity.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateRepositoryImpl extends AbstractHibernateRepository<Template> implements TemplateRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateRepositoryImpl.class);
	
	public TemplateRepositoryImpl() {
		super();

		setClazz(Template.class);
	}
	
	@Override
	public boolean updateTemplateWithTemplateFile(Integer templateId, String templateFile) {
		Template template = this.findOne(templateId);

		if(template == null) {
			logger.error(String.format("Template for id: %s could not be found, so the templateFile was not set", templateId));
			return false;
		}
			
		template.setTemplateFile(templateFile);
		return true;
	}

}