package gov.va.escreening.delegate;

import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.domain.ClinicalNoteDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranAssessmentDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.dto.SelectVeteranResultDto;
import gov.va.escreening.dto.VeteranAssessmentNoteDto;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.util.List;
import java.util.Map;

public interface CreateAssessmentDelegate {

	/**
	 * Searches both the local DB and VistA, removes duplicates based on VistA
	 * IEN.
	 * 
	 * @param escreenUser
	 * @param lastName
	 * @param ssnLastFour
	 * @return
	 */
	List<SelectVeteranResultDto> searchVeterans(EscreenUser escreenUser,
			String lastName, String ssnLastFour);

	/**
	 * Searches VistA only for veterans using 'lastName' and 'ssnLastFour'
	 * 
	 * @param escreenUser
	 * @param lastName
	 * @param ssnLastFour
	 * @return
	 */
	List<SelectVeteranResultDto> searchVistaForVeterans(
			EscreenUser escreenUser, String lastName, String ssnLastFour);

	/**
	 * Retrieves the veteran from the database if possible, updates the veteran
	 * demographics data if required. If 'veteranId' is not passed, then it
	 * tries to fetch the veteran from VistA using 'veteranIen'
	 * 
	 * @param escreenUser
	 * @param veteranId
	 * @param veteranIen
	 * @param forceRefresh
	 * @return
	 */
	VeteranDto fetchVeteran(EscreenUser escreenUser, Integer veteranId,
			String veteranIen, boolean forceRefresh);

	/**
	 * Retrieves the appointments for the veteran.
	 * 
	 * @param escreenUser
	 * @param veteranIen
	 */
	List<VistaVeteranAppointment> getVeteranAppointments(
			EscreenUser escreenUser, String veteranIen);

	/**
	 * Retrieves the clinical reminders for the veteran.
	 * 
	 * @param escreenUser
	 * @param veteranIen
	 * @return
	 */
	List<VistaVeteranClinicalReminder> getVeteranClinicalReminders(
			EscreenUser escreenUser, String veteranIen);

	/**
	 * Retrieves all the veteranAssessment for veteran 'veteranId' and programs
	 * 'programIdList'
	 * 
	 * @param veteranId
	 * @pram programIdList
	 * @return
	 */
	List<VeteranAssessmentDto> getVeteranAssessments(Integer veteranId,
			List<Integer> programIdList);

	/**
	 * Copies a VistA Veteran record and creates a new record in the database
	 * for it.
	 * 
	 * @param escreenUser
	 * @param veteranIen
	 * @return
	 */
	Integer importVistaRecord(EscreenUser escreenUser, String veteranIen);

	/**
	 * Closes any existing 'active' assessments and then creates a new one.
	 * 
	 * @param escreenUser
	 * @param veteranId
	 * @return
	 */
	Integer createVeteranAssessment(EscreenUser escreenUser, int veteranId);

	/**
	 * Retrieves all the clinical notes associated with 'programId'
	 * 
	 * @param programId
	 * @return
	 */
	List<ClinicalNoteDto> findAllClinicalNoteForProgramId(Integer programId);

	/**
	 * Retrieves all the clinical notes associated with 'veteranAssessmentId'
	 * 
	 * @param veteranAssessmentId
	 * @return
	 */
	List<VeteranAssessmentNoteDto> findAllClinicalNoteForVeteranAssessmentId(
			int veteranAssessmentId);

	/**
	 * Retrieves the veteranAssessment for 'veteranAssessmentId'
	 * 
	 * @param veteranAssessmentId
	 * @return
	 */
	VeteranAssessment getVeteranAssessmentByVeteranAssessmentId(
			int veteranAssessmentId);

	/**
	 * Gets the veteran from the database.
	 * 
	 * @param veteranId
	 * @return
	 */
	VeteranDto getVeteranFromDatabase(int veteranId);

	/**
	 * Retrieves all the survey in the system.
	 * 
	 * @return
	 */
	List<SurveyDto> getSurveyList();

