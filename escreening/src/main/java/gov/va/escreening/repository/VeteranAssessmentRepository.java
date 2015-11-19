package gov.va.escreening.repository;

import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.dto.report.Report593ByDayDTO;
import gov.va.escreening.dto.report.Report593ByTimeDTO;
import gov.va.escreening.dto.report.Report594DTO;
import gov.va.escreening.dto.report.Report595DTO;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Date;
import java.util.List;

public interface VeteranAssessmentRepository extends RepositoryInterface<VeteranAssessment> {

    /**
     * @param veteranId
     * @param assessmentStatusIdList
     * @return Will only return <strong>unarchived</strong> assessments which have one of the the given statuses.
     */
    List<VeteranAssessment> findByVeteranIdAndAssessmentStatusIdList(
            Integer veteranId, List<Integer> assessmentStatusIdList);

    /**
     * @param veteranId
     * @param assessmentStatusIdList
     * @return Will return <strong>any</strong> assessments which have one of the the given statuses.
     */
    List<VeteranAssessment> findAllByVeteranIdAndAssessmentStatusIdList(
    		Integer veteranId, List<Integer> assessmentStatusIdList);
    
    /**
     * @param programIdList
     * @return
     */
    List<VeteranAssessment> findByProgramIdList(List<Integer> programIdList);

    /**
     * @param veteranAssessmentId
     * @param veteranId
     * @param programId
     * @param clinicanId
     * @param createdByUserId
     * @param fromAssessmentDate
     * @param toAssessmentDate
     * @param programIdList
     * @param searchAttributes
     * @return
     */
    SearchResult<VeteranAssessment> searchVeteranAssessment(
            Integer veteranAssessmentId, Integer veteranId, Integer programId,
            Integer clinicanId, Integer createdByUserId,
            Date fromAssessmentDate, Date toAssessmentDate,
            List<Integer> programIdList, SearchAttributes searchAttributes);

    /**
     * @param clinicanId
     * @param createdByUserId
     * @param programId
     * @param fromAssessmentDate
     * @param toAssessmentDate
     * @param veteranId
     * @param programIdList
     * @return
     */
    List<VeteranAssessment> searchVeteranAssessmentForExport(Integer clinicanId,
                                                             Integer createdByUserId, Integer programId,
                                                             Date fromAssessmentDate, Date toAssessmentDate, Integer veteranId,
                                                             List<Integer> programIdList);

    /**
     * @param veteranAssessmentId
     * @return Date of the last modification for the given assessment. If there are not saved results yet a Date will be
     * returned with the value of January 1, 1970, 00:00:00 GMT.
     */
    Date getDateModified(int veteranAssessmentId);

    /**
     * Retrieves all the veteranAssessment veteran 'veteranId'
     *
     * @param veteranId
     * @return
     */
    List<VeteranAssessment> findByVeteranId(int veteranId);

    /**
     * Retrieves all the veteranAsessments for veteranId and programIdList
     *
     * @param veteranId
     * @param programIdList
     * @return
     */
    List<VeteranAssessment> findByVeteranIdAndProgramIdList(int veteranId,
                                                            List<Integer> programIdList);

    /**
     * Retrieves all measures for the given assessment. Note this can change if modules are added/removed from veteran
     * assessment, so this should only be called after we know it will not change (e.g. assessment is in a state !=
     * clean)
     *
     * @param veteranAssessmentId
     * @return
     */
    List<Measure> getMeasuresFor(Integer veteranAssessmentId);

    Integer getVeteranCountFor593(String fromDate, String toDate, List<Integer> clinicIds);

    Integer getBatteryCountFor593(String fromDate, String toDate, List<Integer> clinicIds);

    Integer getAvgDurantionFor593(String fromDate, String toDate, List<Integer> clinicIds);

    Integer getVetWithMultipleBatteriesFor593(String fromDate, String toDate, List<Integer> clinicIds);

    float getAvgNumOfAssessmentPerClinicianClinicFor593(String fromDate, String toDate, List<Integer> clinicIds);

    List<Report593ByDayDTO> getBatteriesByDayFor593(String fromDate, String toDate, List<Integer> clinicIds);

    List<Report593ByTimeDTO> getBatteriesByTimeFor593(String fromDate, String toDate, List<Integer>clinicIds);

    List<Report595DTO> getTopSkippedQuestions(List<Integer> clinicIds, String fromDate, String toDate);

    List<Integer> getGenderCount(List<Integer> clinicIds, String fromDate, String toDate, List<Integer> measureAnswerIds);

    Integer getMissingCountFor(List<Integer> cList, String fromDate, String toDate, int i);

    List<Number> getAgeStatistics(List<Integer> cList, String fromDate, String toDate);

    List<Number> getNumOfDeploymentStatistics(List<Integer> cList, String fromDate, String toDate);

    int getAssessmentCount(String fromDate, String toDate, List<Integer> clinicIds);

    List<Report594DTO> findAlertsCount(String fromDate, String toDate, List<Integer> clinicIds);
}