package gov.va.escreening.domain;

public enum UserStatusEnum {

    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive"),
    DELETED(3, "Deleted");

    private final int userStatusId;
    private final String userStatusName;

    public int getUserStatusId() {
        return userStatusId;
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    UserStatusEnum(int userStatusId, String userStatusName) {
        this.userStatusId = userStatusId;
        this.userStatusName = userStatusName;
    }

}
