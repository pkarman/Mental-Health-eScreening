package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicSurvey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ClinicSurveyRepositoryImpl extends AbstractHibernateRepository<ClinicSurvey> implements
        ClinicSurveyRepository {

    public ClinicSurveyRepositoryImpl() {
        super();

        setClazz(ClinicSurvey.class);
    }

    @Override
    public List<ClinicSurvey> findAllByClinicId(int clinicId) {

        List<ClinicSurvey> resultList = new ArrayList<ClinicSurvey>();

        String sql = "SELECT cs FROM ClinicSurvey cs JOIN cs.Clinic c WHERE c.clinicId = :clinicId ORDER BY cs.clinicSurveyId";

        TypedQuery<ClinicSurvey> query = entityManager.createQuery(sql, ClinicSurvey.class);
        query.setParameter("clinicId", clinicId);

        resultList = query.getResultList();

        return resultList;
    }
}
