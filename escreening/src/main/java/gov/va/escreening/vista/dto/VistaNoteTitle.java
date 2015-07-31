package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class VistaNoteTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long noteTitleIen;
    private String noteTitleName;

    public Long getNoteTitleIen() {
        return noteTitleIen;
    }

    public String getNoteTitleName() {
        return noteTitleName;
    }

    public VistaNoteTitle(Long noteTitleIen, String noteTitleName) {
        this.noteTitleIen = noteTitleIen;
        this.noteTitleName = noteTitleName;
    }

    @Override
    public String toString() {
        return "VistaNoteTitle [noteTitleIen=" + noteTitleIen + ", noteTitleName=" + noteTitleName + "]";
    }

}
