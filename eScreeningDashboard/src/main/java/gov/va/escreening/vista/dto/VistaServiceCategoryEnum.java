package gov.va.escreening.vista.dto;

public enum VistaServiceCategoryEnum {

    AMBULATORY("A"),
    HOSPITALIZATION("H"),
    IN_HOSPITAL("I"),
    CHART_REVIEW("C"),
    TELECOMMUNICATIONS("T"),
    NOT_FOUND("N"),
    DAY_SURGERY("S"),
    OBSERVATION("O"),
    EVENT("E"),
    NURSING_HOME("R"),
    DAILY_HOSPITALIZATION_DATA("D"),
    ANCILLARY_PACKAGE_DAILY_DATA("X");

    private final String code;

    public String getCode() {
        return code;
    }

    VistaServiceCategoryEnum(String code) {
        this.code = code;
    }
}
