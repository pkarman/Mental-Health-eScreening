package gov.va.escreening.domain;

public enum RoleEnum {

    EVALUATION_AMDIN(1, "Consultation and Program Evaluation Administrator"),
    TECH_ADMIN(3, "Healthcare System Technical Administrator"),
    CLINICIAN(4, "Clinician"),
    ASSISTANT(5, "Assistant");

    private final int roleId;
    private final String roleName;

    public int getRoleId() {
        return this.roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    RoleEnum(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
