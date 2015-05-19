package gov.va.escreening.vista;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.delegate.SaveToVistaResponse;
import gov.va.escreening.delegate.VistaDelegate;
import gov.va.escreening.domain.MentalHealthAssessment;
import gov.va.escreening.entity.AssessmentAppointment;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentAuditLog;
import gov.va.escreening.entity.VeteranAssessmentAuditLogHelper;
import gov.va.escreening.repository.AssessmentAppointmentRepository;
import gov.va.escreening.repository.VeteranAssessmentAuditLogRepository;
import gov.va.escreening.repository.VistaRepository;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.AssessmentEngineService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.templateprocessor.TemplateProcessorService;
import gov.va.escreening.util.SurveyResponsesHelper;
import gov.va.escreening.vista.dto.HealthFactorHeader;
import gov.va.escreening.vista.dto.HealthFactorLists;
import gov.va.escreening.vista.dto.HealthFactorProvider;
import gov.va.escreening.vista.dto.HealthFactorVisitData;
import gov.va.escreening.vista.dto.MentalHealthAssessmentResult;
import gov.va.escreening.vista.dto.ProgressNoteParameters;
import gov.va.escreening.vista.dto.VisitInfo_DT;
import gov.va.escreening.vista.dto.VisitInfo_HL;
import gov.va.escreening.vista.dto.VisitInfo_OL;
import gov.va.escreening.vista.dto.VisitInfo_PR;
import gov.va.escreening.vista.dto.VisitInfo_PT;
import gov.va.escreening.vista.dto.VisitInfo_VC;
import gov.va.escreening.vista.dto.VistaDateFormat;
import gov.va.escreening.vista.dto.VistaProgressNote;
import gov.va.escreening.vista.dto.VistaServiceCategoryEnum;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;

