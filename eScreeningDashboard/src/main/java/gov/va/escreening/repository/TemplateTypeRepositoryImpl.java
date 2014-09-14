package gov.va.escreening.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import gov.va.escreening.entity.TemplateType;

@Repository
public class TemplateTypeRepositoryImpl extends AbstractHibernateRepository<TemplateType> implements TemplateTypeRepository {
	
	public TemplateTypeRepositoryImpl() {
		super();
		setClazz(TemplateType.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TemplateType> findAllOrderByName() {
        return (List<TemplateType>)entityManager.createQuery("from " + TemplateType.class.getName()+" t order by t.name ").getResultList();
    }
}
