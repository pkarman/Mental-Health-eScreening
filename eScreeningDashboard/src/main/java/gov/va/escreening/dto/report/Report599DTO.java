package gov.va.escreening.dto.report;

/**
 * Created by kliu on 3/19/15.
 */
public class Report599DTO {
    private String moduleName;
    private String positivePercent;
    private String negativePercent;
    private String missingPercent;
    private String positiveCount;
    private String negativeCount;
    private String missingCount;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPositivePercent() {
        return positivePercent;
    }

    public void setPositivePercent(String positivePercent) {
        this.positivePercent = positivePercent;
    }

    public String getNegativePercent() {
        return negativePercent;
    }

    public void setNegativePercent(String negativePercent) {
        this.negativePercent = negativePercent;
    }

    public String getMissingPercent() {
        return missingPercent;
    }

    public void setMissingPercent(String missingPercent) {
        this.missingPercent = missingPercent;
    }

    public String getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(String positiveCount) {
        this.positiveCount = positiveCount;
    }

    public String getNegativeCount() {
        return negativeCount;
    }

    public void setNegativeCount(String negativeCount) {
        this.negativeCount = negativeCount;
    }

    public String getMissingCount() {
        return missingCount;
    }

    public void setMissingCount(String missingCount) {
        this.missingCount = missingCount;
    }
}
