package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicalReminderSurvey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ClinicalReminderSurveyRepositoryImpl extends AbstractHibernateRepository<ClinicalReminderSurvey> implements
        ClinicalReminderSurveyRepository {

    public ClinicalReminderSurveyRepositoryImpl() {
        super();

        setClazz(ClinicalReminderSurvey.class);
    }

    @Override
    public List<ClinicalReminderSurvey> findAllByVistaIen(String vistaIen) {

        List<ClinicalReminderSurvey> resultList = new ArrayList<ClinicalReminderSurvey>();

        String sql = "SELECT crs FROM ClinicalReminderSurvey crs JOIN crs.clinicalReminder cr WHERE cr.vistaIen = :vistaIen ORDER BY crs.clinicalReminderSurveyId";

        TypedQuery<ClinicalReminderSurvey> query = entityManager.createQuery(sql, ClinicalReminderSurvey.class);
        query.setParameter("vistaIen", vistaIen);

        resultList = query.getResultList();

        return resultList;
    }

	@Override
	public void removeSurveyMapping(int surveyID) {
		// TODO Auto-generated method stub
		String sql = "delete from ClinicalReminderSurvey crs where crs.survey.surveyId = :surveyID";
		Query q = entityManager.createQuery(sql).setParameter("surveyID", surveyID);
		q.executeUpdate();
	}

	@Override
	public void createClinicalReminderSurvey(int clinicalReminderId,
			int surveyId) {
		String sql = "insert into clinical_reminder_survey (clinical_reminder_id, survey_id) values (:crID, :sId)";
		Query q = entityManager.createNativeQuery(sql).setParameter("crID", clinicalReminderId)
				.setParameter("sId", surveyId);
		q.executeUpdate();
		
	}
	
	
}
