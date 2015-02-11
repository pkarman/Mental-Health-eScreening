package gov.va.escreening.form;

import gov.va.escreening.domain.VeteranDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateVeteranAssessementsFormBean extends EditVeteranAssessmentFormBean 
{
	
	private static final long serialVersionUID = 1L;

	private List<VeteranDto> veterans;
	
	/**
	 * key -- VeteranID, value: list of survey IDs that are in Due clinical Reminders
	 */
	private Map<Integer, Set<Integer>> vetSurveyMap;

	public CreateVeteranAssessementsFormBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<VeteranDto> getVeterans() {
		return veterans;
	}

	public void setVeterans(List<VeteranDto> veterans) {
		this.veterans = veterans;
	}

	public Map<Integer, Set<Integer>> getVetSurveyMap() {
		return vetSurveyMap;
	}

	public void setVetSurveyMap(Map<Integer, Set<Integer>> vetSurveyMap) {
		this.vetSurveyMap = vetSurveyMap;
	}
	
	
}
