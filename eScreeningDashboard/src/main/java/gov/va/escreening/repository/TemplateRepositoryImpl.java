package gov.va.escreening.repository;

import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateRepositoryImpl extends
		AbstractHibernateRepository<Template> implements TemplateRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(TemplateRepositoryImpl.class);

	public TemplateRepositoryImpl() {
		super();

		setClazz(Template.class);
	}

	@Override
	public boolean updateTemplateWithTemplateFile(Integer templateId,
			String templateFile) {
		Template template = this.findOne(templateId);

		if (template == null) {
			logger.error(String
					.format("Template for id: %s could not be found, so the templateFile was not set",
							templateId));
			return false;
		}

		template.setTemplateFile(templateFile);
		return true;
	}

	@Override
	public Template getTemplateByIdAndTemplateType(Integer surveyId,
			Integer templateTypeId) {
		String sql = "SELECT t From Survey s JOIN s.templates t JOIN t.templateType tt where s.surveyId = :surveyId and tt.templateTypeId = :templateTypeId";
		TypedQuery<Template> query = entityManager.createQuery(sql,
				Template.class);
		query.setParameter("surveyId", surveyId);
		query.setParameter("templateTypeId", templateTypeId);

		try {
			return query.getSingleResult();
		} catch (IllegalStateException | PersistenceException e) {
			return null;
		}

	}

}