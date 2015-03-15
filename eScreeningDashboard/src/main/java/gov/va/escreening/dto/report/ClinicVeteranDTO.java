package gov.va.escreening.dto.report;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kliu on 3/14/15.
 */
public class ClinicVeteranDTO implements Serializable {

    private String clinicName;

    private List<VeteranModuleGraphReportDTO> veteranModuleGraphReportDTOs;

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public List<VeteranModuleGraphReportDTO> getVeteranModuleGraphReportDTOs() {
        return veteranModuleGraphReportDTOs;
    }

    public void setVeteranModuleGraphReportDTOs(List<VeteranModuleGraphReportDTO> veteranModuleGraphReportDTOs) {
        this.veteranModuleGraphReportDTOs = veteranModuleGraphReportDTOs;
    }
}
