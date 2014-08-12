package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.List;

public interface ModuleDataExporter {
	List<DataExportCell> apply(ModuleEnum modEnum, VeteranAssessment assessment, Integer identifiedExportType);
}
