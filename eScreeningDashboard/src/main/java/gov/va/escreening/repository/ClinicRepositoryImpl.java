package gov.va.escreening.repository;

import gov.va.escreening.entity.Clinic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ClinicRepositoryImpl extends AbstractHibernateRepository<Clinic> implements ClinicRepository {

    public ClinicRepositoryImpl() {
        super();

        setClazz(Clinic.class);
    }

    @Override
    public List<Clinic> findByProgramId(int programId) {

        List<Clinic> resultList = new ArrayList<Clinic>();

        String sql = "SELECT c FROM Clinic c JOIN c.program p WHERE p.programId = :programId ORDER BY c.name";

        TypedQuery<Clinic> query = entityManager.createQuery(sql, Clinic.class);
        query.setParameter("programId", programId);

        resultList = query.getResultList();

        return resultList;
    }

}
