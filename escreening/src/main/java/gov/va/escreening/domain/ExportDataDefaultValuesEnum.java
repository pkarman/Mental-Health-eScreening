package gov.va.escreening.domain;

public enum ExportDataDefaultValuesEnum {
    FALSE(0),
    TRUE(1),
    MISSINGVALUE(999);

    private final int defaultValueNum;

    public int getDefaultValueNum() {
        return this.defaultValueNum;
    }

    ExportDataDefaultValuesEnum(int defaultValueNum) {
        this.defaultValueNum = defaultValueNum;
    }
}