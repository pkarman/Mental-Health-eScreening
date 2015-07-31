package gov.va.escreening.domain;

public enum ExportTypeEnum {
    IDENTIFIED(1),
    DEIDENTIFIED(2);

    private final int exportTypeId;

    public int getExportTypeId() {
        return this.exportTypeId;
    }

    ExportTypeEnum(int exportTypeId) {
        this.exportTypeId = exportTypeId;
    }
}