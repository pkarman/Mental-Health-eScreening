package gov.va.escreening.vista.dto;

public enum VistaServiceCategoryEnum {

    A("AMBULATORY"),
    H("HOSPITALIZATION"),
    I("IN HOSPITAL"),
    C("CHART REVIEW"),
    T("TELECOMMUNICATIONS"),
    N("NOT FOUND"),
    S("DAY SURGERY"),
    O("OBSERVATION"),
    E("EVENT"),
    R("NURSING_HOME"),
    D("DAILY HOSPITALIZATION DATA"),
    X("ANCILLARY PACKAGE DAILY DATA");

    private final String description;

    public String getDescription() {
        return description;
    }

    VistaServiceCategoryEnum(String description) {
        this.description = description;
    }
}
