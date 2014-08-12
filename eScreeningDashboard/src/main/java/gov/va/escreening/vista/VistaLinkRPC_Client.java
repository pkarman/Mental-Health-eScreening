package gov.va.escreening.vista;

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
import gov.va.escreening.vista.request.ORQQCN_SVC_WITH_SYNONYMS_RequestParameters;
import gov.va.escreening.vista.request.ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequest;
import gov.va.escreening.vista.request.ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORQQPXRM_MENTAL_HEALTH_RESULTS_RequestParameters;
import gov.va.escreening.vista.request.ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequest;
import gov.va.escreening.vista.request.ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORQQPXRM_MENTAL_HEALTH_SAVE_RequestParameters;
import gov.va.escreening.vista.request.ORQQPXRM_MENTAL_HEALTH_SAVE_VistaLinkRequest;
import gov.va.escreening.vista.request.ORQQPXRM_MENTAL_HEALTH_SAVE_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORQQPXRM_MHV_RequestParameters;
import gov.va.escreening.vista.request.ORQQPXRM_MHV_VistaLinkRequest;
import gov.va.escreening.vista.request.ORQQPXRM_MHV_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORWDCN32_DEF_RequestParameters;
import gov.va.escreening.vista.request.ORWDCN32_DEF_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWDCN32_DEF_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORWPCE_GETSVC_RequestParameters;
import gov.va.escreening.vista.request.ORWPCE_GETSVC_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWPCE_GETSVC_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORWPCE_SAVE_RequestParameters;
import gov.va.escreening.vista.request.ORWPCE_SAVE_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWPCE_SAVE_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORWPT_SELECT_RequestParameters;
import gov.va.escreening.vista.request.ORWPT_SELECT_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWPT_SELECT_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORWU1_NEWLOC_RequestParameters;
import gov.va.escreening.vista.request.ORWU1_NEWLOC_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWU1_NEWLOC_VistaLinkRequestContext;
import gov.va.escreening.vista.request.ORWU_NEWPERS_RequestParameters;
import gov.va.escreening.vista.request.ORWU_NEWPERS_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWU_NEWPERS_VistaLinkRequestContext;
import gov.va.escreening.vista.request.TIU_CREATE_RECORD_RequestParameters;
import gov.va.escreening.vista.request.TIU_CREATE_RECORD_VistaLinkRequest;
import gov.va.escreening.vista.request.TIU_CREATE_RECORD_VistaLinkRequestContext;
import gov.va.escreening.vista.request.TIU_GET_PN_TITLES_VistaLinkRequest;
import gov.va.escreening.vista.request.TIU_GET_PN_TITLES_VistaLinkRequestContext;
import gov.va.escreening.vista.request.TIU_LOAD_BOILERPLATE_TEXT_RequestParameters;
import gov.va.escreening.vista.request.TIU_LOAD_BOILERPLATE_TEXT_VistaLinkRequest;
import gov.va.escreening.vista.request.TIU_LOAD_BOILERPLATE_TEXT_VistaLinkRequestContext;
import gov.va.escreening.vista.request.TIU_LOCK_RECORD_RequestParameters;
import gov.va.escreening.vista.request.TIU_LOCK_RECORD_VistaLinkRequest;
import gov.va.escreening.vista.request.TIU_LOCK_RECORD_VistaLinkRequestContext;
import gov.va.escreening.vista.request.TIU_SET_DOCUMENT_TEXT_RequestParameters;
import gov.va.escreening.vista.request.TIU_SET_DOCUMENT_TEXT_VistaLinkRequest;
import gov.va.escreening.vista.request.TIU_SET_DOCUMENT_TEXT_VistaLinkRequestContext;
import gov.va.escreening.vista.request.TIU_UNLOCK_RECORD_RequestParameters;
import gov.va.escreening.vista.request.TIU_UNLOCK_RECORD_VistaLinkRequest;
import gov.va.escreening.vista.request.TIU_UNLOCK_RECORD_VistaLinkRequestContext;
import gov.va.escreening.vista.request.VistaLinkRequest;
import gov.va.escreening.vista.request.VistaLinkRequestContext;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkVpidConnectionSpec;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;

