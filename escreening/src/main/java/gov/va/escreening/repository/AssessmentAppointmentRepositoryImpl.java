package gov.va.escreening.repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import gov.va.escreening.entity.VeteranAssessment;
import org.springframework.stereotype.Repository;

import gov.va.escreening.entity.AssessmentAppointment;

@Repository("assessmentAppointmentRepository")
public class AssessmentAppointmentRepositoryImpl
        extends AbstractHibernateRepository<AssessmentAppointment>
        implements AssessmentAppointmentRepository {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy@HH:mm");

    @Override
    public AssessmentAppointment findByAssessmentId(int assessmentId) {
        String sql = "From AssessmentAppointment aa where aa.vetAssessmentId = :id";
        Query q = entityManager.createQuery(sql).setParameter("id", assessmentId);

        List<AssessmentAppointment> result = q.getResultList();
        if (result != null && result.size() > 0) {
            return result.get(0);
        }

        return null;
    }

    @Override
    public void bindAppointments(List<VeteranAssessment> veteranAssessments, String orderByColumn, String orderByDirection) {
        String sql = "From AssessmentAppointment aa where aa.vetAssessmentId in (:ids)";
        TypedQuery<AssessmentAppointment> q = entityManager.createQuery(sql, AssessmentAppointment.class);
        q.setParameter("ids", veteranAssessments.stream().map(VeteranAssessment::getVeteranAssessmentId).collect(Collectors.toList()));
        List<AssessmentAppointment> aas = q.getResultList();
        Map<Integer, AssessmentAppointment> aaMap = aas.stream().collect(Collectors.toMap(AssessmentAppointment::getVetAssessmentId, (aa) -> aa));

        // bind appointment dates with the veteran assessments, if needed
        veteranAssessments.forEach(va -> {
            AssessmentAppointment aa = aaMap.get(va.getVeteranAssessmentId());
            if (aa != null) {
                va.setAppointmentDateAndTime(LocalDateTime.ofInstant(aa.getAppointmentDate().toInstant(), ZoneId.systemDefault()).format(formatter));
                va.setAppointmentDateAndTimeAsLong(aa.getAppointmentDate().getTime());
            }
        });

        // apply sort logic now, iif the orderByColumn is apptDateAndTime
        if ("apptDateAndTime".equals(orderByColumn)) {
            if ("SORT_ASCENDING".equals(orderByDirection)) {
                Collections.sort(veteranAssessments, (e1, e2) -> {
                    long e1T = e1.getAppointmentDateAndTimeAsLong();
                    long e2T = e2.getAppointmentDateAndTimeAsLong();
                    return e1T == e2T ? 0 : e1T < e2T ? -1 : 1;
                });
            } else {
                Collections.sort(veteranAssessments, (e1, e2) -> {
                    long e1T = e1.getAppointmentDateAndTimeAsLong();
                    long e2T = e2.getAppointmentDateAndTimeAsLong();
                    return e1T == e2T ? 0 : e1T < e2T ? 1 : -1;
                });
            }
        }
    }


}
