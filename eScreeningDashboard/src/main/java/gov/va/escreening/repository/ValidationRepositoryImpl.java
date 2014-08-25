package gov.va.escreening.repository;

import gov.va.escreening.entity.Validation;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ValidationRepositoryImpl extends AbstractHibernateRepository<Validation> implements ValidationRepository {
    public ValidationRepositoryImpl() {
        super();
        setClazz(Validation.class);
    }

	@Override
	public Validation findValidationByCode(String code) {
		String sql = "FROM Validation v WHERE v.code = :code";

        TypedQuery<Validation> query = entityManager.createQuery(sql, Validation.class);
        query.setParameter("code", code);
     
        return query.getSingleResult();
	}
}