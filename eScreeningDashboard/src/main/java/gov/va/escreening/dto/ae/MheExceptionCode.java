package gov.va.escreening.dto.ae;

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
