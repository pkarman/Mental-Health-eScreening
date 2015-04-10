package gov.va.escreening.form;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.domain.VeteranWithClinicalReminderFlag;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchCreateFormBean extends EditVeteranAssessmentFormBean 
{
	
	private static final long serialVersionUID = 1L;

	private List<VeteranWithClinicalReminderFlag> veterans;
	
	/**
	 * key -- VeteranID, value: list of survey IDs that are in Due clinical Reminders
	 */
	private Map<Integer, Set<Integer>> vetSurveyMap;
	
	private String program;
	private String clinic;

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getClinic() {
		return clinic;
	}

	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	public BatchCreateFormBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<VeteranWithClinicalReminderFlag> getVeterans() {
		return veterans;
	}

	public void setVeterans(List<VeteranWithClinicalReminderFlag> veterans) {
		this.veterans = veterans;
	}

	public Map<Integer, Set<Integer>> getVetSurveyMap() {
		return vetSurveyMap;
	}

	public void setVetSurveyMap(Map<Integer, Set<Integer>> vetSurveyMap) {
		this.vetSurveyMap = vetSurveyMap;
	}
	
	
}
