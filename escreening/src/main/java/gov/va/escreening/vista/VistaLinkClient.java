package gov.va.escreening.vista;

import gov.va.escreening.delegate.SaveToVistaContext;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.vista.dto.ConsultationServiceNameDataSet;
import gov.va.escreening.vista.dto.ConsultationUrgencyDataSet;
import gov.va.escreening.vista.dto.HealthFactor;
import gov.va.escreening.vista.dto.HealthFactorHeader;
import gov.va.escreening.vista.dto.HealthFactorProvider;
import gov.va.escreening.vista.dto.HealthFactorVisitData;
import gov.va.escreening.vista.dto.MentalHealthAssessmentResult;
import gov.va.escreening.vista.dto.PatientDemographics;
import gov.va.escreening.vista.dto.ProgressNoteParameters;
import gov.va.escreening.vista.dto.VistaClinician;
import gov.va.escreening.vista.dto.VistaLocation;
import gov.va.escreening.vista.dto.VistaNoteTitle;
import gov.va.escreening.vista.dto.VistaProgressNote;
import gov.va.escreening.vista.dto.VistaServiceCategoryEnum;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by pouncilt on 4/9/14.
 */
public interface VistaLinkClient {
    VistaLocation findLocation(String locationName, String startingNameSearchCriteria) throws VistaLinkClientException;
    VistaLocation findLocation(String locationName, String startingNameSearchCriteria, Boolean sortDirection) throws VistaLinkClientException;
    VistaServiceCategoryEnum findServiceCategory(VistaServiceCategoryEnum initialServiceConnectionCategory, Long locationIEN, Boolean inpatientStatus) throws VistaLinkClientException;
    VistaServiceCategoryEnum findServiceCategory(VistaServiceCategoryEnum initialServiceConnectionCategory, Long locationIEN) throws VistaLinkClientException;
    VistaNoteTitle[] findProgressNoteTitles() throws VistaLinkClientException;
    VistaNoteTitle findProgressNoteTitle(String progressNoteTitleSearchCriteria) throws VistaLinkClientException;
    VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                    String startingNameSearchCriteria, String securityKeyFilterSearchCriteria,
                                    Date dateFilterSearchCriteria, Boolean sortDirectionFilterSearchCriteria,
                                    Boolean rdvUserFilterSearchCriteria, Boolean returnAllFilterSearchCriteria) throws VistaLinkClientException;
    VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                    String startingNameSearchCriteria, String securityKeyFilterSearchCriteria,
                                    Date dateFilterSearchCriteria) throws VistaLinkClientException;
    VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                    String startingNameSearchCriteria, Boolean sortDirectionFilterSearchCriteria)
            throws VistaLinkClientException;
    VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                    String startingNameSearchCriteria) throws VistaLinkClientException;
    VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                 String startingNameSearchCriteria, String securityKeyFilterSearchCriteria, Date dateFilterSearchCriteria,
                                 Boolean sortDirectionFilterSearchCriteria, Boolean rdvUserFilterSearchCriteria,
                                 Boolean returnAllFilterSearchCriteria) throws VistaLinkClientException;
    VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                 String startingNameSearchCriteria, String securityKeyFilterSearchCriteria, Date dateFilterSearchCriteria) throws VistaLinkClientException;
    VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria, String startingNameSearchCriteria, Boolean sortDirectionFilterSearchCriteria) throws VistaLinkClientException;
    VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria, String startingNameSearchCriteria) throws VistaLinkClientException;
    VistaProgressNote saveProgressNote(Long patientIEN, Long titleIEN, Long locationIEN, Long visitIEN, Date visitDateTime,
                                         String visitString, Object[] identifiers, String content,
                                         Boolean suppressCommitPostLogic, Boolean saveCrossReference, Boolean appendContent) throws VistaLinkClientException;
    VistaProgressNote saveProgressNote(ProgressNoteParameters progressNoteParameters) throws VistaLinkClientException;
    Boolean saveHealthFactor(Long noteIEN, Long locationIEN, Boolean historicalHealthFactor, HealthFactorHeader healthFactorHeader,
                             Set<HealthFactorVisitData> healthFactorVisitDataList, HealthFactorProvider healthFactorProvider,
                             Set<HealthFactor> healthFactors) throws VistaLinkClientException;
    Boolean saveMentalHealthAssessment(Long patientIEN, String mentalHealthTestName, String mentalHealthTestAnswers) throws VistaLinkClientException;
    MentalHealthAssessmentResult getMentalHealthAssessmentResults(Long reminderDialogIEN, Long patientIEN, String mentalHealthTestName, String dateCode, String staffCode, String mentalHealthTestAnswers) throws VistaLinkClientException;
    Boolean saveMentalHealthPackage(Long patientIEN, String mentalHealthTestName, Date date, String staffCode, String mentalHealthTestAnswers) throws VistaLinkClientException;
    PatientDemographics findPatientDemographics(Long patientIEN) throws VistaLinkClientException;
    ConsultationUrgencyDataSet getConsultationUrgencyDataSet(String consultationOrderType) throws VistaLinkClientException;
    ConsultationServiceNameDataSet getConsultationServiceNameDataSet(String startPositionSearchCriteria, String purpose, Boolean includeSynonyms) throws VistaLinkClientException;
    Long getTBIConsultDisplayGroupIEN(Long quickOrderIen, Long partPatientIEN, Long partLocationIEN, Long partProviderIEN, Boolean partInpatient, String partSex,Integer partAge, Long locationIEN);  
    
    Map<String, Long> getIENsMapForResponseList();
    public Map<String, Long> getConsultationServiceNameDataSet2(String startPositionSearchCriteria, String purpose,	Boolean includeSynonyms) throws VistaLinkClientException;
    public Map<String, Object> saveTBIConsultOrders(VeteranAssessment veteranAssessment, long quickOrderIen, String refTbiServiceName, Map<String, String> tbiexportColumnsMap);
    public Map<String, Map<String, String>> getConsultInfo(String orderType);
    
    void closeConnection();
	boolean savePainScale(VeteranAssessment veteranAssessment, String visitDate, SaveToVistaContext response);
}