import java.util.Date;
import java.util.Set;

/**
 * Created by pouncilt on 4/10/14.
 */
abstract class VistaLinkRPC_Client extends BaseVistaLinkRPC_Client implements VistaLinkClient {


    public VistaLinkRPC_Client(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkVpidConnectionSpec vpidConnectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
        super(vistaLinkManagedConnectionFactory, vpidConnectionSpec, appProxyName, rpcContext);
    }

    public VistaLinkRPC_Client(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkVpidConnectionSpec vpidConnectionSpec, String appProxyName, String rpcContext, Boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
        super(vistaLinkManagedConnectionFactory, vpidConnectionSpec, appProxyName, rpcContext, useProprietaryMessageFormat, connectionTimeOut);
    }

    public VistaLinkRPC_Client(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkDuzConnectionSpec duzConnectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
        super(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);
    }

    public VistaLinkRPC_Client(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkDuzConnectionSpec duzConnectionSpec, String appProxyName, String rpcContext, Boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
        super(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext, useProprietaryMessageFormat, connectionTimeOut);
    }

    @Override
    public VistaLocation findLocation(String locationName, String startingNameSearchCriteria) throws VistaLinkClientException {
        ORWU1_NEWLOC_RequestParameters requestParameters = new ORWU1_NEWLOC_RequestParameters(locationName, startingNameSearchCriteria);

        @SuppressWarnings("unchecked") VistaLinkRequestContext<ORWU1_NEWLOC_RequestParameters> context = new ORWU1_NEWLOC_VistaLinkRequestContext<ORWU1_NEWLOC_RequestParameters>(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaLocation> request = new ORWU1_NEWLOC_VistaLinkRequest(context);
        VistaLocation vistaLocation = request.sendRequest();
        return vistaLocation;
    }

    @Override
    public VistaLocation findLocation(String locationName, String startingNameSearchCriteria, Boolean sortDirection) throws VistaLinkClientException {
        ORWU1_NEWLOC_RequestParameters requestParameters = new ORWU1_NEWLOC_RequestParameters(locationName, startingNameSearchCriteria, sortDirection);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWU1_NEWLOC_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaLocation> request = new ORWU1_NEWLOC_VistaLinkRequest(context);
        VistaLocation vistaLocation = request.sendRequest();
        return vistaLocation;
    }

    @Override
    public VistaServiceCategoryEnum findServiceCategory(VistaServiceCategoryEnum initialServiceConnectionCategory, Long locationIEN) throws VistaLinkClientException {
        ORWPCE_GETSVC_RequestParameters requestParameters = new ORWPCE_GETSVC_RequestParameters(initialServiceConnectionCategory, locationIEN);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWPCE_GETSVC_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaServiceCategoryEnum> request = new ORWPCE_GETSVC_VistaLinkRequest(context);
        VistaServiceCategoryEnum serviceCategoryEnum = request.sendRequest();
        return serviceCategoryEnum;
    }

    @Override
    public VistaServiceCategoryEnum findServiceCategory(VistaServiceCategoryEnum initialServiceConnectionCategory, Long locationIEN, Boolean inpatientStatus) throws VistaLinkClientException {
        ORWPCE_GETSVC_RequestParameters requestParameters = new ORWPCE_GETSVC_RequestParameters(initialServiceConnectionCategory, locationIEN, inpatientStatus);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWPCE_GETSVC_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaServiceCategoryEnum> request = new ORWPCE_GETSVC_VistaLinkRequest(context);
        VistaServiceCategoryEnum serviceCategoryEnum = request.sendRequest();
        return serviceCategoryEnum;
    }

    @Override
    public VistaNoteTitle[] findProgressNoteTitles() throws VistaLinkClientException {
        VistaLinkRequestContext context = new TIU_GET_PN_TITLES_VistaLinkRequestContext(getRequest(), getConnection());
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaNoteTitle[]> request = new TIU_GET_PN_TITLES_VistaLinkRequest(context);
        VistaNoteTitle[] vistaNoteTitles = request.sendRequest();
        return vistaNoteTitles;
    }

    @Override
    public VistaNoteTitle findProgressNoteTitle(String progressNoteTitleSearchCriteria) throws VistaLinkClientException {
        VistaNoteTitle[] vistaNoteTitles = findProgressNoteTitles();
        VistaNoteTitle vistaNoteTitle = null;

        for(int i = 0; i < vistaNoteTitles.length; i++){
            if(vistaNoteTitles[i].getNoteTitleName().equalsIgnoreCase(progressNoteTitleSearchCriteria)){
                vistaNoteTitle = vistaNoteTitles[i];
                break;
            }
        }

        return vistaNoteTitle;
    }

    @Override
    public VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                           String startingNameSearchCriteria, String securityKeyFilterSearchCriteria,
                                           Date dateFilterSearchCriteria) throws VistaLinkClientException {
        ORWU_NEWPERS_RequestParameters requestParameters = new ORWU_NEWPERS_RequestParameters(firstNameSearchCriteria,
                lastNameSearchCriteria, startingNameSearchCriteria, securityKeyFilterSearchCriteria, dateFilterSearchCriteria);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWU_NEWPERS_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaClinician[]> request = new ORWU_NEWPERS_VistaLinkRequest(context);
        VistaClinician[] clinicians = request.sendRequest();
        return clinicians;
    }

