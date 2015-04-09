package gov.va.escreening.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by krizvi on 03/03/15.
 */
public class ReportAvgScoreFormBean implements Serializable {
    private boolean numeric;
    private boolean graph;
    private boolean individualData;
    private boolean groupData;
    private boolean allModules;
    private boolean allClinics;
    private Date fromDate;
    private Date toDate;

    //@NotNull
    private List<Integer> surveyList;
    //@NotNull
    private List<Integer> clinicList;

    public boolean isNumeric() {
        return numeric;
    }

    public void setNumeric(boolean numeric) {
        this.numeric = numeric;
    }

    public boolean isGraph() {
        return graph;
    }

    public void setGraph(boolean graph) {
        this.graph = graph;
    }

    public boolean isIndividualData() {
        return individualData;
    }

    public void setIndividualData(boolean individualData) {
        this.individualData = individualData;
    }

    public boolean isGroupData() {
        return groupData;
    }

    public void setGroupData(boolean groupData) {
        this.groupData = groupData;
    }

    public boolean isAllModules() {
        return allModules;
    }

    public void setAllModules(boolean allModules) {
        this.allModules = allModules;
    }

    public boolean isAllClinics() {
        return allClinics;
    }

    public void setAllClinics(boolean allClinics) {
        this.allClinics = allClinics;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<Integer> getSurveyList() {
        return surveyList;
    }

    public void setSurveyList(List<Integer> surveyList) {
        this.surveyList = surveyList;
    }

    public List<Integer> getClinicList() {
        return clinicList;
    }

    public void setClinicList(List<Integer> clinicList) {
        this.clinicList = clinicList;
    }
}
