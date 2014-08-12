package gov.va.escreening.util;

import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.service.export.DataExtractor;
import gov.va.escreening.service.export.ModuleEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("surveyResponsesHelper")
public class SurveyResponsesHelper {

	@Resource(name = "smrExportName")
	DataExtractor smrExportName;

	@Resource(name = "smrExportOtherName")
	DataExtractor smrExportOtherName;

	public Map<String, String> prepareSurveyResponsesMap(String surveyName,
			List<SurveyMeasureResponse> rawLst, Integer identifiedExportType) {

		List<SurveyMeasureResponse> smrLst = new ArrayList<SurveyMeasureResponse>();
		for (SurveyMeasureResponse smr : rawLst) {
			// the assessment' 'survey measure response' (smr) should be part of looked up survey && also (very
			// important)
			// DO NOT EXPORT A QUESTION WHEN THE QUESTION HAS THE ISMEASUREPPI ATTRIBUTE SET TO TRUE AND EXPORT TYPE IS
			// DE-IDENTIFIED!!!
			if (surveyName.equals(smr.getSurvey().getName()) && !(smr.getMeasure().getIsPatientProtectedInfo() && ExportTypeEnum.DEIDENTIFIED.getExportTypeId() == identifiedExportType)) {
				smrLst.add(smr);
			}
		}

		// return null if there is no smr found for the target survey
		if (smrLst.isEmpty()) {
			return null;
		}

		Map<String, String> exportColumnsMap = new HashMap<String, String>();

		for (SurveyMeasureResponse smr : smrLst) {
			// need to examine smr twice. once for exportName and other one for possible chance of otherExportName
			Map<String, String> tuple = smrExportName.apply(smr);
			if (tuple != null) {
				exportColumnsMap.put(tuple.get("exportName"), tuple.get("exportableResponse"));
			}
			tuple = smrExportOtherName.apply(smr);
			if (tuple != null) {
				exportColumnsMap.put(tuple.get("exportName"), tuple.get("exportableResponse"));
			}
		}
		return exportColumnsMap;
	}

	public Survey isTBIConsultSelected(VeteranAssessment veteranAssessment) {
		for (SurveyMeasureResponse smr : veteranAssessment.getSurveyMeasureResponseList()) {
			if (ModuleEnum.ME_BTBIS.getModuleName().equals(smr.getSurvey().getName())) {
				MeasureAnswer ma = smr.getMeasureAnswer();
				if ("1".equals(ma.getCalculationValue()) && smr.getBooleanValue()) {
					return smr.getSurvey();
				}
			}
		}
		return null;
	}

	public Map<String, String> prepareSurveyResponsesMap(String surveyName,
			VeteranAssessment assessment, Integer identifiedExportType) {
		return prepareSurveyResponsesMap(surveyName, assessment.getSurveyMeasureResponseList(), identifiedExportType);
	}
}
