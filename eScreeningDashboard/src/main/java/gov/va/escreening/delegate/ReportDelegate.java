package gov.va.escreening.delegate;

import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.security.EscreenUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 3/16/15.
 */
public interface ReportDelegate {
    Map<String, Object> getAvgScoresVetByClinicGraphReport(Map<String, Object> requestData, EscreenUser escreenUser);

    List<SurveyDto> getSurveyList();

    List<ClinicDto> getClinicDtoList();

    Map<String, Object> getAveScoresByClinicGraphOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount);

    Map<String, Object> getAvgScoresVetByClinicGraphicOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser);

    Map<String, Object> getIndividualStaticsGraphicPDF(Map<String, Object> requestData, EscreenUser escreenUser);

    List<Map<String, Object>> createIndivChartableDataForAvgScoresForPatientsByClinic(Map<String, Object> requestData);

    List<Map<String, Object>> createGrpChartableDataForAvgScoresForPatientsByClinic(Map<String, Object> requestData);

    List<Map<String, Object>> createChartableDataForIndividualStats(Map<String, Object> requestData);

    Map<String, Object> genIndividualStatisticsNumeric(Map<String, Object> requestData, EscreenUser escreenUser);

    Map<String, Object> genAvgScoresVetByClinicNumeric(HashMap<String, Object> requestData, EscreenUser escreenUser);

    Map<String,Object> genClinicStatisticReportsPart1eScreeningBatteriesReport(HashMap<String, Object> requestData, EscreenUser escreenUser);

    Map<String,Object> genClinicStatisticReportsPartIVAverageTimePerModuleReport(HashMap<String, Object> requestData, EscreenUser escreenUser);

    Map<String,Object> genClinicStatisticReportsPartVDemographicsReport(HashMap<String, Object> requestData, EscreenUser escreenUser);
}
