package gov.va.escreening.vista;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.constants.TemplateConstants;
import gov.va.escreening.delegate.VistaDelegate;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.domain.MentalHealthAssessment;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentAuditLog;
import gov.va.escreening.entity.VeteranAssessmentAuditLogHelper;
import gov.va.escreening.repository.VeteranAssessmentAuditLogRepository;
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

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Service("vistaDelegate")
public class VistaDelegateImpl implements VistaDelegate, MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(VistaDelegateImpl.class);
	private MessageSource messageSource;

	private VeteranAssessmentService veteranAssessmentService;

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
	public void setVeteranAssessmentService(
			VeteranAssessmentService veteranAssessmentService) {
		this.veteranAssessmentService = veteranAssessmentService;
	}

	@Transactional(readOnly = false)
	@Override
	public void saveVeteranAssessmentToVista(int veteranAssessmentId,
			EscreenUser escreenUser) throws VistaLinkClientException {

		if (!escreenUser.getCprsVerified()) {
			throw new IllegalStateException(msg("vista.failed.cprs.verification"));
		}

		// 1. Get Veteran's assessment.
		VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);

		// 2. Make sure Veteran has been mapped to a VistA record. Else, this
		// will not work.
		if (StringUtils.isEmpty(veteranAssessment.getVeteran().getVeteranIen())) {
			throw new IllegalStateException(msg("vista.failed.mapping"));
		}

		VistaLinkClientStrategy vistaLinkClientStrategy = null;
		VistaLinkClient vistaLinkClient = null;

		Exception exception = null;
		try {
			vistaLinkClientStrategy = rpcConnectionProvider.createVistaLinkClientStrategy(escreenUser, "", "OR CPRS GUI CHART");
			vistaLinkClient = vistaLinkClientStrategy.getClient();

			Long patientIEN = Long.parseLong(veteranAssessment.getVeteran().getVeteranIen());
			Long locationIEN = Long.parseLong(veteranAssessment.getClinic().getVistaIen());
			Boolean inpatientStatus = vistaLinkClient.findPatientDemographics(patientIEN).getInpatientStatus();
			VistaServiceCategoryEnum encounterServiceCategory = vistaLinkClient.findServiceCategory(VistaServiceCategoryEnum.AMBULATORY, locationIEN, inpatientStatus);
			String visitDate = VistaUtils.convertToVistaDateString((veteranAssessment.getDateCompleted() != null) ? veteranAssessment.getDateCompleted() : new Date(), VistaDateFormat.MMddHHmmss);
			String visitString = locationIEN + ";" + visitDate + ";" + ((encounterServiceCategory != null) ? encounterServiceCategory.getCode() : VistaServiceCategoryEnum.AMBULATORY.getCode());

			// Get Mental Health Assessments
			saveMentalHealthAssessments(patientIEN, veteranAssessment, vistaLinkClient);

			// Generate CPRS Note based on the responses to the survey
			// questions.
			VistaProgressNote progressNote = saveProgressNote(patientIEN, locationIEN, visitString, veteranAssessment, vistaLinkClient);

			// Do Health Factors
			saveMentalHealthFactors(locationIEN, visitString, visitDate, inpatientStatus, progressNote.getIEN(), veteranAssessment, vistaLinkClient);

			// save TBI Consult request
			saveTbiConsultRequest(veteranAssessment, vistaLinkClient);

			// save this activity in audit log
			VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_VISTA_SAVE, veteranAssessment.getAssessmentStatus().getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
			veteranAssessmentAuditLogRepository.update(auditLogEntry);
		} catch (VistaLinkClientException vlce) {
			exception = vlce;
		} catch (Exception e) {
			exception = e;
		} finally {
			if (vistaLinkClient != null) {
				vistaLinkClient.closeConnection();
			}
			if (exception != null) {
				logger.error(exception.getMessage(), exception);
				throw new IllegalStateException(exception);
			} else {
				// mark this assessment as finalized as the assessment is saved successfully in vista
				assessmentEngineService.transitionAssessmentStatusTo(veteranAssessmentId, AssessmentStatusEnum.FINALIZED);
			}
		}
	}

	private void saveTbiConsultRequest(VeteranAssessment veteranAssessment,
			VistaLinkClient vistaLinkClient) {
		Survey btbisSurvey = surveyResponsesHelper.isTBIConsultSelected(veteranAssessment);
		if (btbisSurvey != null) {
			logger.debug("saving TBI Consult Request to Vista");

			Map<String, Object> vistaResponse = vistaLinkClient.saveTBIConsultOrders(veteranAssessment, surveyResponsesHelper.prepareSurveyResponsesMap(btbisSurvey.getName(), veteranAssessment.getSurveyMeasureResponseList(), ExportTypeEnum.DEIDENTIFIED.getExportTypeId()));
			logger.debug("TBI Consult Response {}", vistaResponse);
		}
	}

	private String msg(String msgKey) {
		return messageSource.getMessage(msgKey, null, null);
	}

	private void saveMentalHealthFactors(Long locationIEN, String visitString,
			String visitDate, Boolean inpatientStatus, Long progressNoteIEN,
			VeteranAssessment veteranAssessment, VistaLinkClient vistaLinkClient) throws VistaLinkClientException {

		HealthFactorProvider healthFactorProvider = createHealthFactorProvider(veteranAssessment);

		HealthFactorHeader healthFactorHeader = createHealthFactorHeader(inpatientStatus, visitString);

		HealthFactorLists healthFactorSet = createHealthFactorList(veteranAssessment, gov.va.escreening.vista.dto.HealthFactor.ActionSymbols.Plus);

		Set<HealthFactorVisitData> healthFactorVisitDataSet = null;

		// Use VistaClient to save to VistA
		if (healthFactorSet != null) {
			if (!healthFactorSet.getCurrentHealthFactors().isEmpty()) {
				healthFactorVisitDataSet = createHealthFactorVisitDataSet(veteranAssessment, VistaServiceCategoryEnum.AMBULATORY, false, visitDate);
				saveVeteranHealthFactorsToVista(vistaLinkClient, progressNoteIEN, locationIEN, false, healthFactorSet.getCurrentHealthFactors(), healthFactorHeader, healthFactorProvider, healthFactorVisitDataSet);
			}

			if (!healthFactorSet.getHistoricalHealthFactors().isEmpty()) {
				// TODO: Need to get visit date from the historical health
				// factor prompts.
				healthFactorVisitDataSet = createHealthFactorVisitDataSet(veteranAssessment, VistaServiceCategoryEnum.AMBULATORY, true, visitDate);
				saveVeteranHealthFactorsToVista(vistaLinkClient, progressNoteIEN, locationIEN, true, healthFactorSet.getHistoricalHealthFactors(), healthFactorHeader, healthFactorProvider, healthFactorVisitDataSet);
			}
		}
	}

	private VistaProgressNote saveProgressNote(Long patientIEN,
			Long locationIEN, String visitString,
			VeteranAssessment veteranAssessment, VistaLinkClient vistaLinkClient) throws Exception {

		Set<TemplateConstants.Style> templateStyles 
							= Sets.newHashSet(TemplateConstants.Style.CPRS_NOTE_HEADER, TemplateConstants.Style.CPRS_NOTE_FOOTER);
		Long titleIEN = Long.parseLong(veteranAssessment.getNoteTitle().getVistaIen());
		Date visitDate = (veteranAssessment.getDateCompleted() != null) ? veteranAssessment.getDateCompleted() : new Date();
		Object[] identifiers = { Long.parseLong(veteranAssessment.getClinician().getVistaDuz().trim()), visitDate, locationIEN, null };
		String progressNoteContent = templateProcessorService.generateNote(veteranAssessment.getVeteranAssessmentId(), TemplateConstants.ViewType.TEXT, templateStyles, true);
		Boolean appendContent = true;
		Long visitIEN = null;
		Date visitDateTime = veteranAssessment.getDateCompleted();
		ProgressNoteParameters progressNoteParameters = new ProgressNoteParameters(patientIEN, titleIEN, locationIEN, visitIEN, visitDateTime, visitString, identifiers, progressNoteContent, appendContent);
		return vistaLinkClient.saveProgressNote(progressNoteParameters);
	}

	private MentalHealthAssessmentResult saveMentalHealthAssessments(
			Long patientIEN, VeteranAssessment veteranAssessment,
			VistaLinkClient vistaLinkClient) throws VistaLinkClientException {

		List<MentalHealthAssessment> mentalHealthAssessments = veteranAssessmentService.getMentalHealthAssessments(veteranAssessment.getVeteranAssessmentId());

		MentalHealthAssessmentResult mhaResults = null;

		for (MentalHealthAssessment mentalHealthAssessment : mentalHealthAssessments) {
			mhaResults = saveMhsToVista(veteranAssessment, patientIEN, mentalHealthAssessment, vistaLinkClient);
			if (mhaResults != null) {
				saveMhaToDb(mentalHealthAssessment, mhaResults, veteranAssessment);
			}
		}

		return mhaResults;
	}

	private void saveMhaToDb(MentalHealthAssessment mentalHealthAssessment,
			MentalHealthAssessmentResult mhaResults,
			VeteranAssessment veteranAssessment) {
		// save mental health assessment score to database here.
		Integer veteranAssessmentId = veteranAssessment.getVeteranAssessmentId();
		Long mhaSurveyId = mentalHealthAssessment.getSurveyId();
		String mhaDesc = mhaResults.getMentalHealthAssessmentResultDescription();
		veteranAssessmentService.saveMentalHealthTestResult(veteranAssessmentId, mhaSurveyId.intValue(), mhaDesc);
	}

	private MentalHealthAssessmentResult saveMhsToVista(
			VeteranAssessment veteranAssessment, Long patientIEN,
			MentalHealthAssessment mentalHealthAssessment,
			VistaLinkClient vistaLinkClient) {
		String mhaTestName = mentalHealthAssessment.getMentalHealthTestName();
		String mhaTestAnswers = mentalHealthAssessment.getMentalHealthTestAnswers();
		Long mhaReminderDialogIEN = mentalHealthAssessment.getReminderDialogIEN();

		// save mental health assessment test answers to VistA.
		boolean savePassed = vistaLinkClient.saveMentalHealthAssessment(patientIEN, mhaTestName, mhaTestAnswers);

		String staffCode = veteranAssessment.getClinician().getVistaDuz();
		boolean saveHealthPackagePassed = vistaLinkClient.saveMentalHealthPackage(patientIEN, mhaTestName, new Date(), staffCode, mhaTestAnswers);

		if (savePassed) {
			String dateCode = "T";
			return vistaLinkClient.getMentalHealthAssessmentResults(mhaReminderDialogIEN, patientIEN, mhaTestName, dateCode, staffCode, mhaTestAnswers);
		} else {
			return null;
		}
	}

	private void saveVeteranHealthFactorsToVista(
			VistaLinkClient vistaLinkClient, Long noteIEN, Long locationIEN,
			boolean historicalHealthFactor,
			Set<gov.va.escreening.vista.dto.HealthFactor> healthFactors,
			HealthFactorHeader healthFactorHeader,
			HealthFactorProvider healthFactorProvider,
			Set<HealthFactorVisitData> healthFactorVisitDataList) throws VistaLinkClientException {

		vistaLinkClient.saveHealthFactor(noteIEN, locationIEN, historicalHealthFactor, healthFactorHeader, healthFactorVisitDataList, healthFactorProvider, healthFactors);
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
