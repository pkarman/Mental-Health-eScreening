package gov.va.escreening.delegate;

import com.google.common.collect.Maps;
import gov.va.escreening.util.ReportsUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
@Component("avgStatGrpChart")
public class ReportFunctionAvgStatGrpChart extends ReportFunctionCommon implements ReportFunction {
    @Override
    public void createReport(Object[] args) {
        // requestData, surveyId, clinicId, chartableDataList
        Map<String, Object> requestData = (Map<String, Object>) args[0];
        Integer surveyId = (Integer) args[1];
        Integer clinicId = (Integer) args[2];
        List<Map<String, Object>> chartableDataList = (List<Map<String, Object>>) args[3];

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        if (isSplittableModule(surveyId)) {
            List<String> avNames = findSplittableAvNames(surveyId);
            for (String avName : avNames) {
                addData(clinicId, surveyId, avName, fromDate, toDate, chartableDataList);
            }
        } else {
            addData(clinicId, surveyId, null, fromDate, toDate, chartableDataList);
        }
    }

    private void addData(Integer clinicId, Integer surveyId, String avName, String fromDate, String toDate, List<Map<String, Object>> chartableDataList) {
        final Map<String, Object> chartableDataForClinic = createChartableDataForGrpAvgScoresForPatientsByClinic(clinicId, surveyId, avName, fromDate, toDate);
        if (!chartableDataForClinic.isEmpty()) {
            chartableDataList.add(chartableDataForClinic);
        }
    }

    private Map<String, Object> createChartableDataForGrpAvgScoresForPatientsByClinic(Integer clinicId, Integer surveyId, String avName, String fromDate, String toDate) {
        Map<String, Object> chartableDataMap = Maps.newHashMap();

        final Map<String, Object> surveyDataForClinicStatisticsGraph = scoreService.getSurveyDataForClinicStatisticsGraph(clinicId, surveyId, avName, fromDate, toDate);

        final Map<String, Object> metaData = intervalService.generateMetadata(surveyId, avName);
        if (metaData != null) {
            metaData.put("score", !surveyDataForClinicStatisticsGraph.isEmpty() ? surveyDataForClinicStatisticsGraph.values().iterator().next() : 0);
        }

        chartableDataMap.put("dataSet", surveyDataForClinicStatisticsGraph);
        chartableDataMap.put("dataFormat", metaData);

        return chartableDataMap;

    }

}
