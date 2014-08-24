package gov.va.escreening.service.export;

import gov.va.escreening.domain.ExportDataDefaultValuesEnum;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.util.SurveyResponsesHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ModuleExporterAbstract implements ModuleDataExporter {
	protected final Logger logger = LoggerFactory.getLogger(ModuleExporterAbstract.class);
	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat tf = new SimpleDateFormat("HH:mm:ss zzz");
	private static final String MISSING_DEFAULT = String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum());

	@Resource(name = "surveyResponsesHelper")
	protected SurveyResponsesHelper surveyResponsesHelper;

	protected String miss() {
		return MISSING_DEFAULT;
	}

	protected String getOrMiss(String data) {
		return (data != null && !data.isEmpty()) ? data : miss();
	}

	protected String getTmAsStr(Date dateCreated) {
		return dateCreated != null ? tf.format(dateCreated) : "";
	}

	protected String getDtAsStr(Date dateCreated) {
		return dateCreated != null ? df.format(dateCreated) : "";
	}

	protected String getStrFromInt(Integer duration) {
		return duration == null ? "" : String.valueOf(duration);
	}

	protected int getIntFromStr(String strVal) {
		return strVal == null ? 0 : Integer.parseInt(strVal.trim());
	}

	protected float getFloatFromStr(String strVal) {
		return strVal == null ? 0.00F : Float.parseFloat(strVal.trim());
	}

	@Override
	public List<DataExportCell> apply(ModuleEnum moduleEnum,
			VeteranAssessment assessment, Integer identifiedExportType) {

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Enter:%s--%s--%s--%s", moduleEnum.getCategory(), moduleEnum.name(), moduleEnum.getModuleName(), moduleEnum.getDescription()));
		}

		Map<String, String> usrRespMap = surveyResponsesHelper.prepareSurveyResponsesMap(moduleEnum.getModuleName(), assessment, identifiedExportType);

		return applyNow(moduleEnum.getModuleName(), usrRespMap, assessment);
	}

	protected DataExportCell create(Map<String, String> usrRespMap,
			String colName, CellValueExtractor cve) {

		return new DataExportCell(colName, getOrMiss(usrRespMap == null ? null : cve == null ? usrRespMap.get(colName) : cve.apply(colName, usrRespMap)));
	}

	protected DataExportCell create(Map<String, String> usrRespMap,
			String colName) {
		return create(usrRespMap, colName, null);
	}

	protected abstract List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment);

	protected String sumColumns(String[] columnsList, String sumColumnName,
			Map<String, String> usrRespMap) {

		int sum = sum(columnsList, usrRespMap);
		String strSum = String.valueOf(sum);
		usrRespMap.put(sumColumnName, strSum);
		return strSum;
	}

	protected int sum(String[] columnsList, Map<String, String> usrRespMap) {
		int sum = 0;
		for (String c : columnsList) {
			sum += getIntFromStr(usrRespMap.get(c));
		}
		return sum;
	}

}
