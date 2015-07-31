package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentNote;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class VeteranAssessmentNoteRepositoryImpl extends AbstractHibernateRepository<VeteranAssessmentNote> implements
        VeteranAssessmentNoteRepository {

    public VeteranAssessmentNoteRepositoryImpl() {
        super();

        setClazz(VeteranAssessmentNote.class);
    }

    @Override
    public List<VeteranAssessmentNote> findAllByVeteranAssessmentId(int veteranAssessmentId) {

        String sql = "SELECT van FROM VeteranAssessmentNote van JOIN van.veteranAssessment va JOIN van.clinicalNote cn WHERE va.veteranAssessmentId = :veteranAssessmentId ORDER BY cn.title";

        TypedQuery<VeteranAssessmentNote> query = entityManager.createQuery(sql, VeteranAssessmentNote.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);

        return query.getResultList();
    }

}
