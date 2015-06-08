package gov.va.escreening.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import gov.va.escreening.constants.TemplateConstants;
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

	@Override
	public List<TemplateType> findAllModuleTypeOrderByName() {
		// types which can be mapped to a module
		return entityManager.createQuery(
		            "FROM " + TemplateType.class.getName()
		            +" t where t.templateTypeId in (" + TemplateConstants.moduleTemplateIds() + ") order by t.name ",
		            TemplateType.class).getResultList();
	}
	
	@Override
	public List<TemplateType> findAllTypeOrderByNameForBattery() {
		// types which can be mapped to a battery
	    return entityManager.createQuery(
	            "From " + TemplateType.class.getName()
                +" t where t.templateTypeId not in (" + TemplateConstants.moduleTemplateIds() + ") order by t.name ",
                TemplateType.class).getResultList();
	}
}
