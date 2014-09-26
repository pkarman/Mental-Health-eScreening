package gov.va.escreening.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Battery;

import org.springframework.stereotype.Repository;

@Repository
public class AssessmentVariableRepositoryImpl extends AbstractHibernateRepository<AssessmentVariable> implements AssessmentVariableRepository {
	public AssessmentVariableRepositoryImpl() {
		super();
		setClazz(AssessmentVariable.class);
	}

	@Override
	public List<AssessmentVariable> findAllFormulae() {
		String sql = "FROM AssessmentVariable av where av.assessmentVariableTypeId=4";
		TypedQuery<AssessmentVariable> query = entityManager.createQuery(sql, AssessmentVariable.class);
		List<AssessmentVariable> resultList = query.getResultList();
		return resultList;
	}
}