    @Override
    public VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                           String startingNameSearchCriteria, String securityKeyFilterSearchCriteria,
                                           Date dateFilterSearchCriteria, Boolean sortDirectionFilterSearchCriteria,
                                           Boolean rdvUserFilterSearchCriteria, Boolean returnAllFilterSearchCriteria)
            throws VistaLinkClientException {
        ORWU_NEWPERS_RequestParameters requestParameters = new ORWU_NEWPERS_RequestParameters(firstNameSearchCriteria,
                lastNameSearchCriteria, startingNameSearchCriteria, sortDirectionFilterSearchCriteria, securityKeyFilterSearchCriteria,
                dateFilterSearchCriteria, rdvUserFilterSearchCriteria, returnAllFilterSearchCriteria);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWU_NEWPERS_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaClinician[]> request = new ORWU_NEWPERS_VistaLinkRequest(context);
        VistaClinician[] clinicians = request.sendRequest();
        return clinicians;
    }

    @Override
    public VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                           String startingNameSearchCriteria) throws VistaLinkClientException {
        VistaClinician[] clinicians = findClinicians(firstNameSearchCriteria, lastNameSearchCriteria, startingNameSearchCriteria, null, null);

        return clinicians;
    }

    @Override
    public VistaClinician[] findClinicians(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                           String startingNameSearchCriteria, Boolean sortDirectionFilterSearchCriteria) throws VistaLinkClientException {
        VistaClinician[] clinicians = findClinicians(firstNameSearchCriteria, lastNameSearchCriteria,
                startingNameSearchCriteria, null, null, sortDirectionFilterSearchCriteria, false, false);

        return clinicians;
    }

    @Override
    public VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                        String startingNameSearchCriteria, String securityKeyFilterSearchCriteria,
                                        Date dateFilterSearchCriteria) throws VistaLinkClientException {
        VistaClinician[] clinicians = findClinicians(firstNameSearchCriteria, lastNameSearchCriteria,
                startingNameSearchCriteria, securityKeyFilterSearchCriteria, dateFilterSearchCriteria);
        VistaClinician clinician = null;

        if (clinicians != null && clinicians.length > 0) {
            clinician = clinicians[0];
        }

        return clinician;
    }

    @Override
    public VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria,
                                        String startingNameSearchCriteria, String securityKeyFilterSearchCriteria,
                                        Date dateFilterSearchCriteria, Boolean sortDirectionFilterSearchCriteria,
                                        Boolean rdvUserFilterSearchCriteria, Boolean returnAllFilterSearchCriteria) throws VistaLinkClientException {
        VistaClinician[] clinicians = findClinicians(firstNameSearchCriteria, lastNameSearchCriteria,
                startingNameSearchCriteria, securityKeyFilterSearchCriteria,
                dateFilterSearchCriteria, sortDirectionFilterSearchCriteria, rdvUserFilterSearchCriteria, returnAllFilterSearchCriteria);
        VistaClinician clinician = null;

        if (clinicians != null && clinicians.length > 0) {
            clinician = clinicians[0];
        }

        return clinician;
    }

    @Override
    public VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria, String startingNameSearchCriteria) throws VistaLinkClientException {
        VistaClinician[] clinicians = findClinicians(firstNameSearchCriteria, lastNameSearchCriteria, startingNameSearchCriteria);
        VistaClinician clinician = null;

        if (clinicians != null && clinicians.length > 0) {
            clinician = clinicians[0];
        }

        return clinician;
    }

    @Override
    public VistaClinician findClinician(String firstNameSearchCriteria, String lastNameSearchCriteria, String startingNameSearchCriteria, Boolean sortDirectionFilterSearchCriteria) throws VistaLinkClientException {
        VistaClinician[] clinicians = findClinicians(firstNameSearchCriteria, lastNameSearchCriteria, startingNameSearchCriteria, sortDirectionFilterSearchCriteria);
        VistaClinician clinician = null;

        if (clinicians != null && clinicians.length > 0) {
            clinician = clinicians[0];
        }

        return clinician;
    }

    @Override
    public VistaProgressNote saveProgressNote(Long patientIEN, Long titleIEN, Long locationIEN, Long visitIEN,
                                              Date visitDateTime, String visitString, Object[] identifiers,
                                              String content, Boolean suppressCommitPostLogic,
                                              Boolean saveCrossReference, Boolean appendContent) throws VistaLinkClientException {
        VistaProgressNote progressNote = createProgressNote(patientIEN, titleIEN, visitDateTime, locationIEN, visitIEN,
                identifiers, visitString, false, false);

        lockProgressNote(progressNote.getIEN());
        try {
            progressNote.setBoilerPlateText(getProgressNoteBoilerPlateText(titleIEN, patientIEN, visitString));
            progressNote.setContent(content);
            setProgressNoteText(progressNote);
        } finally {
            unlockProgressNote(progressNote.getIEN());
        }

        return progressNote;
    }

    @Override
    public VistaProgressNote saveProgressNote(ProgressNoteParameters progressNoteParameters) throws VistaLinkClientException {
        VistaProgressNote progressNote = createProgressNote(progressNoteParameters.getPatientIEN(),
                progressNoteParameters.getTitleIEN(), progressNoteParameters.getVisitDateTime(),
                progressNoteParameters.getLocationIEN(), progressNoteParameters.getVisitIEN(),
                progressNoteParameters.getIdentifiers(), progressNoteParameters.getVisitString(),
                progressNoteParameters.isSuppressCommitPostLogic(), progressNoteParameters.isSaveCrossReference());

        lockProgressNote(progressNote.getIEN());
        try {
            progressNote.setBoilerPlateText(getProgressNoteBoilerPlateText(progressNoteParameters.getTitleIEN(),
                    progressNoteParameters.getPatientIEN(), progressNoteParameters.getVisitString()));
            progressNote.setContent(progressNoteParameters.getContent());
            setProgressNoteText(progressNote, false, progressNoteParameters.isAppendContent());
        } finally {
            unlockProgressNote(progressNote.getIEN());
        }

        return progressNote;
    }

    @Override
    public Boolean saveHealthFactor(Long noteIEN, Long locationIEN, Boolean historicalHealthFactor, HealthFactorHeader healthFactorHeader, Set<HealthFactorVisitData> healthFactorVisitDataList, HealthFactorProvider healthFactorProvider, Set<HealthFactor> healthFactors) throws VistaLinkClientException {
        ORWPCE_SAVE_RequestParameters requestParameters = new ORWPCE_SAVE_RequestParameters( noteIEN, locationIEN, historicalHealthFactor, healthFactorHeader,
                healthFactorVisitDataList, healthFactorProvider, healthFactors);
        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWPCE_SAVE_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<Boolean> request = new ORWPCE_SAVE_VistaLinkRequest(context);
        return request.sendRequest();
    }

    @Override
    public Boolean saveMentalHealthAssessment(Long patientIEN, String mentalHealthTestName, String mentalHealthTestAnswers) throws VistaLinkClientException{
        ORQQPXRM_MHV_RequestParameters requestParameters = new ORQQPXRM_MHV_RequestParameters(patientIEN, mentalHealthTestName, mentalHealthTestAnswers);
        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORQQPXRM_MHV_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<Boolean> request = new ORQQPXRM_MHV_VistaLinkRequest(context);
        return request.sendRequest();
    }

    @Override
    public MentalHealthAssessmentResult getMentalHealthAssessmentResults(Long reminderDialogIEN, Long patientIEN, String mentalHealthTestName, String dateCode, String staffCode, String mentalHealthTestAnswers) throws VistaLinkClientException {
        ORQQPXRM_MENTAL_HEALTH_RESULTS_RequestParameters requestParameters = new ORQQPXRM_MENTAL_HEALTH_RESULTS_RequestParameters(reminderDialogIEN, patientIEN, mentalHealthTestName, dateCode, staffCode, mentalHealthTestAnswers);
        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<MentalHealthAssessmentResult> request = new ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequest(context);
        return request.sendRequest();
    }

    @Override
    public Boolean saveMentalHealthPackage(Long patientIEN, String mentalHealthTestName, Date date, String staffCode, String mentalHealthTestAnswers) throws VistaLinkClientException {
        ORQQPXRM_MENTAL_HEALTH_SAVE_RequestParameters requestParameters = new ORQQPXRM_MENTAL_HEALTH_SAVE_RequestParameters(patientIEN, mentalHealthTestName, date, staffCode, mentalHealthTestAnswers);
        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORQQPXRM_MENTAL_HEALTH_SAVE_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<Boolean> request = new ORQQPXRM_MENTAL_HEALTH_SAVE_VistaLinkRequest(context);
        return request.sendRequest();
    }

    @Override
    public PatientDemographics findPatientDemographics(Long patientIEN) throws VistaLinkClientException {
        ORWPT_SELECT_RequestParameters requestParameters = new ORWPT_SELECT_RequestParameters(patientIEN);
        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWPT_SELECT_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<PatientDemographics> request = new ORWPT_SELECT_VistaLinkRequest(context);
        return request.sendRequest();
    }

    @Override
    public ConsultationUrgencyDataSet getConsultationUrgencyDataSet(String consultationOrderType) throws VistaLinkClientException {
        ORWDCN32_DEF_RequestParameters requestParameters = new ORWDCN32_DEF_RequestParameters(consultationOrderType);
        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORWDCN32_DEF_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<ConsultationUrgencyDataSet> request = new ORWDCN32_DEF_VistaLinkRequest(context);
        return request.sendRequest();
    }

    @Override
    public ConsultationServiceNameDataSet getConsultationServiceNameDataSet(String startPositionSearchCriteria, String purpose, Boolean includeSynonyms) throws VistaLinkClientException {
        ORQQCN_SVC_WITH_SYNONYMS_RequestParameters requestParameters = new ORQQCN_SVC_WITH_SYNONYMS_RequestParameters(startPositionSearchCriteria, purpose, includeSynonyms);
        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<ConsultationServiceNameDataSet> request = new ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequest(context);
        return request.sendRequest();
    }

    private boolean setProgressNoteText(VistaProgressNote progressNote) throws VistaLinkClientException {
        TIU_SET_DOCUMENT_TEXT_RequestParameters requestParameters = new TIU_SET_DOCUMENT_TEXT_RequestParameters(progressNote.getIEN(), progressNote.getBoilerPlateText(), progressNote.getContent());

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new TIU_SET_DOCUMENT_TEXT_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<Boolean> request = new TIU_SET_DOCUMENT_TEXT_VistaLinkRequest(context);
        Boolean progressNoteTextSet = request.sendRequest();

        return progressNoteTextSet;
    }

    private boolean setProgressNoteText(VistaProgressNote progressNote, Boolean suppressEditBuffer,
                                        Boolean appendContent) throws VistaLinkClientException {
        TIU_SET_DOCUMENT_TEXT_RequestParameters requestParameters = new TIU_SET_DOCUMENT_TEXT_RequestParameters(progressNote.getIEN(),
                progressNote.getBoilerPlateText(), progressNote.getContent(), appendContent, suppressEditBuffer);


        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new TIU_SET_DOCUMENT_TEXT_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<Boolean> request = new TIU_SET_DOCUMENT_TEXT_VistaLinkRequest(context);
        Boolean progressNoteTextSet = request.sendRequest();

        return progressNoteTextSet;
    }

    private String[] getProgressNoteBoilerPlateText(Long titleIEN, Long patientIEN, String visitString) throws VistaLinkClientException {
        TIU_LOAD_BOILERPLATE_TEXT_RequestParameters requestParameters = new TIU_LOAD_BOILERPLATE_TEXT_RequestParameters(patientIEN, titleIEN, visitString);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new TIU_LOAD_BOILERPLATE_TEXT_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<String[]> request = new TIU_LOAD_BOILERPLATE_TEXT_VistaLinkRequest(context);
        String[] progressNoteBoilerPlateText = request.sendRequest();

        return progressNoteBoilerPlateText;
    }

    private boolean lockProgressNote(Long progressNoteIEN) throws VistaLinkClientException {
        TIU_LOCK_RECORD_RequestParameters requestParameters = new TIU_LOCK_RECORD_RequestParameters(progressNoteIEN);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new TIU_LOCK_RECORD_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<Boolean> request = new TIU_LOCK_RECORD_VistaLinkRequest(context);
        Boolean progressNoteLocked = request.sendRequest();
        return progressNoteLocked;
    }

    private boolean unlockProgressNote(Long progressNoteIEN) throws VistaLinkClientException {
        TIU_UNLOCK_RECORD_RequestParameters requestParameters = new TIU_UNLOCK_RECORD_RequestParameters(progressNoteIEN);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new TIU_UNLOCK_RECORD_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<Boolean> request = new TIU_UNLOCK_RECORD_VistaLinkRequest(context);
        Boolean progressNoteUnlocked = request.sendRequest();
        return progressNoteUnlocked;
    }

    private VistaProgressNote createProgressNote(Long patientIEN, Long titleIEN, Date visitDateTime,
                                                 Long locationIEN, Long visitIEN, Object[] identifiers,
                                                 String visitString, Boolean suppressPostCommitCodeFromExecuting,
                                                 Boolean saveTelnetCrossReference) throws VistaLinkClientException{
        TIU_CREATE_RECORD_RequestParameters requestParameters = new TIU_CREATE_RECORD_RequestParameters(patientIEN,
                titleIEN, visitDateTime, locationIEN, visitIEN, identifiers, visitString, suppressPostCommitCodeFromExecuting,
                saveTelnetCrossReference);

        @SuppressWarnings("unchecked") VistaLinkRequestContext context = new TIU_CREATE_RECORD_VistaLinkRequestContext(getRequest(), getConnection(), requestParameters);
        @SuppressWarnings("unchecked") VistaLinkRequest<VistaProgressNote> request = new TIU_CREATE_RECORD_VistaLinkRequest(context);
        VistaProgressNote progressNote = request.sendRequest();
        return progressNote;
    }
}
