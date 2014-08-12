package gov.va.escreening.service.export;

import gov.va.escreening.dto.DropDownObject;

import java.util.List;

public interface ExportDataFilterOptionsService {
	List<DropDownObject> getExportDataFilterOptions();
}