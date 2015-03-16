package gov.va.escreening.dto.report;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kliu on 3/14/15.
 */
public class ClinicDTO implements Serializable {

    private String clinicName;
    private List<ModuleGraphReportDTO> graphReport;

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public List<ModuleGraphReportDTO> getGraphReport() {
        return graphReport;
    }

    public void setGraphReport(List<ModuleGraphReportDTO> graphReport) {
        this.graphReport = graphReport;
    }
}