import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("vistaDelegate")
public class VistaDelegateImpl implements VistaDelegate, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(VistaDelegateImpl.class);
    private MessageSource messageSource;

    private VeteranAssessmentService veteranAssessmentService;

    @Value("${quick.order.ien}")
    private long quickOrderIen;

    @Resource(name = "surveyResponsesHelper")
    SurveyResponsesHelper surveyResponsesHelper;

    @Resource(name = "rpcConnectionProvider")
    RpcConnectionProvider rpcConnectionProvider;

    @Resource(name = "assessmentEngineService")
    AssessmentEngineService assessmentEngineService;

    @Autowired
    private VeteranAssessmentAuditLogRepository veteranAssessmentAuditLogRepository;

    @Autowired
    private TemplateProcessorService templateProcessorService;

    @Autowired
    private VistaRepository vistaRepo;

    @Autowired
    private AssessmentAppointmentRepository assessmentApptRepo;

    @Autowired
    public void setVeteranAssessmentService(
            VeteranAssessmentService veteranAssessmentService) {
        this.veteranAssessmentService = veteranAssessmentService;
    }

    @Transactional(readOnly = false)
    @Override
    public SaveToVistaResponse saveVeteranAssessmentToVista(int veteranAssessmentId,
                                                            EscreenUser escreenUser) throws VistaLinkClientException {
        SaveToVistaResponse response = new SaveToVistaResponse(veteranAssessmentId, escreenUser);

        VeteranAssessment veteranAssessment = checkVeteranAssessment(veteranAssessmentId, escreenUser, response);
        if (!response.isDone(SaveToVistaResponse.PendingOperation.veteran)) {
            return response;
        }

        final VistaLinkClientStrategy vistaLinkClientStrategy = rpcConnectionProvider.createVistaLinkClientStrategy(escreenUser, "", "OR CPRS GUI CHART");
        final VistaLinkClient vistaLinkClient = vistaLinkClientStrategy.getClient();
        Long patientIEN = Long.parseLong(veteranAssessment.getVeteran().getVeteranIen());

        // Get Mental Health Assessments
        MentalHealthAssessmentResult mentalHealthAssessmentResult = saveMentalHealthAssessments(patientIEN, veteranAssessment, vistaLinkClient, response);
        if (!response.isDone(SaveToVistaResponse.PendingOperation.mha)) {
            return response;
        }

        // Generate CPRS Note based on the responses to the survey
        // questions.
        Long locationIEN = Long.parseLong(veteranAssessment.getClinic().getVistaIen());
        Boolean inpatientStatus = vistaLinkClient.findPatientDemographics(patientIEN).getInpatientStatus();
        VistaServiceCategoryEnum encounterServiceCategory = vistaLinkClient.findServiceCategory(VistaServiceCategoryEnum.AMBULATORY, locationIEN, inpatientStatus);
        Date visitDateTime = (veteranAssessment.getDateCompleted() != null) ? veteranAssessment.getDateCompleted() : new Date();
        String visitDate = VistaUtils.convertToVistaDateString(visitDateTime, VistaDateFormat.MMddHHmmss);
        String visitStr = findVisitStr(escreenUser, patientIEN.toString(), veteranAssessment);
        String visitString = visitStr != null ? visitStr : (locationIEN + ";" + visitDate + ";" + ((encounterServiceCategory != null) ? encounterServiceCategory.getCode() : VistaServiceCategoryEnum.AMBULATORY.getCode()));

        VistaProgressNote progressNote = saveProgressNote(patientIEN, locationIEN, visitString, veteranAssessment, vistaLinkClient, response);
        if (!response.isDone(SaveToVistaResponse.PendingOperation.cprs)) {
            return response;
        }


        // Do Health Factors
        saveMentalHealthFactors(locationIEN, visitString, visitDate, inpatientStatus, progressNote.getIEN(), veteranAssessment, vistaLinkClient, response);
        if (!response.isDone(SaveToVistaResponse.PendingOperation.hf)) {
            return response;
        }

        // save TBI Consult request
        saveTbiConsultRequest(veteranAssessment, vistaLinkClient, response);
        if (!response.isDone(SaveToVistaResponse.PendingOperation.tbi)) {
            return response;
        }

        savePainScale(veteranAssessment, visitDate, vistaLinkClient, response);
        if (!response.isDone(SaveToVistaResponse.PendingOperation.pain_scale)) {
            return response;
        }

        // save this activity in audit log
        VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_VISTA_SAVE, veteranAssessment.getAssessmentStatus().getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
        veteranAssessmentAuditLogRepository.update(auditLogEntry);
        return response;
    }

    private void savePainScale(VeteranAssessment veteranAssessment, String visitDate, VistaLinkClient vistaLinkClient, SaveToVistaResponse response) {
        vistaLinkClient.savePainScale(veteranAssessment, visitDate, response);
        response.requestDone(SaveToVistaResponse.PendingOperation.pain_scale);
    }

    private VeteranAssessment checkVeteranAssessment(int veteranAssessmentId, EscreenUser escreenUser, SaveToVistaResponse response) {
        VeteranAssessment veteranAssessment = null;
        if (!escreenUser.getCprsVerified()) {
            response.addUserError(SaveToVistaResponse.PendingOperation.veteran, msg(SaveToVistaResponse.MsgKey.usr_err_vet__verification));
        } else {
            // 1. Get Veteran's assessment.
            veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
            if (veteranAssessment == null) {
                response.addUserError(SaveToVistaResponse.PendingOperation.veteran, msg(SaveToVistaResponse.MsgKey.usr_err_vet__not_found));
            } else if (StringUtils.isEmpty(veteranAssessment.getVeteran().getVeteranIen())) {
                // 2. Make sure Veteran has been mapped to a VistA record. Else, this
                // will not work.
                response.addUserError(SaveToVistaResponse.PendingOperation.veteran, msg(SaveToVistaResponse.MsgKey.usr_err_vet__failed_mapping));
            }
        }
        response.requestDone(SaveToVistaResponse.PendingOperation.veteran);
        return veteranAssessment;
    }

    private String findVisitStr(EscreenUser user, String vetIen, VeteranAssessment assessment) {
        AssessmentAppointment assessAppt = assessmentApptRepo.findByAssessmentId(assessment.getVeteranAssessmentId());
        if (assessAppt == null) return null;

        long date = assessAppt.getAppointmentDate().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(assessAppt.getAppointmentDate());
        c.add(Calendar.HOUR, 24);
        c.set(Calendar.HOUR, 0); //Set the end date to midnight next day.
        List<VistaVeteranAppointment> apptList = vistaRepo.getVeteranAppointments(user.getVistaDivision(),
                user.getVistaVpid(), user.getVistaDuz(), assessAppt.getAppointmentDate(), c.getTime(), vetIen);

        VistaVeteranAppointment picked = null;
        for (VistaVeteranAppointment appt : apptList) {
            if (appt.getClinicName() != null && appt.getClinicName().equals(assessment.getClinic().getName())) {
                if (picked == null) {
                    picked = appt;
                } else if (Math.abs(picked.getAppointmentDate().getTime() - date)
                        > Math.abs(appt.getAppointmentDate().getTime() - date)) {
                    picked = appt;
                }

            }
        }

        if (picked != null) {
            String visitString = picked.getVisitStr();
            String[] part = visitString.split(";");
            if (part.length == 3) {
                return String.format("%s;%s;%s", part[2], part[1], part[0]);
            }
        }
        return null;
    }

    private void saveTbiConsultRequest(VeteranAssessment veteranAssessment,
                                       VistaLinkClient vistaLinkClient, SaveToVistaResponse response) {
        try {
            Survey btbisSurvey = isTBIConsultSelected(veteranAssessment);
            if (btbisSurvey != null) {
                Map<String, Object> vistaResponse = vistaLinkClient.saveTBIConsultOrders(veteranAssessment, quickOrderIen, surveyResponsesHelper.prepareSurveyResponsesMap(btbisSurvey.getName(), veteranAssessment.getSurveyMeasureResponseList(), true));
                logger.debug("TBI Consult Response {}", vistaResponse);
                response.addSuccess(SaveToVistaResponse.PendingOperation.tbi, msg(SaveToVistaResponse.MsgKey.usr_pass_tbi__saved_success));
            }
        } catch (Exception e) {
            response.addSysErr(SaveToVistaResponse.PendingOperation.tbi, Throwables.getRootCause(e).getMessage());
        }
        response.requestDone(SaveToVistaResponse.PendingOperation.tbi);
    }

    private Survey isTBIConsultSelected(VeteranAssessment veteranAssessment) {
        for (SurveyMeasureResponse smr : veteranAssessment.getSurveyMeasureResponseList()) {
            if ("BTBIS".equals(smr.getSurvey().getName())) {
                MeasureAnswer ma = smr.getMeasureAnswer();
                if ("1".equals(ma.getCalculationValue()) && smr.getBooleanValue()) {
                    return smr.getSurvey();
                }
            }
        }
        return null;
    }


    private String msg(SaveToVistaResponse.MsgKey msgKey) {
        return messageSource.getMessage(msgKey.name(), null, null);
    }

    private void saveMentalHealthFactors(Long locationIEN, String visitString,
                                         String visitDate, Boolean inpatientStatus, Long progressNoteIEN,
                                         VeteranAssessment veteranAssessment, VistaLinkClient vistaLinkClient, SaveToVistaResponse response) throws VistaLinkClientException {

        HealthFactorProvider healthFactorProvider = createHealthFactorProvider(veteranAssessment);

        HealthFactorHeader healthFactorHeader = createHealthFactorHeader(inpatientStatus, visitString);

        HealthFactorLists healthFactorSet = createHealthFactorList(veteranAssessment, gov.va.escreening.vista.dto.HealthFactor.ActionSymbols.Plus);

        Set<HealthFactorVisitData> healthFactorVisitDataSet = null;

        // Use VistaClient to save to VistA
        if (healthFactorSet != null) {
            if (!healthFactorSet.getCurrentHealthFactors().isEmpty()) {
                healthFactorVisitDataSet = createHealthFactorVisitDataSet(veteranAssessment, VistaServiceCategoryEnum.AMBULATORY, false, visitDate);
                saveVeteranHealthFactorsToVista(vistaLinkClient, progressNoteIEN, locationIEN, false, healthFactorSet.getCurrentHealthFactors(), healthFactorHeader, healthFactorProvider, healthFactorVisitDataSet, response);
            }

            if (!healthFactorSet.getHistoricalHealthFactors().isEmpty()) {
                // TODO: Need to get visit date from the historical health
                // factor prompts.
                healthFactorVisitDataSet = createHealthFactorVisitDataSet(veteranAssessment, VistaServiceCategoryEnum.AMBULATORY, true, visitDate);
                saveVeteranHealthFactorsToVista(vistaLinkClient, progressNoteIEN, locationIEN, true, healthFactorSet.getHistoricalHealthFactors(), healthFactorHeader, healthFactorProvider, healthFactorVisitDataSet, response);
            }
            response.requestDone(SaveToVistaResponse.PendingOperation.hf);
        }

    }

    private VistaProgressNote saveProgressNote(Long patientIEN,
                                               Long locationIEN, String visitString,
                                               VeteranAssessment veteranAssessment, VistaLinkClient vistaLinkClient, SaveToVistaResponse response) {
        String progressNoteContent = null;
        try {
            progressNoteContent = templateProcessorService.generateCPRSNote(veteranAssessment.getVeteranAssessmentId(), ViewType.TEXT, EnumSet.of(TemplateType.VISTA_QA));
            response.addSuccess(SaveToVistaResponse.PendingOperation.cprs, msg(SaveToVistaResponse.MsgKey.usr_pass_cprs__gen_success));
            if (true) {
                throw new IllegalStateException("TEST EXCEPTION for JSP to handle the callResults logic");
            }

        } catch (Exception e) {
            response.addSysErr(SaveToVistaResponse.PendingOperation.cprs, Throwables.getRootCause(e).getMessage());
            return null;
        }

        VistaProgressNote vistaProgressNote = null;
        try {
            Boolean appendContent = true;
            Long visitIEN = null;
            Date visitDateTime = veteranAssessment.getDateCompleted();
            Long titleIEN = Long.parseLong(veteranAssessment.getNoteTitle().getVistaIen());
            Date visitDate = (veteranAssessment.getDateCompleted() != null) ? veteranAssessment.getDateCompleted() : new Date();
            Object[] identifiers = {Long.parseLong(veteranAssessment.getClinician().getVistaDuz().trim()), visitDate, locationIEN, null};
            ProgressNoteParameters progressNoteParameters = new ProgressNoteParameters(patientIEN, titleIEN, locationIEN, visitIEN, visitDateTime, visitString, identifiers, progressNoteContent, appendContent);
            vistaProgressNote = vistaLinkClient.saveProgressNote(progressNoteParameters);
            response.addSuccess(SaveToVistaResponse.PendingOperation.cprs, msg(SaveToVistaResponse.MsgKey.usr_pass_cprs__saved_success));
        } catch (Exception e) {
            response.addSysErr(SaveToVistaResponse.PendingOperation.cprs, Throwables.getRootCause(e).getMessage());
        }

        response.requestDone(SaveToVistaResponse.PendingOperation.cprs);

        return vistaProgressNote;
    }

    private MentalHealthAssessmentResult saveMentalHealthAssessments(
            Long patientIEN, VeteranAssessment veteranAssessment,
            VistaLinkClient vistaLinkClient, SaveToVistaResponse response) throws VistaLinkClientException {

        List<MentalHealthAssessment> mentalHealthAssessments = veteranAssessmentService.getMentalHealthAssessments(veteranAssessment.getVeteranAssessmentId());
        MentalHealthAssessmentResult mhaResults = null;

        if (!checkMHA(mentalHealthAssessments, response)) {
            return mhaResults;
        }

        for (MentalHealthAssessment mentalHealthAssessment : mentalHealthAssessments) {
            mhaResults = sendMhaToVista(veteranAssessment, patientIEN, mentalHealthAssessment, vistaLinkClient, response);
            if (mhaResults != null) {
                saveMhaToDb(mentalHealthAssessment, mhaResults, veteranAssessment, response);
            }
        }

        if (response.isDone(SaveToVistaResponse.PendingOperation.sendMhaToVista) && response.isDone(SaveToVistaResponse.PendingOperation.saveMhaToDb)) {
            response.requestDone(SaveToVistaResponse.PendingOperation.mha);
        }

        return mhaResults;
    }

    private boolean checkMHA(List<MentalHealthAssessment> mentalHealthAssessments, SaveToVistaResponse response) {
        if (mentalHealthAssessments.isEmpty()) {
            response.requestDone(SaveToVistaResponse.PendingOperation.sendMhaToVista);
            response.requestDone(SaveToVistaResponse.PendingOperation.saveMhaToDb);
            response.requestDone(SaveToVistaResponse.PendingOperation.mha);
            return false;
        }

        return true;
    }

    private void saveMhaToDb(MentalHealthAssessment mentalHealthAssessment,
                             MentalHealthAssessmentResult mhaResults,
                             VeteranAssessment veteranAssessment, SaveToVistaResponse response) {
        // save mental health assessment score to database here.
        Integer veteranAssessmentId = veteranAssessment.getVeteranAssessmentId();
        Long mhaSurveyId = mentalHealthAssessment.getSurveyId();
        String mhaDesc = mhaResults.getMentalHealthAssessmentResultDescription();
        try {
            veteranAssessmentService.saveMentalHealthTestResult(veteranAssessmentId, mhaSurveyId.intValue(), mhaDesc);
            response.addSuccess(SaveToVistaResponse.PendingOperation.saveMhaToDb, msg(SaveToVistaResponse.MsgKey.usr_pass_mha__mhtr_success));
        } catch (Exception e) {
            response.addSysErr(SaveToVistaResponse.PendingOperation.saveMhaToDb, Throwables.getRootCause(e).getMessage());
        }
        response.requestDone(SaveToVistaResponse.PendingOperation.saveMhaToDb);
    }

    private MentalHealthAssessmentResult sendMhaToVista(
            VeteranAssessment veteranAssessment, Long patientIEN,
            MentalHealthAssessment mentalHealthAssessment,
            VistaLinkClient vistaLinkClient, SaveToVistaResponse response) {
        String mhaTestName = mentalHealthAssessment.getMentalHealthTestName();
        String mhaTestAnswers = mentalHealthAssessment.getMentalHealthTestAnswers();
        Long mhaReminderDialogIEN = mentalHealthAssessment.getReminderDialogIEN();

        // if mhaTestAnswers is empty or null then return null from here
        if (Strings.isNullOrEmpty(mhaTestAnswers)) {
            String warmMsg = String.format("Mental health Assessment will not be sent to Vista. " +
                            "Reason: 'MHA test answers' is left blank for 'Veteran assessment id' [%s], " +
                            "'Patient ien' [%s], 'MHA test name' [%s], 'MHA reminder dialog ien' [%s]",
                    veteranAssessment.getVeteranAssessmentId(), patientIEN, mhaTestName, mhaReminderDialogIEN);
            logger.warn(warmMsg);

            response.addWarnMsg(SaveToVistaResponse.PendingOperation.sendMhaToVista, warmMsg);
            return null;
        }

        // save mental health assessment test answers to VistA.

        boolean savePassed = false;

        try {
            savePassed = vistaLinkClient.saveMentalHealthAssessment(patientIEN, mhaTestName, mhaTestAnswers);
            if (savePassed) {
                response.addSuccess(SaveToVistaResponse.PendingOperation.sendMhaToVista, msg(SaveToVistaResponse.MsgKey.usr_pass_mha__success));
            } else {
                response.addFailedMsg(SaveToVistaResponse.PendingOperation.sendMhaToVista, msg(SaveToVistaResponse.MsgKey.usr_err_mha__failed));
                return null;
            }
        } catch (Exception e) {
            response.addSysErr(SaveToVistaResponse.PendingOperation.sendMhaToVista, Throwables.getRootCause(e).getMessage());
            return null;
        }

        MentalHealthAssessmentResult assessmentResults = null;
        String staffCode = veteranAssessment.getClinician().getVistaDuz();
        try {
            vistaLinkClient.saveMentalHealthPackage(patientIEN, mhaTestName, new Date(), staffCode, mhaTestAnswers);
            response.addSuccess(SaveToVistaResponse.PendingOperation.sendMhaToVista, msg(SaveToVistaResponse.MsgKey.usr_pass_mha__mhp_success));
            try {
                String dateCode = "T";
                assessmentResults = vistaLinkClient.getMentalHealthAssessmentResults(mhaReminderDialogIEN, patientIEN, mhaTestName, dateCode, staffCode, mhaTestAnswers);
                response.addSuccess(SaveToVistaResponse.PendingOperation.sendMhaToVista, msg(SaveToVistaResponse.MsgKey.usr_pass_mha__mhar_success));
            } catch (Exception e) {
                response.addSysErr(SaveToVistaResponse.PendingOperation.sendMhaToVista, Throwables.getRootCause(e).getMessage());
            }
        } catch (Exception e) {
            response.addSysErr(SaveToVistaResponse.PendingOperation.sendMhaToVista, Throwables.getRootCause(e).getMessage());
        }

        response.requestDone(SaveToVistaResponse.PendingOperation.sendMhaToVista);
        return assessmentResults;
    }

    private void saveVeteranHealthFactorsToVista(
            VistaLinkClient vistaLinkClient, Long noteIEN, Long locationIEN,
            boolean historicalHealthFactor,
            Set<gov.va.escreening.vista.dto.HealthFactor> healthFactors,
            HealthFactorHeader healthFactorHeader,
            HealthFactorProvider healthFactorProvider,
            Set<HealthFactorVisitData> healthFactorVisitDataList, SaveToVistaResponse response) throws VistaLinkClientException {

        try {
            vistaLinkClient.saveHealthFactor(noteIEN, locationIEN, historicalHealthFactor, healthFactorHeader, healthFactorVisitDataList, healthFactorProvider, healthFactors);
            response.addSuccess(SaveToVistaResponse.PendingOperation.hf, msg(SaveToVistaResponse.MsgKey.usr_pass_hf__saved_success));
        } catch (Exception e) {
            response.addSysErr(SaveToVistaResponse.PendingOperation.hf, Throwables.getRootCause(e).getMessage());
        }
    }

    // TODO: Re-factor to include Immunizations and Health Factors in one
    // collection as they both use the same base sequence number.
    private HealthFactorLists createHealthFactorList(
            VeteranAssessment veteranAssessment,
            gov.va.escreening.vista.dto.HealthFactor.ActionSymbols actionSymbol) {

        Set<gov.va.escreening.vista.dto.HealthFactor> historicalHealthFactors = new LinkedHashSet<gov.va.escreening.vista.dto.HealthFactor>();

        Set<gov.va.escreening.vista.dto.HealthFactor> currentHealthFactors = new LinkedHashSet<gov.va.escreening.vista.dto.HealthFactor>();

        gov.va.escreening.vista.dto.HealthFactor someHealthFactor = null;

        if (veteranAssessment != null && veteranAssessment.getHealthFactors() != null) {
            int sequenceNumber = 1;

            for (HealthFactor existingHealthFactor : veteranAssessment.getHealthFactors()) {
                someHealthFactor = createHealthFactor(existingHealthFactor, actionSymbol, sequenceNumber++);
                if (someHealthFactor.isHistoricalHealthFactor()) {
                    historicalHealthFactors.add(someHealthFactor);
                } else {
                    currentHealthFactors.add(someHealthFactor);
                }
            }
        }
        return new HealthFactorLists(currentHealthFactors, historicalHealthFactors);
    }

    private gov.va.escreening.vista.dto.HealthFactor createHealthFactor(
            HealthFactor healthFactor,
            gov.va.escreening.vista.dto.HealthFactor.ActionSymbols actionSymbol,
            int sequenceNumber) {
        String ien = healthFactor.getVistaIen();
        String name = healthFactor.getName();
        boolean historicalHealthFactor = healthFactor.getIsHistorical(); // Indicates
        // if
        // we
        // need
        // to
        // use
        // OL
        // or
        // not.
        String healthFactorCommentText = null; // TODO: We will need to get the
        // comment text from the veteran
        // assessment.

        return new gov.va.escreening.vista.dto.HealthFactor(actionSymbol, ien, name, historicalHealthFactor, sequenceNumber, healthFactorCommentText);
    }

    private HealthFactorProvider createHealthFactorProvider(
            VeteranAssessment veteranAssessment) {
        String ien = veteranAssessment.getClinician().getVistaDuz();
        String name = veteranAssessment.getClinician().getLastName() + "," + veteranAssessment.getClinician().getFirstName();
        Boolean primaryPhysician = false; // TODO: Need to determine if the
        // escreen user is the primary
        // physician.
        return new HealthFactorProvider(ien, name, primaryPhysician);
    }

    private Set<HealthFactorVisitData> createHealthFactorVisitDataSet(
            VeteranAssessment veteranAssessment,
            VistaServiceCategoryEnum encounterServiceCategory,
            boolean historicalHealthFactor, String visitDate) {
        Set<HealthFactorVisitData> healthFactorVisitDataSet = new LinkedHashSet<HealthFactorVisitData>();
        // Add DT Encounter Date
        healthFactorVisitDataSet.add(new VisitInfo_DT(visitDate));

        // Add PT Patient
        healthFactorVisitDataSet.add(new VisitInfo_PT(veteranAssessment.getVeteran().getVeteranIen()));

        // Add VC Encounter Service CategoryString
        healthFactorVisitDataSet.add(new VisitInfo_VC(encounterServiceCategory));

        if (historicalHealthFactor) {
            // Add PR Parent Visit IEN or Progress Note IEN, if not defined use
            // zero for IEN.
            healthFactorVisitDataSet.add(new VisitInfo_PR("0"));

            // Add OL Outside (Historical) Location
            // TODO: Need to determine if the Veteran used a outside location.
            // If so, set the outside location to addtionalData.
            String additionalData = "";
            healthFactorVisitDataSet.add(new VisitInfo_OL("0", additionalData));

        } else {
            // Add HL Encounter Location
            healthFactorVisitDataSet.add(new VisitInfo_HL(veteranAssessment.getClinic().getVistaIen()));
        }

        return healthFactorVisitDataSet;
    }

    private HealthFactorHeader createHealthFactorHeader(
            Boolean inpatientStatus, String visitString) {
        return new HealthFactorHeader(inpatientStatus, visitString);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
