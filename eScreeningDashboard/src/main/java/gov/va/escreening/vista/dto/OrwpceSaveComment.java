package gov.va.escreening.vista.dto;

import gov.va.escreening.vista.extractor.VistaRecord;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OrwpceSaveComment implements Serializable, VistaRecord  {

    private static final long serialVersionUID = 1L;

    private static final String prefix = "COM";

    private Integer sequenceNumber;
    private String commentText;

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public static String getPrefix() {
        return prefix;
    }

    public OrwpceSaveComment() {

    }

    public String toVistaRecord() {

        String[] pieces = new String[3];

        pieces[0] = prefix;
        pieces[1] = (sequenceNumber != null) ? sequenceNumber.toString() : "";
        pieces[2] = (commentText != null) ? commentText : "@";

        return StringUtils.join(pieces, "^");
    }
}
