package gov.va.escreening.repository;

import gov.va.escreening.entity.Consult;

import org.springframework.stereotype.Repository;

@Repository
public class ConsultRepositoryImpl extends AbstractHibernateRepository<Consult> implements ConsultRepository {

    public ConsultRepositoryImpl() {
        super();

        setClazz(Consult.class);
    }

}
