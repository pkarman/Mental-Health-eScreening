package gov.va.escreening.vista.dto;

public enum VisitTypeEnum {

    ENCOUNTER_DATE("DT"),
    PATIENT("PT"),
    ENCOUNTER_LOCATION("HL"),
    ENCOUNTER_SERVICE_CATEGORY("VC"),
    PARENT_VISIT_IEN_HIST("PR"),
    OUTSIDE_LOCATION_HIST("OL");

    private final String code;

    public String getCode() {
        return code;
    }

    VisitTypeEnum(String code) {
        this.code = code;
    }
}
