package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentSurvey;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;

import java.util.List;

/**
 * Created by kliu on 3/3/15.
 */
public interface VeteranAssessmentSurveyScoreRepository extends RepositoryInterface<VeteranAssessmentSurveyScore> {
    public List<VeteranAssessmentSurveyScore> getDataForIndividual(String lastName, String ssnLastFour, List<Integer> surveyIds, String fromDate, String toDate);

    public List<VeteranAssessmentSurveyScore> getIndividualDataForClicnic(List<Integer> clinicIds, List<Integer> surveyIds,
                                                                          String fromDate, String toDate);
    public List<VeteranAssessmentSurveyScore> getDataForClicnic(List<Integer> clinicIds, List<Integer> surveyIds,
                                                                String fromDate, String toDate);
}
