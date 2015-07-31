package gov.va.escreening.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kliu on 2/23/15.
 */
public class ReportSearchFormBean implements Serializable {

    @NotEmpty(message = "The last 4 SSN is required")
    @Pattern(regexp = "\\d{4}", message = "The last 4 SSN is required")
    private String lastFourSsn;

    @Size(max = 30, message = "Last name must be no longer than 30 characters")
    @NotEmpty(message = "Last name is required")
    private String lastName;

    private boolean isNumeric;

    private boolean isGraph;

    @NotNull
    private List<Integer> surveyList;

    public String getLastFourSsn() {
        return lastFourSsn;
    }

    public void setLastFourSsn(String lastFourSsn) {
        this.lastFourSsn = lastFourSsn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    public void setNumeric(boolean isNumeric) {
        this.isNumeric = isNumeric;
    }

    public boolean isGraph() {
        return isGraph;
    }

    public void setGraph(boolean isGraph) {
        this.isGraph = isGraph;
    }

    public List<Integer> getSurveyList() {
        return surveyList;
    }

    public void setSurveyList(List<Integer> surveyList) {
        this.surveyList = surveyList;
    }
}
