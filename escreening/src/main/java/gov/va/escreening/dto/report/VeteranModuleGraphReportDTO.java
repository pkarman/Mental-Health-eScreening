package gov.va.escreening.dto.report;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kliu on 3/4/15.
 */
public class VeteranModuleGraphReportDTO implements Serializable {

    private String lastNameAndSSN;

    private List<ModuleGraphReportDTO> moduleGraphs;

    public String getLastNameAndSSN() {
        return lastNameAndSSN;
    }

    public void setLastNameAndSSN(String lastNameAndSSN) {
        this.lastNameAndSSN = lastNameAndSSN;
    }

    public List<ModuleGraphReportDTO> getModuleGraphs() {
        return moduleGraphs;
    }

    public void setModuleGraphs(List<ModuleGraphReportDTO> moduleGraphs) {
        this.moduleGraphs = moduleGraphs;
    }
}
