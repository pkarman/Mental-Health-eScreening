package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 6/13/14.
 */
public class ORQQCN_SVC_WITH_SYNONYMS_RequestParameters extends VistaLinkRequestParameters {
    // startPositionSearchCriteria represents the service name or ien.
    // if startPositionSearchCriteria is equal to 1, the RPC will use it as the IEN for “All Services”, that way it will return everything.
    private String startPositionSearchCriteria = null;
    private String purpose = null;
    private Boolean includeSynonyms = null;

    public ORQQCN_SVC_WITH_SYNONYMS_RequestParameters(String startPositionSearchCriteria, String purpose, Boolean includeSynonyms) {
        this.startPositionSearchCriteria = startPositionSearchCriteria;
        if(!purpose.equals("0") && !purpose.equals("1")) {
            throw new IllegalArgumentException("Argument purpose can only be 0 or 1.");
        }
        this.purpose = purpose;
        this.includeSynonyms = (includeSynonyms != null)? includeSynonyms: false;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterString("startPositionSearchCriteria", startPositionSearchCriteria, true);
        super.checkRequestParameterString("purpose", purpose, true);
        super.checkRequestParameterBoolean("includeSynonyms", includeSynonyms, true);
    }

    public String getStartPositionSearchCriteria() {
        return startPositionSearchCriteria;
    }

    public String getPurpose() {
        return purpose;
    }

    public Boolean getIncludeSynonyms() {
        return includeSynonyms;
    }
}
