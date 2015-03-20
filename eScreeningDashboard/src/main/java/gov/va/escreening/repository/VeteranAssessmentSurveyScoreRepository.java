package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentSurvey;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;

import java.util.List;

/**
 * Created by kliu on 3/3/15.
 */
public interface VeteranAssessmentSurveyScoreRepository extends RepositoryInterface<VeteranAssessmentSurveyScore> {
    public List<VeteranAssessmentSurveyScore> getDataForIndividual(Integer surveyId, Integer veteranId, String fromDate, String toDate);

    public List<VeteranAssessmentSurveyScore> getDataForIndividual(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate);

    public List<VeteranAssessmentSurveyScore> getIndividualDataForClicnic(Integer clinicIds, List<Integer> surveyIds,
                                                                          String fromDate, String toDate);
    public List getDataForClicnic(Integer clinicId, Integer surveyId,
                             String fromDate, String toDate);

    public List getAverageForClicnic(Integer clinicId, Integer surveyId,
                                            String fromDate, String toDate);
    public int getVeteranCountForClinic(Integer clinicId, Integer surveyId, String fromDate, String toDate);

}
