package gov.va.escreening.repository;

import gov.va.escreening.entity.NoteTitleMap;

import org.springframework.stereotype.Repository;

@Repository
public class NoteTitleMapRepositoryImpl extends AbstractHibernateRepository<NoteTitleMap> implements
        NoteTitleMapRepository {

    public NoteTitleMapRepositoryImpl() {
        super();

        setClazz(NoteTitleMap.class);
    }

}
