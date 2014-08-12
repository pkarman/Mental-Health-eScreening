package gov.va.escreening.dto.dashboard;

import java.io.Serializable;

public class VeteranSearchResult implements Serializable {

	public static String VETERAN_ID = "veteranId";
	public static String LAST_NAME = "lastName";
	public static String SSN_LAST_FOUR = "ssnLastFour";
	public static String EMAIL = "email";
	public static String GENDER = "gender";
	public static String LAST_ASSESSMENT_DATE = "lastAssessmentDate";
	public static String TOTAL_ASSESSMENTS = "totalAssessments";
	
    private static final long serialVersionUID = 1L;

    private Integer veteranId;
    private String vistaLocalPid = "";
    private String veteranName = "";
    private String ssnLastFour = "";
    private String email = "";
    private String gender = "";
    private String lastAssessmentDate = "";
    private Integer totalAssessments;

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public String getVistaLocalPid() {
        return vistaLocalPid;
    }

    public void setVistaLocalPid(String vistaLocalPid) {
        this.vistaLocalPid = vistaLocalPid;
    }

    public String getVeteranName() {
        return veteranName;
    }

    public void setVeteranName(String veteranName) {
        this.veteranName = veteranName;
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastAssessmentDate() {
        return lastAssessmentDate;
    }

    public void setLastAssessmentDate(String lastAssessmentDate) {
        this.lastAssessmentDate = lastAssessmentDate;
    }

    public Integer getTotalAssessments() {
        return totalAssessments;
    }

    public void setTotalAssessments(Integer totalAssessments) {
        this.totalAssessments = totalAssessments;
    }

    public VeteranSearchResult() {

    }

    @Override
    public String toString() {
        return "VeteranSearchResult [veteranId=" + veteranId + ", vistaLocalPid=" + vistaLocalPid + ", veteranName="
                + veteranName + ", ssnLastFour=" + ssnLastFour + ", email=" + email + ", gender=" + gender
                + ", lastAssessmentDate=" + lastAssessmentDate + ", totalAssessments=" + totalAssessments + "]";
    }

}
