package gov.va.escreening.domain;

//TODO: This seems to be very similar to MheExceptionCode.  We have to have all of these error code enums in one place.
public enum ErrorCodeEnum {

    DATA_VALIDATION(10, "Data validation"),
    OBJECT_NOT_FOUND(11, "Object with given ID does not exist");

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
