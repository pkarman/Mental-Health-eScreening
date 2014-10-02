package gov.va.escreening.util;

import gov.va.escreening.domain.ExportDataDefaultValuesEnum;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.service.export.DataExtractor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("surveyResponsesHelper")
public class SurveyResponsesHelper {
	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat tf = new SimpleDateFormat("HH:mm:ss zzz");
	private static final String MISSING_DEFAULT = String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum());

	@Resource(name = "smrExportName")
	DataExtractor smrExportName;

	@Resource(name = "smrExportOtherName")
	DataExtractor smrExportOtherName;

	public Map<String, String> prepareSurveyResponsesMap(String surveyName,
			List<SurveyMeasureResponse> rawLst, boolean show) {

		List<SurveyMeasureResponse> smrLst = new ArrayList<SurveyMeasureResponse>();
		for (SurveyMeasureResponse smr : rawLst) {
			// the assessment' 'survey measure response' (smr) should be part of looked up survey && also (very
			// important)
			// DO NOT EXPORT A QUESTION WHEN THE QUESTION HAS THE ISMEASUREPPI ATTRIBUTE SET TO TRUE AND EXPORT TYPE IS
			// DE-IDENTIFIED!!!
			if (surveyName.equals(smr.getSurvey().getName()) && (!smr.getMeasure().getIsPatientProtectedInfo() || show)) {
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

	public final String miss() {
		return MISSING_DEFAULT;
	}

	public final String getOrMiss(String data) {
		return (data != null && !data.isEmpty()) ? data : miss();
	}

	public final String getTmAsStr(Date dateCreated) {
		return dateCreated != null ? tf.format(dateCreated) : "";
	}

	public final String getDtAsStr(Date dateCreated) {
		return dateCreated != null ? df.format(dateCreated) : "";
	}

	public final String getStrFromInt(Integer duration) {
		return duration == null ? "" : String.valueOf(duration);
	}

	public int getIntFromStr(String strVal) {
		return strVal == null ? 0 : Integer.parseInt(strVal.trim());
	}

	public float getFloatFromStr(String strVal) {
		return strVal == null ? 0.00F : Float.parseFloat(strVal.trim());
	}

	public DataExportCell createExportCell(Map<String, String> usrRespMap,
			Map<String, String> formulaeMap, String exportName, boolean show) {

		// try to find the user response
		String exportVal = usrRespMap == null ? null : usrRespMap.get(exportName);
		// if value of export name was an formulae (sum, avg, or some other fornula derived value)
		exportVal = getOrMiss(exportVal == null ? formulaeMap.get(exportName) : exportVal);
		// skip the MISSING value
		//return MISSING_DEFAULT.equals(colVal) ? null : new DataExportCell(colName, colVal);
		return new DataExportCell(exportName, exportVal);
	}
}
