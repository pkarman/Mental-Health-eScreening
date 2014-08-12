package gov.va.escreening.context;
 
import gov.va.escreening.domain.VeteranDto;

import java.io.Serializable;

public class AssessmentContext implements Serializable {
 
    private static final long serialVersionUID = 1L;

    private Integer veteranAssessmentId;
    private Boolean isInitialized = false;
    private VeteranDto veteran;
    
	public Integer getVeteranAssessmentId() {
        return veteranAssessmentId;
    }
    public void setVeteranAssessmentId(Integer veteranAssessmentId) {
        this.veteranAssessmentId = veteranAssessmentId;
    }

    public Boolean getIsInitialized() {
        return isInitialized;
    }
    public void setIsInitialized(Boolean isInitialized) {
        this.isInitialized = isInitialized;
    }
    
    public VeteranDto getVeteran() {
		return veteran;
	}
    
	public void setVeteran(VeteranDto veteran) {
		this.veteran = veteran;
	}
	
	public AssessmentContext() {

    }

    @Override
	public String toString() {
		return "AssessmentContext [veteranAssessmentId=" + veteranAssessmentId
				+ ", isInitialized=" + isInitialized + ", veteran=" + veteran
				+ "]";
	}

}
