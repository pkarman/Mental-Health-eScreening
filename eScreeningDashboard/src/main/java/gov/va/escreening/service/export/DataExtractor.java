package gov.va.escreening.service.export;

import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.util.SurveyResponsesHelper;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public interface DataExtractor {
	final Logger logger = LoggerFactory.getLogger(DataExtractor.class);
	Map<String, String> apply(SurveyMeasureResponse smr, SurveyResponsesHelper srh);
}

@Component("smrExportName")
class ExportName implements DataExtractor {
	@Override
	public Map<String, String> apply(SurveyMeasureResponse smr, SurveyResponsesHelper srh) {
		MeasureAnswer ma = smr.getMeasureAnswer();

		// data export column we could be interested in
		String xportName = srh.buildExportName(smr, ma.getExportName());
		if (xportName==null){
			logger.warn(String.format("%s export name is null--returning null from here", ma));
			return null;
		}
		// user entered data
		String textValue = smr.getTextValue();
		Long numberValue = smr.getNumberValue();

		// marker to identify that this measure answer record was selected
		Boolean boolValue = smr.getBooleanValue();

		// both cannot be null, if it is then skip this
		// if (textValue == null && boolValue == null) {
		// continue;
		// }

		String exportableResponse = null;
		if (textValue != null && !textValue.trim().isEmpty()) {
			exportableResponse = textValue;
		} else if (numberValue != null) {
			exportableResponse = String.valueOf(numberValue);
		} else if (boolValue != null && boolValue) {
			exportableResponse = ma.getCalculationValue();
			if (exportableResponse == null) {
				exportableResponse = "1";
			}
		}

		if (exportableResponse != null) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("exportName", xportName);
			m.put("exportableResponse", exportableResponse);
			return m;
		} else {
			return null;
		}
	}
}

@Component("smrExportOtherName")
class ExportOtherName implements DataExtractor {
	@Override
	public Map<String, String> apply(SurveyMeasureResponse smr, SurveyResponsesHelper srh) {
		MeasureAnswer ma = smr.getMeasureAnswer();

		// data export column we could be interested in
		String xportName = srh.buildExportName(smr,ma.getOtherExportName());

		String otherValue = smr.getOtherValue();

		String exportableResponse = null;
		if ("other".equals(ma.getAnswerType()) && otherValue != null && !otherValue.trim().isEmpty()) {
			exportableResponse = otherValue;
		}

		if (exportableResponse != null) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("exportName", xportName);
			m.put("exportableResponse", exportableResponse);
			return m;
		} else {
			return null;
		}
	}
}
