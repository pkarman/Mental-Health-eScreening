package gov.va.escreening.domain;

public enum ErrorCodeEnum {

    DATA_VALIDATION(10, "Data validation");

    private final int value;
    private final String description;

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    ErrorCodeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
