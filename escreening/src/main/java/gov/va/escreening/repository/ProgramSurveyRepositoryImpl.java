package gov.va.escreening.repository;

import gov.va.escreening.entity.ProgramSurvey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ProgramSurveyRepositoryImpl extends AbstractHibernateRepository<ProgramSurvey> implements
        ProgramSurveyRepository {

    public ProgramSurveyRepositoryImpl() {
        super();

        setClazz(ProgramSurvey.class);
    }

    @Override
    public List<ProgramSurvey> findAllByProgramId(int programId) {

        List<ProgramSurvey> resultList = new ArrayList<ProgramSurvey>();
        String sql = "SELECT ps FROM ProgramSurvey ps JOIN ps.program p WHERE p.programId = :programId ORDER BY ps.programSurveyId";

        TypedQuery<ProgramSurvey> query = entityManager.createQuery(sql, ProgramSurvey.class);
        query.setParameter("programId", programId);

        resultList = query.getResultList();

        return resultList;
    }
}
