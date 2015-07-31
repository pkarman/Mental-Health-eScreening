package gov.va.escreening.repository;

import gov.va.escreening.entity.Survey;

import java.util.List;

public interface SurveyRepository extends RepositoryInterface<Survey> {

    /**
     * Retrieves all published surveys that are mandatory by the system (deomographics).
     * @return
     */
    List<Survey> getRequiredSurveys();

    /**
     * Retrieves all the surveys assigned to veteran assessment.
     * @param veteranAssessmentId
     * @return
     */
    List<Survey> findForVeteranAssessmentId(int veteranAssessmentId);
    
    /**
     * Retrieves all the survey.
     * @return
     */
    List<Survey> getSurveyList();

    List<Survey> getMhaSurveyList();

	List<Survey> findByTemplateId(Integer templateId);

    List<Survey> getSurveyListByIds(List<Integer> surveyIdList);
}
