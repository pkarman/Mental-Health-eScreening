package gov.va.escreening.dto.ae;

//TODO: This seems to be very similar to ErrorCodeEnum.  We have to have all of these error code enums in one place.
public enum MheExceptionCode {

    AUTHENTICATION_FAILED(1001, "Failed to authenticate"),
    DATA_VALIDATION_FAILED(1002, "Data validation");

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    MheExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
