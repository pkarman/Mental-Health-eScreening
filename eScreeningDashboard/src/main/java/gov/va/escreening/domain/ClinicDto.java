package gov.va.escreening.domain;

import java.io.Serializable;

public class ClinicDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer clinicId;
    private String clinicName;
    private Integer programId;
    private String programName;

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public ClinicDto() {

    }
}
