package gov.va.escreening.repository;

import gov.va.escreening.entity.AssessmentAppointment;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.List;

public interface AssessmentAppointmentRepository extends RepositoryInterface<AssessmentAppointment> {
    AssessmentAppointment findByAssessmentId(int assessmentId);

    void bindAppointments(List<VeteranAssessment> veteranAssessments, String orderByColumn, String orderByDirection);
}
