package gov.va.escreening.repository;

import gov.va.escreening.entity.SurveySection;

import java.util.List;

public interface SurveySectionRepository extends RepositoryInterface<SurveySection> {

    /**
     * Finds all SurveySections for the given assessment
     * @param veteranAssessmentId
     * @return List of SurveySection in display order
     */
    public List<SurveySection> findForVeteranAssessmentId(int veteranAssessmentId);
    
    /**
     * Retrieves all the SurveySections in the system.
     * @return
     */
    List<SurveySection> getSurveySectionList();

}