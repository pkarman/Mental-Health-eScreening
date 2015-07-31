package gov.va.escreening.dto.report;

/**
 * Created by kliu on 3/1/15.
 */
public class ScoreHistoryDTO {

    private String screeningDate;
    private String clinicName;
    private String secondLine;

    public String getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(String screeningDate) {
        this.screeningDate = screeningDate;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }
}
