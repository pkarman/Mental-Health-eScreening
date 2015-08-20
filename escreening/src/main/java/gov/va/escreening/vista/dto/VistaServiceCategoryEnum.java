package gov.va.escreening.vista.dto;

public enum VistaServiceCategoryEnum {

    A("A"),
    H("H"),
    I("I"),
    C("C"),
    T("T"),
    N("N"),
    S("S"),
    O("O"),
    E("E"),
    R("R"),
    D("D"),
    X("X");

    private final String code;

    public String getCode() {
        return code;
    }

    VistaServiceCategoryEnum(String code) {
        this.code = code;
    }
}
