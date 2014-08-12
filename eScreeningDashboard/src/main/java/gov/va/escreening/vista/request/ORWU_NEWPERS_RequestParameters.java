package gov.va.escreening.vista.request;

import java.util.Date;

/**
 * Created by pouncilt on 5/3/14.
 */
public class ORWU_NEWPERS_RequestParameters extends VistaLinkRequestParameters {
    private String firstNameFilterSearchCriteria = null;
    private String lastNameFilterSearchCriteria = null;
    private String startingNameSearchCriteria = null;
    private Boolean sortDirectionFilterSearchCriteria = false;
    private String securityKeyFilterSearchCriteria = null;
    private Date dateFilterSearchCriteria = null;
    private Boolean rdvUserFilterSearchCriteria = false;
    private Boolean returnAllFilterSearchCriteria = false;

    public ORWU_NEWPERS_RequestParameters(String firstNameFilterSearchCriteria, String lastNameFilterSearchCriteria,
                                          String startingNameSearchCriteria, Boolean sortDirectionFilterSearchCriteria,
                                          String securityKeyFilterSearchCriteria, Date dateFilterSearchCriteria,
                                          Boolean rdvUserFilterSearchCriteria, Boolean returnAllFilterSearchCriteria){

        this.firstNameFilterSearchCriteria = firstNameFilterSearchCriteria;
        this.lastNameFilterSearchCriteria = lastNameFilterSearchCriteria;
        this.startingNameSearchCriteria = startingNameSearchCriteria;
        if(sortDirectionFilterSearchCriteria != null) this.sortDirectionFilterSearchCriteria = sortDirectionFilterSearchCriteria;
        this.securityKeyFilterSearchCriteria = securityKeyFilterSearchCriteria;
        this.dateFilterSearchCriteria = dateFilterSearchCriteria;
        if(rdvUserFilterSearchCriteria != null) this.rdvUserFilterSearchCriteria = rdvUserFilterSearchCriteria;
        if(returnAllFilterSearchCriteria != null) this.returnAllFilterSearchCriteria = returnAllFilterSearchCriteria;

        checkRequiredParameters();
    }

    public ORWU_NEWPERS_RequestParameters(String firstNameFilterSearchCriteria, String lastNameFilterSearchCriteria,
                                          String startingNameSearchCriteria, String securityKeyFilterSearchCriteria,
                                          Date dateFilterSearchCriteria){

        this.firstNameFilterSearchCriteria = firstNameFilterSearchCriteria;
        this.lastNameFilterSearchCriteria = lastNameFilterSearchCriteria;
        this.startingNameSearchCriteria = startingNameSearchCriteria;
        if(sortDirectionFilterSearchCriteria != null) this.sortDirectionFilterSearchCriteria = sortDirectionFilterSearchCriteria;
        this.securityKeyFilterSearchCriteria = securityKeyFilterSearchCriteria;
        this.dateFilterSearchCriteria = dateFilterSearchCriteria;
        if(rdvUserFilterSearchCriteria != null) this.rdvUserFilterSearchCriteria = rdvUserFilterSearchCriteria;
        if(returnAllFilterSearchCriteria != null) this.returnAllFilterSearchCriteria = returnAllFilterSearchCriteria;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterString("firstNameFilterSearchCriteria", firstNameFilterSearchCriteria, true);
        super.checkRequestParameterString("lastNameFilterSearchCriteria", lastNameFilterSearchCriteria, true);
        super.checkRequestParameterString("startingNameSearchCriteria", startingNameSearchCriteria, false);
        super.checkRequestParameterBoolean("sortDirectionFilterSearchCriteria", sortDirectionFilterSearchCriteria, true);
        super.checkRequestParameterString("securityKeyFilterSearchCriteria", securityKeyFilterSearchCriteria, false);
        super.checkRequestParameterDate("dateFilterSearchCriteria", dateFilterSearchCriteria, false);
        super.checkRequestParameterBoolean("rdvUserFilterSearchCriteria", rdvUserFilterSearchCriteria, false);
        super.checkRequestParameterBoolean("returnAllFilterSearchCriteria", returnAllFilterSearchCriteria, false);
    }

    public String getFirstNameFilterSearchCriteria() {
        return firstNameFilterSearchCriteria;
    }

    public String getLastNameFilterSearchCriteria() {
        return lastNameFilterSearchCriteria;
    }

    public String getStartingNameSearchCriteria() {
        return startingNameSearchCriteria;
    }

    public Boolean isSortDirectionFilterSearchCriteria() {
        return sortDirectionFilterSearchCriteria;
    }

    public String getSecurityKeyFilterSearchCriteria() {
        return securityKeyFilterSearchCriteria;
    }

    public Date getDateFilterSearchCriteria() {
        return dateFilterSearchCriteria;
    }

    public Boolean isRdvUserFilterSearchCriteria() {
        return rdvUserFilterSearchCriteria;
    }

    public Boolean isReturnAllFilterSearchCriteria() {
        return returnAllFilterSearchCriteria;
    }
}
