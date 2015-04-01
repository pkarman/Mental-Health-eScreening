package gov.va.escreening.service;

import gov.va.escreening.dto.report.ModuleGraphReportDTO;
import gov.va.escreening.dto.report.Report599DTO;
import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.dto.report.VeteranModuleGraphReportDTO;
import gov.va.escreening.entity.VeteranAssessment;

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

    Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer surveyId, Integer veteranId, String fromDate, String toDate);
    
    Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate);

    TableReportDTO getSurveyDataForIndividualStatisticsReport(Integer surveyId, Integer veteranId, String fromDate, String toDate);

    ModuleGraphReportDTO getGraphReportDTOForIndividual(Integer surveyId, Integer veteranId, String fromDate, String toDate);


    Map<String, Object> getSurveyDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String fromDate, String toDate);
    
    ModuleGraphReportDTO getSurveyDataForVetClinicReport(Integer clinicId, Integer surveyIds, String fromDate, String toDate);

    ModuleGraphReportDTO getGraphDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String fromDate, String toDate, boolean containsCount);

     ModuleGraphReportDTO getSurveyDataForVetClinicReport(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate);

    List<Report599DTO> getClinicStatisticReportsPartVIPositiveScreensReport(String fromDate, String toDate, List<Integer> clinicIds, List<String> surveyNameList);
}
