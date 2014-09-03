package gov.va.escreening.dto.dashboard;

import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.ExportLogData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AssessmentDataExport {

	String header;
	List<String> data;
	private List<List<DataExportCell>> tableContent;
	private DataExportFilterOptions filterOptions;

	public List<List<DataExportCell>> getTableContent() {
		return tableContent;
	}

	public DataExportFilterOptions getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(DataExportFilterOptions filterOptions) {
		this.filterOptions = filterOptions;
	}

	public void setHeaderAndData(String header, List<String> data) {
		this.header = header;
		this.data = data;

		if (this.tableContent != null) {
			this.tableContent.clear();
		}
	}

	public String getHeader() {
		return header;
	}

	public List<String> getData() {
		return data;
	}

	public Integer getExportLogId() {
		return getFilterOptions().getExportLogId();
	}

	public void setExportLogId(Integer exportLogId) {
		getFilterOptions().setExportLogId(exportLogId);
	}

	public static AssessmentDataExport createFromExportLog(ExportLog exportLog) {
		AssessmentDataExport ade = new AssessmentDataExport();

		ade.setFilterOptions(createFilterOptionsFromExportLog(exportLog));
		Map<String, Object> headerAndDataMap = createHeaderAndDataFromExportLog(exportLog.getExportLogDataList());
		ade.setHeaderAndData((String) headerAndDataMap.get("header"), (List<String>) headerAndDataMap.get("data"));
		return ade;
	}

	private static Map<String, Object> createHeaderAndDataFromExportLog(
			List<ExportLogData> exportLogDataList) {

		Map<String, Object> headerAndDataMap = new HashMap<String, Object>();

		if (exportLogDataList == null) {
			return headerAndDataMap;
		}
		// extract the header--it is always the very first row (with index of 0)
		Iterator<ExportLogData> iterHeader = exportLogDataList.iterator();
		ExportLogData header = iterHeader.next();
		headerAndDataMap.put("header", header.getExportLogData());

		List<String> data = new ArrayList<String>();
		for (Iterator<ExportLogData> iterData = iterHeader; iterData.hasNext();) {
			ExportLogData eld = iterData.next();
			data.add(eld.getExportLogData());
		}
		headerAndDataMap.put("data", data);

		return headerAndDataMap;
	}

	private static DataExportFilterOptions createFilterOptionsFromExportLog(
			ExportLog exportLog) {
		DataExportFilterOptions newFilterOptions = new DataExportFilterOptions();
		newFilterOptions.setFilePath(exportLog.getFilePath());
		newFilterOptions.setAssessmentEnd(exportLog.getAssessmentEndFilter());
		newFilterOptions.setAssessmentStart(exportLog.getAssessmentStartFilter());
		newFilterOptions.setClinicianUserId(exportLog.getClinician() == null ? null : exportLog.getClinician().getUserId());
		newFilterOptions.setComment(exportLog.getComment());
		newFilterOptions.setCreatedByUserId(exportLog.getCreatedByUser() == null ? null : exportLog.getCreatedByUser().getUserId());
		newFilterOptions.setExportedByUserId(exportLog.getExportedByUser().getUserId());
		newFilterOptions.setExportLogId(exportLog.getExportLogId());
		newFilterOptions.setExportTypeId(exportLog.getExportType().getExportTypeId());
		newFilterOptions.setProgramId(exportLog.getProgram() == null ? null : exportLog.getProgram().getProgramId());
		newFilterOptions.setVeteranId(exportLog.getVeteran() == null ? null : exportLog.getVeteran().getVeteranId());
		return newFilterOptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((filterOptions == null) ? 0 : filterOptions.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AssessmentDataExport)) {
			return false;
		}
		AssessmentDataExport other = (AssessmentDataExport) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (filterOptions == null) {
			if (other.filterOptions != null) {
				return false;
			}
		} else if (!filterOptions.equals(other.filterOptions)) {
			return false;
		}
		if (header == null) {
			if (other.header != null) {
				return false;
			}
		} else if (!header.equals(other.header)) {
			return false;
		}
		return true;
	}

	public void setTableContents(List<List<DataExportCell>> tableContents) {
		this.tableContent = tableContents;
	}

	public boolean hasTableContents() {
		return getTableContent() != null && !getTableContent().isEmpty();
	}

	public boolean hasData() {
		return header != null && !header.isEmpty() && data != null && !data.isEmpty();
	}
}