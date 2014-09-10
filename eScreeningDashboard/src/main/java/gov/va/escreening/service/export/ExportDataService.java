package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.form.ExportDataFormBean;

import java.util.List;

public interface ExportDataService {

	AssessmentDataExport getAssessmentDataExport(
			ExportDataFormBean exportDataFormBean);

	ExportLog logDataExport(AssessmentDataExport dataExport) throws Exception;

	List<DataExportCell> createDataExportForOneAssessment(
			VeteranAssessment assessment, Integer identifiedExportType);

	AssessmentDataExport downloadExportData(Integer userId, int exportLogId,
			String comment);

}