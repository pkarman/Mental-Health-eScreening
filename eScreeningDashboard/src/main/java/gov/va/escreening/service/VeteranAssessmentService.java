package gov.va.escreening.service;

import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.domain.MentalHealthAssessment;
import gov.va.escreening.domain.VeteranAssessmentDto;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.VeteranAssessmentInfo;
import gov.va.escreening.dto.dashboard.AssessmentSearchResult;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.entity.Consult;
import gov.va.escreening.entity.DashboardAlert;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.form.AssessmentReportFormBean;
import gov.va.escreening.form.ExportDataFormBean;

import java.util.List;

/**
 * @author jocchiuzzo
 * 
 */
public interface VeteranAssessmentService {

    /**
     * Returns the Clean assessments for a veteran.
     * @param veteranId
     * @return
     */
    List<VeteranAssessment> getAvailableAssessmentsForVeteran(Integer veteranId);

    /**
     * Retrieves all the assessment based on the program ID list.
     * @param programIdList
     * @return
     */
    List<VeteranAssessment> getAssessmentsForProgramIdList(List<Integer> programIdList);

    /**
     * 
     * @param programIdList
     * @return
     */
    List<VeteranAssessmentDto> getAssessmentListForProgramIdList(List<Integer> programIdList);

    /**
     * Removes and adds surveys assigned associated with the veteranAssessmentId.
     * @param veteranAssessmentId
     * @param surveyIdList
     */
    void updateVeteranAssessmentSurveyIdList(int veteranAssessmentId, List<Integer> surveyIdList);

    /**
     * Creates a new veteranAssessment
     * @param veteranId
     * @param clinicianId
     * @param clinicId
     * @param createdByUserId
     * @return
     */
    VeteranAssessment create(int veteranId, int clinicianId, int clinicId, int createdByUserId);

    /**
     * Closes any existing veteran assessments that are active (clean, incomplete) and then creates a new one.
     * @param veteranId
     * @param createdByUserId
     * @return
     */
    Integer create(int veteranId, int createdByUserId);

    /**
     * 
     * @param assessmentReportFormBean
     * @param searchAttributes
     * @return
     */
    SearchResult<AssessmentSearchResult> searchVeteranAssessment(AssessmentReportFormBean assessmentReportFormBean,
            SearchAttributes searchAttributes);

    SearchResult<AssessmentSearchResult> searchVeteranAssessment(String programId, SearchAttributes searchAttributes);

    /**
     * 
     * @param exportDataFormBean
     * @return
     */
    List<VeteranAssessment> searchVeteranAssessmentForExport(ExportDataFormBean exportDataFormBean);

    /**
     * Returns the veteranAssessment by the primary key.
     * @param veteranAssessmentId
     * @return
     */
    VeteranAssessment findByVeteranAssessmentId(int veteranAssessmentId);

    /**
     * 
     * @param veteranId
     * @param clinicianId
     * @param clinicId
     * @param createdByUserId
     * @param accessMode
     * @return
     */
    VeteranAssessment create(int veteranId, int clinicianId, int clinicId, int createdByUserId, int accessMode);

    /**
     * Retrieves all the veteran assessments for veteran 'veteranId'
     * @param veteranId
     * @return
     */
    List<VeteranAssessmentDto> getVeteranAssessmentsForVeteran(int veteranId);

    /**
     * Retrieves all the veteran assessments for veteran 'veteranId' in Program 'programIdList'
     * @param veteranId
     * @param programIdList
     * @return
     */
    List<VeteranAssessmentDto> getVeteranAssessmentsForVeteranAndProgramIdList(int veteranId,
            List<Integer> programIdList);

    /**
     * Adds and removes clinical notes associated with the assessment based on 'clinicalNoteIdList'
     * @param veteranAssessmentId
     * @param clinicalNoteIdList
     */
    void updateVeteranAssessmentClinicalNoteIdList(int veteranAssessmentId, List<Integer> clinicalNoteIdList);

