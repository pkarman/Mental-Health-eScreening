package gov.va.escreening.dto;

import java.io.Serializable;

public class VeteranAssessmentNoteDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer veteranAssessmentNoteId;
    private Integer veteranAssessmentId;
    private Integer clinicalNoteId;
    private String clinicalNoteTitle;

    public Integer getVeteranAssessmentNoteId() {
        return veteranAssessmentNoteId;
    }

    public void setVeteranAssessmentNoteId(Integer veteranAssessmentNoteId) {
        this.veteranAssessmentNoteId = veteranAssessmentNoteId;
    }

    public Integer getVeteranAssessmentId() {
        return veteranAssessmentId;
    }

    public void setVeteranAssessmentId(Integer veteranAssessmentId) {
        this.veteranAssessmentId = veteranAssessmentId;
    }

    public Integer getClinicalNoteId() {
        return clinicalNoteId;
    }

    public void setClinicalNoteId(Integer clinicalNoteId) {
        this.clinicalNoteId = clinicalNoteId;
    }

    public String getClinicalNoteTitle() {
        return clinicalNoteTitle;
    }

    public void setClinicalNoteTitle(String clinicalNoteTitle) {
        this.clinicalNoteTitle = clinicalNoteTitle;
    }

    public VeteranAssessmentNoteDto() {

    }

}
