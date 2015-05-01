package gov.va.escreening.dto.report;

import java.io.Serializable;

/**
 * Created by kliu on 3/17/15.
 */
public class SurveyTimeDTO implements Serializable {

    private String moduleName;
    private String moduleTime;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleTime() {
        return moduleTime;
    }

    public void setModuleTime(String moduleTime) {
        this.moduleTime = moduleTime;
    }
}
