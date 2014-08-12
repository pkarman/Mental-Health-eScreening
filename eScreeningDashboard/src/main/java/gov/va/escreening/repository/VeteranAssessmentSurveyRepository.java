package gov.va.escreening.repository;

import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.VeteranAssessmentSurvey;

import java.util.List;

public interface VeteranAssessmentSurveyRepository extends RepositoryInterface<VeteranAssessmentSurvey> {

    List<VeteranAssessmentSurvey> forVeteranAssessmentId(int veteranAssessmentId);
    
    VeteranAssessmentSurvey getByVeteranAssessmentIdAndSurveyId(int veteranAssessmentId, int surveyId);
    
    /**
     * Retrieves one tuple for each survey <br/>
     * <br/>
     * Row tuples have the following indices:<br/><ul>
     * <li>0 survey ID</li>
     * <li>1 count of all questions</li>
     * <li>2 count of all questions that were answered</li> 
     */
    List<Object[]> calculateProgress(int veteranAssessmentId);
    
    /**
     * Retrieves a list of surveys associated with veteranAssessmentId
     * @param veteranAssessmentId
     * @return
     */
    List<Survey> findSurveyListByVeteranAssessmentId(int veteranAssessmentId);
    
    /**
     * Retrieves a list of veteranAssessmentSurvey associated with veteranAssessmentId and specified surveyId
     * @param veteranAssessmentId
     * @param surveyId
     * @return
     */
    List<VeteranAssessmentSurvey> findSurveyBySurveyAssessment(int veteranAssessmentId, int surveyId);
}