    /**
     * Creates a new veteran assessment and returns the system generated veteranAssessmentId.
     * @param veteranId
     * @param programId
     * @param clinicId
     * @param clinicianId
     * @param createdByUserId
     * @param selectedNoteTitleId
     * @param selectedBatteryId
     * @param surveyIdList
     * @return
     */
    Integer create(Integer veteranId, Integer programId, Integer clinicId, Integer clinicianId,
            Integer createdByUserId, Integer selectedNoteTitleId, Integer selectedBatteryId, List<Integer> surveyIdList);

    /**
     * Updates an existing veteran assessment.
     * @param veteranAssessmentId
     * @param programId
     * @param clinicId
     * @param clinicianId
     * @param updatedByUserId
     * @param selectedNoteTitleId
     * @param selectedBatteryId
     * @param surveyIdList
     */
    void update(Integer veteranAssessmentId, Integer programId, Integer clinicId, Integer clinicianId,
            Integer updatedByUserId, Integer selectedNoteTitleId, Integer selectedBatteryId, List<Integer> surveyIdList);

    /**
     * Adds the given Consult if it hasn't been added already.
     */
    void addConsult(Integer veteranAssessmentId, Consult consult);

    /**
     * Adds the given HealthFactor if it hasn't been added already.
     */
    void addHealthFactor(Integer veteranAssessmentId, HealthFactor healthFactor);

    /**
     * Adds the given DashboardAlert if it hasn't been added already.
     */
    void addDashboardAlert(Integer veteranAssessmentId, DashboardAlert dashboardAlert);

    /**
     * Determines if the Veteran Assessment is in a read-only state.
     * @param veteranAssessmentId
     * @return
     */
    boolean isReadOnly(Integer veteranAssessmentId);

    /**
     * Initializes visibility of all relevant measures for the given veteran assessment, to false. If this is called a
     * second time all previous entries for the given veteran assessment will be removed.
     */
    void initializeVisibilityFor(VeteranAssessment assessment);

    /**
     * Retrieves the VeteranAssessment for 'veteranAssessmentId'
     * @param veteranAssessmentId
     * @return
     */
    VeteranAssessment findOne(int veteranAssessmentId);

    /**
     * Retrieves the Veteran's Assessment, generates the CPRS Note using the template, grabs the Health Factors
     * associated with the Veteran's Assessment, and then saves it to VistA.
     * @param veteranAssessmentId The assessment to be saved to VistA
     * @param userId The user who initiated the save method
     */
    void saveVeteranAssessmentToVista(int veteranAssessmentId, int userId);

    /**
     * Updates the assessment status.
     * @param veteranAssessmentId
     * @param assessmentStatusEnum
     */
    void updateStatus(int veteranAssessmentId, AssessmentStatusEnum assessmentStatusEnum);

    /**
     * Looks up and retrieves the veteran and veteran assessment data in the bean.
     * @param veteranAssessmentId
     * @return
     */
    VeteranAssessmentInfo getVeteranAssessmentInfo(int veteranAssessmentId);

    /**
     * Retrieves all the surveys in a Veteran Assessment that has MHA tests
     * @param veteranAssessmentId
     * @return
     */
    List<MentalHealthAssessment> getMentalHealthAssessments(int veteranAssessmentId);

    /**
     * Saves the result of a MHA Test. Overwrites if it already exists.
     * @param veteranAssessmentId
     * @param surveyId
     * @prarm mhaResult
     */
    void saveMentalHealthTestResult(int veteranAssessmentId, int surveyId, String mhaResult);

    /**
     * Retrieves the CPRS Note for the Veteran Assessment.
     * @param veteranAssessmentId
     * @return
     */
    String getCprsNote(int veteranAssessmentId);

    /**
     * Updates a veteran assessment.
     * @param veteranAssessmentId
     * @param clinicId
     * @param noteTitleId
     * @param clinicianId
     * @param assessmentStatusId
     */
    void update(int veteranAssessmentId, Integer clinicId, Integer noteTitleId, Integer clinicianId,
            Integer assessmentStatusId);

    /**
     * Returns the list of titles of the health factors associated with veteran assessment.
     * @param veteranAssessmentId
     * @return
     */
    List<String> getHealthFactorReport(int veteranAssessmentId);
}
