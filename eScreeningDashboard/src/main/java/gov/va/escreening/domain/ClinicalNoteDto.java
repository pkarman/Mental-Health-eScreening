package gov.va.escreening.domain;

import java.io.Serializable;

public class ClinicalNoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer clinicalNoteId;
    private String clinicalNoteIen;
    private String title;

    public Integer getClinicalNoteId() {
        return clinicalNoteId;
    }

    public void setClinicalNoteId(Integer clinicalNoteId) {
        this.clinicalNoteId = clinicalNoteId;
    }

    public String getClinicalNoteIen() {
        return clinicalNoteIen;
    }

    public void setClinicalNoteIen(String clinicalNoteIen) {
        this.clinicalNoteIen = clinicalNoteIen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ClinicalNoteDto() {

    }

    public ClinicalNoteDto(String clinicalNoteIen, String title) {

        this.clinicalNoteIen = clinicalNoteIen;
        this.title = title;
    }

    @Override
    public String toString() {
        return "ClinicalNoteDto [clinicalNoteId=" + clinicalNoteId + ", clinicalNoteIen=" + clinicalNoteIen
                + ", title=" + title + "]";
    }

}
