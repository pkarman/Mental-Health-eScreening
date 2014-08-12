package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/5/14.
 */
public class HealthFactorHeader {
    private static final String dataElementName = "HDR";
    private Boolean inpatientStatus = false;
    private String unusedField3 = "";
    private String visitString = null;

    public HealthFactorHeader(Boolean inpatientStatus, String visitString) {
        if(inpatientStatus != null) this.inpatientStatus = inpatientStatus;
        this.visitString = visitString;
    }

    public static String getDataElementName() {
        return dataElementName;
    }

    public Boolean getInpatientStatus() {
        return inpatientStatus;
    }

    public String getUnusedField3() {
        return unusedField3;
    }

    public String getVisitString() {
        return visitString;
    }

    @Override
    public String toString(){
        return dataElementName + "^" + ((inpatientStatus)? "1" : "0") + "^" + unusedField3 + "^" + visitString;
    }
}
