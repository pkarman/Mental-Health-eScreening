package gov.va.escreening.dto;

/**
 * Created by kliu on 2/22/15.
 */
public class ReportTypeDTO {

    private Integer reportTypeId;

    private String reportName;

    public Integer getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Integer reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
