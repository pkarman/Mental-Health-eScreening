package gov.va.escreening.dto.dashboard;

import com.google.common.collect.Maps;
import gov.va.escreening.entity.ExportLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssessmentDataExport {

    String header;
    List<String> data;
    private DataExportFilterOptions filterOptions;
    private byte[] exportZip;


    public DataExportFilterOptions getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(DataExportFilterOptions filterOptions) {
        this.filterOptions = filterOptions;
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
        ade.setExportZip(exportLog.getExportZip());
        return ade;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssessmentDataExport that = (AssessmentDataExport) o;

        if (!filterOptions.equals(that.filterOptions)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return filterOptions.hashCode();
    }

    private String createHeaderFromDataExport(List<List<DataExportCell>> tableContent) {

        String header = null;

        if (tableContent != null && !tableContent.isEmpty()) {
            List<DataExportCell> firstRow = tableContent.iterator().next();
            StringBuilder sb = new StringBuilder();
            for (DataExportCell dec : firstRow) {
                sb.append(dec.getColumnName().replaceAll(",", "-").replaceAll("~", "_"));
                sb.append(",");
            }
            header = sb.toString();
        } else {
            // if export log is requested to be downloaded again
            header = getHeader();
        }

        return header;
    }

    private List<String> createDataFromDataExport(
            List<List<DataExportCell>> tableContent) {

        if (tableContent != null && !tableContent.isEmpty()) {
            List<String> rows = new ArrayList<String>();
            for (List<DataExportCell> row : tableContent) {
                StringBuilder sb = new StringBuilder();
                for (DataExportCell cell : row) {
                    sb.append(cell.getCellValue().replaceAll(",", "-"));
                    sb.append(",");
                }
                rows.add(sb.toString());
            }
            return rows;

        } else {
            // if export log is downloaded again
            return getData();
        }
    }

    public void setHeaderAndRows(List<List<DataExportCell>> tableContents) {
        this.header = createHeaderFromDataExport(tableContents);
        this.data = createDataFromDataExport(tableContents);
    }

    public boolean hasData() {
        return header != null && !header.isEmpty() && data != null && !data.isEmpty();
    }

    public void setExportZip(byte[] exportZip) {
        this.exportZip = exportZip;
    }

    public byte[] getExportZip() {
        return exportZip;
    }

    public Map<String, Object> getExportZipAsModel() {
        Map<String, Object> m = Maps.newHashMap();
        m.put("zipFileName", filterOptions.getFilePath());
        m.put("zippedBytes", exportZip);

        return m;
    }
}