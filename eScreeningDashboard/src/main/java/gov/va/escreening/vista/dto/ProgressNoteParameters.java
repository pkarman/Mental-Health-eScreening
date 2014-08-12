package gov.va.escreening.vista.dto;

//import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Date;

/**
 * Created by pouncilt on 4/18/14.
 */
public class ProgressNoteParameters {
    private Long patientIEN = null;  // Required to create Progress Note.
    private Long titleIEN = null;   // Required to create Progress Note.
    private Date visitDateTime = null; // Optional when creating Progress Note.
    private Long locationIEN = null;  // Optional when creating Progress Note.
    private Long visitIEN = null; // Optional when creating Progress Note.
    private String visitString = null; // Optional when creating Progress Note.
    private Object[] identifiers = null;// {    // Required to create Progress Note.
    private boolean suppressCommitPostLogic = false; // Required to create Progress Note.
    private boolean saveCrossReference = false; // Required to create Progress Note.
    private String content = null; // Required to create Progress Note.
    private boolean appendContent = false;   // Required to create Progress Note.

    public ProgressNoteParameters(Long patientIEN, Long titleIEN, Long locationIEN, Long visitIEN, Date visitDateTime,
                                  String visitString, Object[] identifiers, String content, Boolean appendContent) {

        this.patientIEN = patientIEN;
        this.titleIEN = titleIEN;
        this.locationIEN = locationIEN;
        this.visitIEN = visitIEN;
        this.visitDateTime = visitDateTime;
        this.visitString = visitString;
        this.identifiers = identifiers;
        this.content = content;
        this.appendContent = appendContent;

        checkRequiredParameters();

    }

    public ProgressNoteParameters(Long patientIEN, Long titleIEN, Long locationIEN, Long visitIEN, Date visitDateTime,
                                  String visitString, Object[] identifiers, String content,
                                  Boolean suppressCommitPostLogic, Boolean saveCrossReference, Boolean appendContent) {

        this.patientIEN = patientIEN;
        this.titleIEN = titleIEN;
        this.locationIEN = locationIEN;
        this.visitIEN = visitIEN;
        this.visitDateTime = visitDateTime;
        this.visitString = visitString;
        this.identifiers = identifiers;
        this.content = content;
        this.suppressCommitPostLogic = suppressCommitPostLogic;
        this.saveCrossReference = saveCrossReference;
        this.appendContent = appendContent;

        checkRequiredParameters();
    }

    private void checkRequiredParameters() {
        StringBuffer errorMessages = new StringBuffer();
        if (patientIEN == null) errorMessages.append("Required Argument(patientIEN) is missing or null");
        if (titleIEN == null) errorMessages.append("Required Argument(titleIEN) is missing or null");
        if (identifiers == null) errorMessages.append("Required Argument(identifiers) is missing or null");
        if (content == null) errorMessages.append("Required Argument(content) is missing or null");
        if (errorMessages.length() > 0) throw new IllegalArgumentException(errorMessages.toString());
    }

    public Long getPatientIEN() {
        return patientIEN;
    }

    public Long getTitleIEN() {
        return titleIEN;
    }

    public Date getVisitDateTime() {
        return visitDateTime;
    }

    public Long getLocationIEN() {
        return locationIEN;
    }

    public Long getVisitIEN() {
        return visitIEN;
    }

    public String getVisitString() {
        return visitString;
    }

    public Object[] getIdentifiers() {
        return identifiers;
    }

    public boolean isSuppressCommitPostLogic() {
        return suppressCommitPostLogic;
    }

    public boolean isSaveCrossReference() {
        return saveCrossReference;
    }

    public String getContent() {
        return content;
    }

    public boolean isAppendContent() {
        return appendContent;
    }
}
