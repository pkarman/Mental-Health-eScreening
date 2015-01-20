package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.form.ExportDataFormBean;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Table;

public interface ExportDataService {

	AssessmentDataExport getAssessmentDataExport(Map<String, Table<String, String, String>> dd,
			ExportDataFormBean exportDataFormBean);

	void takeAssessmentSnapShot();

	AssessmentDataExport downloadExportData(Integer userId, int exportLogId,
			String comment);

	List<DataExportCell> buildExportDataForOneAssessment(Map<String, Table<String, String, String>> dd,VeteranAssessment va,
			int identifiedExportType);

}