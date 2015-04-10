package gov.va.escreening.dto.report;

import java.io.Serializable;

/**
 * Created by kliu on 3/17/15.
 */
public class SurveyTimeDTO implements Serializable {

    private String moduleName;
    private String moduleTotalCnt;
    private String moduleAvgMin;
    private String moduleAvgSec;
    private String moduleTime;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleTotalCnt() {
        return moduleTotalCnt;
    }

    public void setModuleTotalCnt(String moduleTotalCnt) {
        this.moduleTotalCnt = moduleTotalCnt;
    }

    public String getModuleAvgMin() {
        return moduleAvgMin;
    }

    public void setModuleAvgMin(String moduleAvgMin) {
        this.moduleAvgMin = moduleAvgMin;
    }

    public String getModuleAvgSec() {
        return moduleAvgSec;
    }

    public void setModuleAvgSec(String moduleAvgSec) {
        this.moduleAvgSec = moduleAvgSec;
    }

    public void setModuleTime(String moduleTime) {
        this.moduleTime = moduleTime;
    }

    public String getModuleTime() {
        return moduleTime;
    }
}