	/**
	 * Returns all the surveys currently associated with the veteran assessment.
	 * 
	 * @param veteranAssessmentId
	 * @return
	 */
	List<SurveyDto> getVeteranAssessmentSurveyList(int veteranAssessmentId);

	/**
	 * Determines which surveys should be pre-selected based on the clinical
	 * reminders that are DUE NOW.
	 * 
	 * @param veteranClinicalReminderDtoList
	 * @return
	 */
	Map<Integer, String> getPreSelectedSurveyMap(
			List<VistaVeteranClinicalReminder> veteranClinicalReminderDtoList);

	/**
	 * Looks up the veteran's clinical reminders that are DUE NOW and then
	 * determines which surveys should be pre-selected.
	 * 
	 * @param escreenUser
	 * @param veteranIen
	 * @return
	 */
	Map<Integer, String> getPreSelectedSurveyMap(EscreenUser escreenUser,
			String veteranIen);

	/**
	 * Retrieves a dropdown list of all the programs in the system ordered by
	 * name.
	 * 
	 * @param programIdList
	 * @return
	 */
	List<DropDownObject> getProgramList(List<Integer> programIdList);

	/**
	 * Retrieves all the battery in the system.
	 * 
	 * @return
	 */
	List<DropDownObject> getBatteryList();

	/**
	 * Retrieves all the battery survey mappings.
	 * 
	 * @return
	 */
	List<BatterySurveyDto> getBatterySurveyList();

	/**
	 * Updates the veteran IEN field for veteranId.
	 * 
	 * @param veteranId
	 * @param veteranIen
	 */
	void updateVeteranIen(int veteranId, String veteranIen);

	/**
	 * Retrieves a dropdown list of clinics based on programId.
	 * 
	 * @param programId
	 * @return
	 */
	List<DropDownObject> getClinicList(int programId);

	/**
	 * Retrieves a dropdown list of clinicians based on programId.
	 * 
	 * @param clinicId
	 * @return
	 */
	List<DropDownObject> getClinicianList(int programId);

	/**
	 * Updates an existing veteran assessment.
	 * 
	 * @param escreenUser
	 * @param veteranAssessmentId
	 * @param selectedProgramId
	 * @param selectedClinicId
	 * @param selectedClinicianId
	 * @param selectedNoteTitleId
	 * @param selectedBatteryId
	 * @param selectedSurveyIdList
	 */
	void editVeteranAssessment(EscreenUser escreenUser,
			Integer veteranAssessmentId, Integer selectedProgramId,
			Integer selectedClinicId, Integer selectedClinicianId,
			Integer selectedNoteTitleId, Integer selectedBatteryId,
			List<Integer> selectedSurveyIdList);

	/**
	 * Creates a new veteran assessment and returns the system assigned veteran
	 * assessment id.
	 * 
	 * @param escreenUser
	 * @param veteranId
	 * @param selectedProgramId
	 * @param selectedClinicId
	 * @param selectedClinicianId
	 * @param selectedNoteTitleId
	 * @param selectedBatteryId
	 * @param selectedSurveyIdList
	 * @return
	 */
	Integer createVeteranAssessment(EscreenUser escreenUser, Integer veteranId,
			Integer selectedProgramId, Integer selectedClinicId,
			Integer selectedClinicianId, Integer selectedNoteTitleId,
			Integer selectedBatteryId, List<Integer> selectedSurveyIdList);

	/**
	 * Retrieves the note titles associated with either programId or 'clinicId'.
	 * 
	 * @param programId
	 * @return
	 */
	List<DropDownObject> getNoteTitleList(Integer programId);

	/**
	 * Looks up the veteran assessment and determines if it is in a read only
	 * state.
	 * 
	 * @param veteranAssessmentId
	 * @return
	 */
	boolean isVeteranAssessmentReadOnly(Integer veteranAssessmentId);

	/**
	 * returns all prgrams a battery is associated to
	 * 
	 * @param batteryId
	 * @return
	 */
	List<Program> getProgramsForBattery(int batteryId);
}
