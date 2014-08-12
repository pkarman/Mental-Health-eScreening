package gov.va.escreening.domain;

public enum MeasureTypeEnum {
    FREETEXT(1),
    SELECTONE(2),
    SELECTMULTI(3),
    TABLEQUESTION(4),
    READONLYTEXT(5),
    SELECTONEMATRIX(6),
    SELECTMULTIMATRIX(7),
    INSTRUCTION(8);

    private final int measureTypeId;
    public int getMeasureTypeId() {
        return this.measureTypeId;
    }
    MeasureTypeEnum(int measureTypeId) {
        this.measureTypeId = measureTypeId;
    }
}