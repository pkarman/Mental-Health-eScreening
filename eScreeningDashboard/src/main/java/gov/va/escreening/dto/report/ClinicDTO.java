package gov.va.escreening.dto.report;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kliu on 3/14/15.
 */
public class ClinicDTO implements Serializable {

    private String clinicName;
    private List<ModuleGraphReportDTO> graphReportDTO;

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public List<ModuleGraphReportDTO> getGraphReportDTO() {
        return graphReportDTO;
    }

    public void setGraphReportDTO(List<ModuleGraphReportDTO> graphReportDTO) {
        this.graphReportDTO = graphReportDTO;
    }
}
