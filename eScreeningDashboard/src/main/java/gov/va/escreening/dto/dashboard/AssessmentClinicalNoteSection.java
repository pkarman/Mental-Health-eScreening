package gov.va.escreening.dto.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssessmentClinicalNoteSection implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String sectionName = "";
    private String displayName = "";
    private List<String> paragraphs = new ArrayList<String>();
    
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public List<String> getParagraphs() {
		return paragraphs;
	}
	public void setParagraphs(List<String> paragraphs) {
		this.paragraphs = paragraphs;
	}
}
