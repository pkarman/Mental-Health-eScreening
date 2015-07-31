package gov.va.escreening.repository;

import gov.va.escreening.entity.AssessmentAppointment;

public interface AssessmentAppointmentRepository extends RepositoryInterface<AssessmentAppointment>
{
	AssessmentAppointment findByAssessmentId(int assessmentId);
}
