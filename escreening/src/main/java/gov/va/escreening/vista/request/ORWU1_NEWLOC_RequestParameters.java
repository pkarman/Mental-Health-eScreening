package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 5/2/14.
 */
public class ORWU1_NEWLOC_RequestParameters extends VistaLinkRequestParameters {
    private String locationNameSearchCriteria = null;
    private String startingNameSearchCriteria = null;
    private Boolean sortDirectionFilterSearchCriteria = false;

    public ORWU1_NEWLOC_RequestParameters() {

    }

    public ORWU1_NEWLOC_RequestParameters(String locationNameSearchCriteria, String startingNameSearchCriteria, Boolean sortDirectionFilterSearchCriteria) {
        this.locationNameSearchCriteria = locationNameSearchCriteria;
        this.startingNameSearchCriteria = startingNameSearchCriteria;
        if (sortDirectionFilterSearchCriteria != null) this.sortDirectionFilterSearchCriteria = sortDirectionFilterSearchCriteria;

        checkRequiredParameters();
    }

    public ORWU1_NEWLOC_RequestParameters(String locationNameSearchCriteria, String startingNameSearchCriteria) {
        this.locationNameSearchCriteria = locationNameSearchCriteria;
        this.startingNameSearchCriteria = startingNameSearchCriteria;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterString("locationNameSearchCriteria", locationNameSearchCriteria,  false);
        super.checkRequestParameterString("startingNameSearchCriteria", startingNameSearchCriteria, false);
        super.checkRequestParameterBoolean("sortDirectionFilterSearchCriteria", sortDirectionFilterSearchCriteria, false);
    }

    public String getLocationNameSearchCriteria() {
        return locationNameSearchCriteria;
    }

    public String getStartingNameSearchCriteria() {
        return startingNameSearchCriteria;
    }

    public Boolean getSortDirectionFilterSearchCriteria() {
        return sortDirectionFilterSearchCriteria;
    }
}
