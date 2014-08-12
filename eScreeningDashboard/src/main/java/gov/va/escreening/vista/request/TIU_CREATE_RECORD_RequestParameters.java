package gov.va.escreening.vista.request;

import java.util.Date;

/**
 * Created by pouncilt on 5/5/14.
 */
public class TIU_CREATE_RECORD_RequestParameters extends VistaLinkRequestParameters {
    private Long patientIEN = null;  // Required to create Progress Note.
    private Long titleIEN = null;   // Required to create Progress Note.
    private Date vistaVisitDateTime = null; // Optional when creating Progress Note.
    private Long locationIEN = null;  // Optional when creating Progress Note.
    private Long visitIEN = null; // Optional when creating Progress Note.
    private Object[] identifiers = null;
    private String visitString = null; // Required to create Progress Note.
    Boolean suppressCommitPostLogic = false; // Required to create Progress Note.
    Boolean saveTelnetCrossReference = false; // Required to create Progress Note.

    public TIU_CREATE_RECORD_RequestParameters(Long patientIEN, Long titleIEN, Date vistaVisitDateTime, Long locationIEN,
                                               Long visitIEN, Object[] identifiers, String visitString,
                                               Boolean suppressCommitPostLogic, Boolean saveTelnetCrossReference) {
        this.patientIEN = patientIEN;
        this.titleIEN = titleIEN;
        this.vistaVisitDateTime = vistaVisitDateTime;
        this.locationIEN = locationIEN;
        this.visitIEN = visitIEN;
        this.identifiers = identifiers;
        this.visitString = visitString;
        if(suppressCommitPostLogic != null) this.suppressCommitPostLogic = suppressCommitPostLogic;
        if(saveTelnetCrossReference != null) this.saveTelnetCrossReference = saveTelnetCrossReference;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterLong("patientIEN", patientIEN, true);
        super.checkRequestParameterLong("titleIEN", titleIEN, true);
        super.checkRequestParameterDate("vistaVisitDateTime", vistaVisitDateTime, false);
        super.checkRequestParameterLong("locationIEN", locationIEN, false);
        super.checkRequestParameterLong("visitIEN", visitIEN, false);
        checkTIUXIdentifiersRequestParameter("identifiers", identifiers, true);
        super.checkRequestParameterString("visitString", visitString, false);
        super.checkRequestParameterBoolean("suppressCommitPostLogic", suppressCommitPostLogic, false);
        super.checkRequestParameterBoolean("saveTelnetCrossReference", saveTelnetCrossReference, false);
    }

    public void checkTIUXIdentifiersRequestParameter(String requestParameterName, Object[] tiuxIdentifiers, boolean required) {
        super.checkRequestParameterArray(requestParameterName, tiuxIdentifiers, required);
        for(int i = 0; i < tiuxIdentifiers.length; i++){
            try {
                if (i == 0) {
                    super.checkRequestParameterLong("tiuxIdentifier.authorIEN", (Long) tiuxIdentifiers[0], true);
                } else if (i == 1) {
                    super.checkRequestParameterDate("tiuxIdentifier.dateRequest", (Date) tiuxIdentifiers[1], true);
                } else if (i == 2) {
                    super.checkRequestParameterLong("tiuxIdentifier.locationIEN", (Long) tiuxIdentifiers[2], true);
                } else if (i == 3) {
                    super.checkRequestParameterString("tiuxIdentifier.subject", (String) tiuxIdentifiers[3], false);
                }
            } catch (IllegalArgumentException iae) {
                continue;
            }
        }
    }

    public Long getPatientIEN() {
        return patientIEN;
    }

    public Long getTitleIEN() {
        return titleIEN;
    }

    public Date getVistaVisitDateTime() {
        return vistaVisitDateTime;
    }

    public Long getLocationIEN() {
        return locationIEN;
    }

    public Long getVisitIEN() {
        return visitIEN;
    }

    public Object[] getIdentifiers() {
        return identifiers;
    }

    public String getVisitString() {
        return visitString;
    }

    public Boolean isSuppressCommitPostLogic() {
        return suppressCommitPostLogic;
    }

    public Boolean isSaveTelnetCrossReference() {
        return saveTelnetCrossReference;
    }
}
