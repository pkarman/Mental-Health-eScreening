package gov.va.escreening.delegate;

import gov.va.escreening.dto.report.ModuleGraphReportDTO;
import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.util.ReportsUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
@Component("indivStatGraphPlusNumber")
public class ReportFunctionIndivStatGraphNumber extends ReportFunctionCommon implements ReportFunction {
    @Override
    public void createReport(Object[] args) {
        Map<String, Object> requestData = (Map<String, Object>) args[0];
        Integer veteranId = (Integer) args[1];
        Integer surveyId = (Integer) args[2];
        List<ModuleGraphReportDTO> resultList = (List<ModuleGraphReportDTO>) args[3];

        Map<String, String> svgObject = (Map<String, String>) args[4];
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        if (isSplittableModule(surveyId)) {
            List<String> avNames = findSplittableAvNames(surveyId);
            for (String avName : avNames) {
                addModuleGraphReportDTO(svgObject, surveyId, avName, veteranId, fromDate, toDate, resultList);
            }
        } else {
            addModuleGraphReportDTO(svgObject, surveyId, null, veteranId, fromDate, toDate, resultList);
        }
    }

    private void addModuleGraphReportDTO(Map<String, String> svgObject, Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate, List<ModuleGraphReportDTO> resultList) {
        String svgData = (svgObject != null && !svgObject.isEmpty()) ? svgObject.get(getModuleName(surveyId, avName)) : null;
        if (svgData != null) {
            ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getGraphReportDTOForIndividual(surveyId, avName, veteranId, fromDate, toDate);
            if (moduleGraphReportDTO.getHasData()) {
                moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgData);
            }
            moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");
            resultList.add(moduleGraphReportDTO);
        }
    }
}
