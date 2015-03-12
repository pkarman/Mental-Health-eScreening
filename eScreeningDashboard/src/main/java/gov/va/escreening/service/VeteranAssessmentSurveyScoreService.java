package gov.va.escreening.service;

import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.entity.VeteranAssessment;

/**
 * Created by munnoo on 3/6/15.
 */
public interface VeteranAssessmentSurveyScoreService {
    /**
     * Service method responsible to create Reportable Scores
     * @param veteranAssessment
     */
    void recordAllReportableScores(VeteranAssessment veteranAssessment);


    TableReportDTO getSurveyDataForIndividualStatisticsReport(Integer surveyId, Integer veteranId, String fromDate, String toDate);
}
