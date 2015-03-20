package gov.va.escreening.dto.report;

/**
 * Created by kliu on 3/1/15.
 */
public class TableReportDTO {

    private String moduleName;
    private String screeningModuleName;
    private String score;
    private String historyByClinic;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getScreeningModuleName() {
        return screeningModuleName;
    }

    public void setScreeningModuleName(String screeningModuleName) {
        this.screeningModuleName = screeningModuleName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHistoryByClinic() {
        return historyByClinic;
    }

    public void setHistoryByClinic(String historyByClinic) {
        this.historyByClinic = historyByClinic;
    }
}
