package gov.va.escreening.delegate;

import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.util.ReportsUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
@Component("indivStatNumeric")
public class ReportFunctionIndivStatNumeric extends ReportFunctionCommon implements ReportFunction {

    @Resource(name="scoreMap")
    ScoreMap scoreMap;

    @Override
    public void createReport(Object[] args) {
        Map<String, Object> requestData = (Map<String, Object>) args[0];
        Integer veteranId = (Integer) args[1];
        Integer surveyId = (Integer) args[2];
        List<TableReportDTO> reports = (List<TableReportDTO>) args[3];

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        if (isSplittableModule(surveyId, scoreMap.getAvMap())) {
            List<String> avNames = findSplittableAvNames(surveyId, scoreMap.getAvMap());
            for (String avName : avNames) {
                addTableReportDTO(surveyId, avName, veteranId, fromDate, toDate, reports);
            }
        } else {
            addTableReportDTO(surveyId, null, veteranId, fromDate, toDate, reports);
        }
    }

    private void addTableReportDTO(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate, List<TableReportDTO> resultList) {
        TableReportDTO tableReportDTO = scoreService.getSurveyDataForIndividualStatisticsReport(surveyId, avName, veteranId, fromDate, toDate);
        if (tableReportDTO != null) {
            resultList.add(tableReportDTO);
        }
    }
}
