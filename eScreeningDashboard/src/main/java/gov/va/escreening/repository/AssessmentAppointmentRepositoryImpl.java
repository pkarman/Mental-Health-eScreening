package gov.va.escreening.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import gov.va.escreening.entity.AssessmentAppointment;

@Repository
public class AssessmentAppointmentRepositoryImpl 
	extends AbstractHibernateRepository<AssessmentAppointment>
	implements AssessmentAppointmentRepository
{

	@Override
	public AssessmentAppointment findByAssessmentId(int assessmentId) {
		String sql = "From AssessmentAppointment aa where aa.vetAssessmentId = :id";
		Query q = entityManager.createQuery(sql).setParameter("id", assessmentId);
		
		List<AssessmentAppointment> result = q.getResultList();
		if(result!=null && result.size() >0)
		{
			return result.get(0);
		}
		
		return null;
	}
	

}
