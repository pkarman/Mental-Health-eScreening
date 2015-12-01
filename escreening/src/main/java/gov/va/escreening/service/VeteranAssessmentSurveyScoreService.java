package gov.va.escreening.service;

import gov.va.escreening.dto.report.ModuleGraphReportDTO;
import gov.va.escreening.dto.report.Report599DTO;
import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.dto.report.VeteranModuleGraphReportDTO;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;

import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 3/6/15.
 */
public interface VeteranAssessmentSurveyScoreService {
    /**
     * Service method responsible to create Reportable Scores
     * @param veteranAssessment
     */
    void recordAllReportableScores(VeteranAssessment veteranAssessment);

    List<VeteranAssessmentSurveyScore> processSelectedReportablePositiveScreens(VeteranAssessment veteranAssessment);

    Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate);
    
    Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer clinicId, Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate);

    TableReportDTO getSurveyDataForIndividualStatisticsReport(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate);

    ModuleGraphReportDTO getGraphReportDTOForIndividual(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate);


    Map<String, Object> getSurveyDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String avName, String fromDate, String toDate);
    
    ModuleGraphReportDTO getGraphDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String avName, String fromDate, String toDate, boolean containsCount);

     ModuleGraphReportDTO getSurveyDataForVetClinicReport(Integer clinicId, Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate);

    List<Report599DTO> getClinicStatisticReportsPartVIPositiveScreensReport(String fromDate, String toDate, List<Integer> clinicIds);
}
