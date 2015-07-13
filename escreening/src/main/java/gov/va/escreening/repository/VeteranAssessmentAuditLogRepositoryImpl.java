package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentAuditLog;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class VeteranAssessmentAuditLogRepositoryImpl 
	extends AbstractHibernateRepository<VeteranAssessmentAuditLog> implements VeteranAssessmentAuditLogRepository {

    public VeteranAssessmentAuditLogRepositoryImpl() {
        super();
        setClazz(VeteranAssessmentAuditLog.class);
    }

    public List<VeteranAssessmentAuditLog> getAssessmentAuditLogByAssessmentId (Integer assessmentId) {
        String sql = "SELECT vaal FROM VeteranAssessmentAuditLog vaal WHERE vaal.veteranAssessmentId = :veteranAssessmentId ORDER BY vaal.dateCreated";

        TypedQuery<VeteranAssessmentAuditLog> query = entityManager.createQuery(sql, VeteranAssessmentAuditLog.class);
        query.setParameter("veteranAssessmentId", assessmentId);

        return query.getResultList();
    }
}
