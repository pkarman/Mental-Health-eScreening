package gov.va.escreening.dto.dashboard;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDataExport {

	public AssessmentDataExport() {
		tableContent = new ArrayList<List<DataExportCell>>();
	}

	private List<List<DataExportCell>> tableContent;
	private DataExportFilterOptions filterOptions;

	public List<List<DataExportCell>> getTableContent() {
		return tableContent;
	}

	public void setTableContent(List<List<DataExportCell>> tableContent) {
		this.tableContent = tableContent;
	}

	public DataExportFilterOptions getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(DataExportFilterOptions filterOptions) {
		this.filterOptions = filterOptions;
	}
}