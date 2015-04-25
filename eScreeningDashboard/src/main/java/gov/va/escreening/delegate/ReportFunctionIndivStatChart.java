package gov.va.escreening.delegate;

import com.google.common.collect.Maps;
import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.util.ReportsUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
@Component("indivStatChart")
public class ReportFunctionIndivStatChart extends ReportFunctionCommon implements ReportFunction {
    @Resource(name="scoreMap")
    ScoreMap scoreMap;

    @Override
    public void createReport(Object[] args) {
        Map<String, Object> requestData = (Map<String, Object>) args[0];
        Integer veteranId = (Integer) args[1];
        Integer surveyId = (Integer) args[2];
        List<Map<String, Object>> chartableDataList = (List<Map<String, Object>>) args[3];

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        if (isSplittableModule(surveyId, this.scoreMap.getAvMap())) {
            List<String> avNames = findSplittableAvNames(surveyId, this.scoreMap.getAvMap());
            for (String avName : avNames) {
                createChartableDataForIndividualStatsForAv(surveyId, avName, veteranId, fromDate, toDate, chartableDataList);

            }
        } else {
            createChartableDataForIndividualStatsForAv(surveyId, null, veteranId, fromDate, toDate, chartableDataList);
        }
    }

    private void createChartableDataForIndividualStatsForAv(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate, List<Map<String, Object>> chartableDataList) {
        final Map<String, Object> chartableDataForIndividualStats = createChartableDataForIndividualStats(surveyId, avName, veteranId, fromDate, toDate);
        if (!chartableDataForIndividualStats.isEmpty()) {
            chartableDataList.add(chartableDataForIndividualStats);
        }
    }

    private Map<String, Object> createChartableDataForIndividualStats(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {
        Map<String, Object> chartableDataMap = Maps.newHashMap();

        final Map<String, Object> surveyDataForIndividualStatisticsGraph = scoreService.getSurveyDataForIndividualStatisticsGraph(surveyId, avName, veteranId, fromDate, toDate);

        final Map<String, Object> metaData = intervalService.generateMetadata(surveyId, avName);
        if (metaData != null) {
            metaData.put("score", !surveyDataForIndividualStatisticsGraph.isEmpty() ? getAvgFromData(surveyDataForIndividualStatisticsGraph) : 0);
        }
        chartableDataMap.put("dataSet", surveyDataForIndividualStatisticsGraph);
        chartableDataMap.put("dataFormat", metaData);

        return chartableDataMap;
    }


}
