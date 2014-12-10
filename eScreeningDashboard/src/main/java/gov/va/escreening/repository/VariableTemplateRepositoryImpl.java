package gov.va.escreening.repository;

import java.util.List;

import javax.persistence.Query;

import gov.va.escreening.entity.VariableTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class VariableTemplateRepositoryImpl extends
		AbstractHibernateRepository<VariableTemplate> implements
		VariableTemplateRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(VariableTemplateRepositoryImpl.class);

	public VariableTemplateRepositoryImpl() {
		super();

		setClazz(VariableTemplate.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<VariableTemplate> findByIds(List<Integer> variableTemplateIds)
	{
		Query query = entityManager.createQuery("from " + VariableTemplate.class.getName()+" where variableTemplateId in (:ids)");
		query.setParameter("ids", variableTemplateIds);
		return query.getResultList();
	}

}
