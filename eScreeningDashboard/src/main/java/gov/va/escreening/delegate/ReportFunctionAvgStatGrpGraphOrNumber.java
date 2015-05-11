package gov.va.escreening.delegate;

import gov.va.escreening.dto.report.ModuleGraphReportDTO;
import gov.va.escreening.util.ReportsUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
@Component("avgStatGrpGraphOrNumber")
public class ReportFunctionAvgStatGrpGraphOrNumber extends ReportFunctionCommon implements ReportFunction {
    @Resource(name="scoreMap")
    ScoreMap scoreMap;

    @Override
    public void createReport(Object[] args) {
        // userReqData, surveyId, clinicId, svgObject, graphReport, includeCount, isGraphOnly
        Map<String, Object> requestData = (Map<String, Object>) args[0];
        Integer surveyId = (Integer) args[1];
        Integer clinicId = (Integer) args[2];
        Map<String, String> svgObject = (Map<String, String>) args[3];
        List<ModuleGraphReportDTO> graphReport = (List<ModuleGraphReportDTO>) args[4];
        Boolean includeCount = (Boolean) args[5];
        Boolean isGraphOnly = (Boolean) args[6];

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        if (isSplittableModule(surveyId, scoreMap.getAvMap())) {
            List<String> avNames = findSplittableAvNames(surveyId, scoreMap.getAvMap());
            for (String avName : avNames) {
                addModuleGraphReportDTO(surveyId, avName, clinicId, svgObject, graphReport, fromDate, toDate, includeCount, isGraphOnly);
            }
        } else {
            addModuleGraphReportDTO(surveyId, null, clinicId, svgObject, graphReport, fromDate, toDate, includeCount, isGraphOnly);
        }
    }

    private void addModuleGraphReportDTO(Integer surveyId, String avName, Integer clinicId, Map<String, String> svgObject, List<ModuleGraphReportDTO> graphReport, String fromDate, String toDate, Boolean includeCount, Boolean isGraphOnly) {
        ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getGraphDataForClinicStatisticsGraph(clinicId, surveyId, avName, fromDate, toDate, includeCount);
        if (moduleGraphReportDTO != null) {
            graphReport.add(moduleGraphReportDTO);
            if (isGraphOnly) {
                moduleGraphReportDTO.setScoreHistoryTitle(null);
            }
            if (svgObject != null && !svgObject.isEmpty()) {
                String svgData = extractSvgObject(svgObject, surveyId, avName, null, clinicId);
                if (svgData != null) {
                    moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgData);
                }
            }
        }
    }
}
