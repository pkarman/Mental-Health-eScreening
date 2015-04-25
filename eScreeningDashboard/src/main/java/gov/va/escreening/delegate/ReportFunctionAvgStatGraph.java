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
@Component("avgStatGraph")
public class ReportFunctionAvgStatGraph extends ReportFunctionCommon implements ReportFunction {
    @Resource(name = "scoreMap")
    ScoreMap scoreMap;

    @Override
    public void createReport(Object[] args) {
        // userReqData, vId, surveyId, clinicId, moduleGraphs, svgObject
        Map<String, Object> requestData = (Map<String, Object>) args[0];
        Integer veteranId = (Integer) args[1];
        Integer surveyId = (Integer) args[2];
        Integer clinicId = (Integer) args[3];
        List<ModuleGraphReportDTO> resultList = (List<ModuleGraphReportDTO>) args[4];
        Map<String, String> svgObject = (Map<String, String>) args[5];

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        if (isSplittableModule(surveyId, scoreMap.getAvMap())) {
            List<String> avNames = findSplittableAvNames(surveyId, scoreMap.getAvMap());
            for (String avName : avNames) {
                addModuleGraphReportDTO(svgObject, clinicId, surveyId, avName, veteranId, fromDate, toDate, resultList);
            }
        } else {
            addModuleGraphReportDTO(svgObject, clinicId, surveyId, null, veteranId, fromDate, toDate, resultList);
        }
    }

    private void addModuleGraphReportDTO(Map<String, String> svgObject, Integer clinicId, Integer surveyId, String avName, Integer vId, String fromDate, String toDate, List<ModuleGraphReportDTO> moduleGraphs) {
        ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getSurveyDataForVetClinicReport(clinicId, surveyId, avName, vId, fromDate, toDate);
        if (moduleGraphReportDTO.getHasData()) {
            String svgData = (svgObject != null && !svgObject.isEmpty()) ? svgObject.get(getModuleName(surveyId, avName, scoreMap.getAvMap())) : null;
            if (svgData != null) {
                moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgData);
            }
            moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");
        }
        moduleGraphs.add(moduleGraphReportDTO);
    }
}
