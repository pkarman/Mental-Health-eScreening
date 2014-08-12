package gov.va.escreening.repository;

import gov.va.escreening.entity.MeasureType;

import org.springframework.stereotype.Repository;

@Repository
public class MeasureTypeRepositoryImpl extends AbstractHibernateRepository<MeasureType> implements MeasureTypeRepository {
    public MeasureTypeRepositoryImpl() {
        super();
        setClazz(MeasureType.class);
    }
}