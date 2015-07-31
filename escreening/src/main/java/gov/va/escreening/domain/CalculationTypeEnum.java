package gov.va.escreening.domain;

public enum CalculationTypeEnum {
    NUMBER(1),
    USERENTEREDNUMBER(2),
    USERENTEREDSTRING(3),
    USERENTEREDBOOLEAN(4);
    
    private final int calucationTypeId;
    public int getCalucationTypeId() {
        return this.calucationTypeId;
    }
    CalculationTypeEnum(int calucationTypeId) {
        this.calucationTypeId = calucationTypeId;
    }
}
