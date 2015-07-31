package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/5/14.
 */
public class HealthFactorProvider {
    private static final String dataElementName = "PRV";
    private String ien = null;
    private String unusedField3 = "";
    private String unusedField4 = "";
    private String name = null;
    private Boolean primaryPhysician = false;

    public HealthFactorProvider(String ien, String name, Boolean primaryPhysician) {
        if(ien == null) throw new NullPointerException("Parameter \"ien\" cannot be Null.");
        if(name == null) throw new NullPointerException("Parameter \"name\" cannot be Null.");

        this.ien = ien;
        this.name = name;
        if(primaryPhysician!= null) this.primaryPhysician = primaryPhysician;
    }

    public static String getDataElementName() {
        return dataElementName;
    }

    public String getIen() {
        return ien;
    }

    public String getUnusedField3() {
        return unusedField3;
    }

    public String getUnusedField4() {
        return unusedField4;
    }

    public String getName() {
        return name;
    }

    public Boolean getPrimaryPhysician() {
        return primaryPhysician;
    }

    @Override
    public String toString() {
        return dataElementName + "^" + ien + "^" + unusedField3 + "^" + unusedField4 + "^" + name + "^" + ((primaryPhysician)? "1": "0");
    }
}
