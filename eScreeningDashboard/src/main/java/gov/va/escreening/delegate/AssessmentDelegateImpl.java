package gov.va.escreening.delegate;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.context.AssessmentContext;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.domain.VeteranDtoHelper;
import gov.va.escreening.dto.ae.*;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.entity.*;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.InvalidAssessmentContextException;
import gov.va.escreening.repository.*;
import gov.va.escreening.service.*;
import gov.va.escreening.templateprocessor.TemplateProcessorService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static gov.va.escreening.constants.AssessmentConstants.*;

@Transactional
public class AssessmentDelegateImpl implements AssessmentDelegate {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentDelegate.class);

	@Autowired
	private AssessmentContext assessmentContext;
	@Autowired
	private VeteranAssessmentService veteranAssessmentService;
	@Autowired
	private VeteranAssessmentSurveyService veteranAssessmentSurveyService;
	@Autowired
	private VeteranService veteranService;
	@Autowired
	private AssessmentEngineService assessmentEngineService;
	@Autowired
	private VeteranAssessmentAuditLogRepository veteranAssessmentAuditLogRepository;
	@Autowired
	private VeteranAssessmentRepository veteranAssessmentRepository;
	@Autowired
	private AssessmentStatusRepository assessmentStatusRepository;
	@Autowired
	private SurveyRepository surveyRepository;
	@Autowired
	private SurveySectionRepository surveySectionRepository;
	
	@Autowired
	private SurveyMeasureResponseRepository surveyMeasureResponseRepository;
	
    @Resource(type=SurveyPageRepository.class)
    private SurveyPageRepository surveyPageRepository;

	@Autowired
	private BatteryRepository batteryRepo;

	@Resource(type = TemplateProcessorService.class)
	private TemplateProcessorService templateProcessorService;

	@Resource(type = VeteranAssessmentSurveyScoreService.class)
	private VeteranAssessmentSurveyScoreService vassSrv;

	@Override
	public List<VeteranDto> findVeterans(VeteranDto veteran) {
		logger.debug("findVeterans");
		return veteranService.findVeterans(veteran);
	}

	@Override
	public VeteranAssessment getAvailableVeteranAssessment(Integer veteranId, Integer programId) {

		List<VeteranAssessment> veteranAssessments = veteranAssessmentService.getAvailableAssessmentsForVeteran(veteranId);

		if (veteranAssessments == null || veteranAssessments.isEmpty()) {
			return null;
		} else {
			for (VeteranAssessment assessment : veteranAssessments) {
				if(programId == null || assessment.getProgram().getProgramId().equals(programId))
				{
					if (surveyRepository.findForVeteranAssessmentId(assessment.getVeteranAssessmentId()).size() > 0)
						return assessment;
				}
			}
			return null;
		}
	}

	@Override
	@Transactional
	public void setUpAssessmentContext(VeteranDto veteran,
			VeteranAssessment veteranAssessment) {
		logger.debug("Set up assessment context");

		assessmentContext.setVeteran(veteran);
		assessmentContext.setVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());
		
		List<SurveyPage> fullList = surveyPageRepository.getSurveyPagesForVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());
		assessmentContext.setSurveyPageList(copyAnswersFromPast48HoursAndFilterSurvey(veteranAssessment, fullList));
		assessmentContext.setIsInitialized(true);
	}

    /** This method copies answers from surveys that are answered within the past 48 hours, then remove the module from 
     * current survey page list 
     * @param assessment The current assessment
     * @param fullList The full list of survey pages
     * @return
     */
	List<SurveyPage> copyAnswersFromPast48HoursAndFilterSurvey(VeteranAssessment assessment, List<SurveyPage> fullList)
	{
		Map<Integer, List<SurveyPage>> map = Maps.newHashMap();
		
		Set<String> copiedAnswers = new HashSet<String>();
		
		List<SurveyMeasureResponse> alreadyAnswered = surveyMeasureResponseRepository.findForVeteranAssessmentId(assessment.getVeteranAssessmentId());
		
		
		for(SurveyPage sp : fullList)
		{
			if(!map.containsKey(sp.getSurvey().getSurveyId()))
			{
				map.put(sp.getSurvey().getSurveyId(), Lists.newArrayList(sp));
			}
			else
			{
				map.get(sp.getSurvey().getSurveyId()).add(sp);
			}
		}
		
		/** this loop handles the case when a veteran log back in to finish the assessment,
		 * If some of the modules have already been copied, don't include them in the page list,
		 * also these modules cannot be copied again.
		 */
		for(SurveyMeasureResponse r : alreadyAnswered)
		{
			if(r.getCopiedFromVeteranAssessment() != null)
			{
				copiedAnswers.add(getUniqueKeyForSurveyMeasureResponse(r));
				fullList.removeAll(map.get(r.getSurvey().getSurveyId()));
			}
		}
		
		List<SurveyMeasureResponse> respList = surveyMeasureResponseRepository.findLast48HourAnswersForVet(assessment.getVeteran().getVeteranId());
		
		for(SurveyMeasureResponse resp : respList)
		{
			String key = getUniqueKeyForSurveyMeasureResponse(resp);
			if(map.containsKey(resp.getSurvey().getSurveyId()) && copiedAnswers.contains(key))
			{
				SurveyMeasureResponse copied = new SurveyMeasureResponse();
				try {
					BeanUtils.copyProperties(copied, resp);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				copied.setVeteranAssessment(assessment);
				copied.setCopiedFromVeteranAssessment(resp.getVeteranAssessment());
				copied.setSurveyMeasureResponseId(null);
				fullList.removeAll(map.get(resp.getSurvey().getSurveyId()));
				
				surveyMeasureResponseRepository.create(copied);
				copiedAnswers.add(key);
			}
		}
		
		surveyMeasureResponseRepository.commit();
		return fullList;
	}
	
	private String getUniqueKeyForSurveyMeasureResponse(SurveyMeasureResponse smr)
	{
		return smr.getMeasure().getMeasureId() + ":" + smr.getMeasureAnswer().getMeasureAnswerId()+":"+smr.getTabularRow();
	}
	
	@Override
	public void ensureValidAssessmentContext() throws InvalidAssessmentContextException {
		logger.debug("Ensuring valid assessment context");
		if (!assessmentContext.getIsInitialized() || assessmentContext.getVeteran() == null)
			throw new InvalidAssessmentContextException("Veteran not logged in");
	}

	@Override
	public List<SurveySection> getAssessmentSections() {
		logger.debug("Getting surveys for current assessment");
		ensureValidAssessmentContext();

		return surveySectionRepository.findForVeteranAssessmentId(assessmentContext.getVeteranAssessmentId());
	}

	@Override
	public AssessmentResponse processPage(AssessmentRequest assessmentRequest) {

		if (assessmentRequest != null) {
			logger.debug("Assessment request: {}", assessmentRequest);

			// we set the assessment ID from the context (not from the request)
			assessmentRequest.setAssessmentId(assessmentContext.getVeteranAssessmentId());
		}
		
		if(assessmentContext.getSurveyPageList().isEmpty())
		{
			//this means all of the surveys have been answered before, should transition to complete directly.
			AssessmentResponse resp = new AssessmentResponse();
			resp.setPage(new Page());
		}
		AssessmentResponse response = assessmentEngineService.processPage(assessmentRequest, assessmentContext.getSurveyPageList());

		prepopulateResponseAnswers(response);

		return response;
	}

	@Override
	public Integer getVeteranAssessmentId() {
		return assessmentContext.getVeteranAssessmentId();
	}

	private void prepopulateResponseAnswers(AssessmentResponse response) {
		// Check to see if this is the identification survey, if it is then pre-populate the response answers
		VeteranDto veteran = assessmentContext.getVeteran();
		if (veteran != null && response.getAssessment() != null && response.getAssessment().getCurrentSurveyId() == SURVEY_IDENTIFICATION_ID) {
			for (Measure measure : response.getPage().getMeasures()) {

				String responseText = null;
				if (!measure.getAnswers().isEmpty() && measure.getAnswers().get(0) != null) {
					switch (measure.getMeasureId()) {

					case MEASURE_IDENTIFICATION_FIRST_NAME_ID:
						responseText = veteran.getFirstName();
						break;
					case MEASURE_IDENTIFICATION_MIDDLE_NAME_ID:
						responseText = veteran.getMiddleName();
						break;
					// case MEASURE_IDENTIFICATION_SUFFIX_ID:
					// responseText = veteran.getSuffix();
					// break;
					case MEASURE_IDENTIFICATION_LAST_NAME_ID:
						responseText = veteran.getLastName();
						break;
					case MEASURE_IDENTIFICATION_SSN_LAST_FOUR:
						responseText = veteran.getSsnLastFour();
						break;
					case MEASURE_IDENTIFICATION_EMAIL:
						responseText = veteran.getEmail();
						break;
					case MEASURE_IDENTIFICATION_PHONE_:
						responseText = VeteranDtoHelper.getPhoneByPriority(veteran);
						break;
					case MEASURE_IDENTIFICATION_CALL_TIME:
						if (!Strings.isNullOrEmpty(veteran.getBestTimeToCall())) {
							// pre populate best time to call
							try {
								String prevResponse = veteran.getBestTimeToCall();
								for (Answer answer : measure.getAnswers()) {
									if (prevResponse.equalsIgnoreCase(answer.getAnswerText())) {
										answer.setAnswerResponse("true");
										answer.setOtherAnswerResponse(veteran.getBestTimeToCallOther());
									}
								}
							} catch (NumberFormatException e) {
								logger.error("Error parsing previously saved call time measure answer", e);
							}
						}
						return;
					}

					if (responseText != null) {
						measure.getAnswers().get(0).setAnswerResponse(responseText);
					}
				}

			}
		}
	}

	@Override
	public String getVeteranFullName() {
		if (assessmentContext.getVeteran() == null) {
			return null;
		}

		if (StringUtils.isNotBlank(assessmentContext.getVeteran().getFirstName())) {
			return assessmentContext.getVeteran().getLastName() + ", " + assessmentContext.getVeteran().getFirstName();
		} else {
			return assessmentContext.getVeteran().getLastName();
		}
	}

	@Override
	public String getCompletionMessage() throws IllegalSystemStateException {
		return getBatteryTempalte(TemplateType.ASSESS_CONCLUSION);
	}

	@Override
	public String getWelcomeMessage() throws IllegalSystemStateException {
		return getBatteryTempalte(TemplateType.ASSESS_WELCOME);
	}
	
	private String getBatteryTempalte(TemplateType type) throws InvalidAssessmentContextException, EntityNotFoundException, IllegalSystemStateException{
		Integer assessmentId = assessmentContext.getVeteranAssessmentId();
		if(assessmentId == null){
			throw new InvalidAssessmentContextException("No assessment found in context");
		}
		
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(assessmentId);
		if(veteranAssessment == null){
			ErrorBuilder.throwing(EntityNotFoundException.class)
		        .toUser("Could not find the assessment.")
		        .toAdmin("Could not find the assessment with ID: " + assessmentId)
		        .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
		        .throwIt();
		}
		
		Battery battery = veteranAssessment.getBattery();
		if(battery == null){
			ErrorBuilder.throwing(IllegalSystemStateException.class)
		        .toUser("No battery assigned to this assessment.  Please contact support.")
		        .toAdmin("No battery is associated with assessment with ID: " + assessmentId + ". Please report this incident to development team.")
		        .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue())
		        .throwIt();
		}
		
		try{
			return templateProcessorService.renderBatteryTemplate(battery, type, assessmentId, ViewType.HTML);
		}
		catch(EntityNotFoundException enf){
			//if there is no defined template we will return an empty string
			return "";
		}
	}
	
	@Override
	public void markAssessmentAsComplete() {
		Integer assessmentId = assessmentContext.getVeteranAssessmentId();
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(assessmentId);

		// update the status of the assessment to complete
		AssessmentStatus status = assessmentStatusRepository.findOne(AssessmentStatusEnum.COMPLETE.getAssessmentStatusId());
		veteranAssessment.setAssessmentStatus(status);
        veteranAssessment.setDateCompleted(new Date());
		veteranAssessmentRepository.update(veteranAssessment);

		// TODO: Currently only a Veteran can take the assessment, person type will need to be detected once a
		// user can take an assessment to properly track the person_id
		veteranAssessment = veteranAssessmentRepository.findOne(assessmentId);
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, ASSESSMENT_EVENT_MARKED_COMPLETED, veteranAssessment.getAssessmentStatus().getAssessmentStatusId(), PERSON_TYPE_VETERAN);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);

        // after the assessment is done, we will calculate the score first before returing to UI.
        recordAllReportableScores(veteranAssessment);
	}

    @Override
    public void recordAllReportableScores(VeteranAssessment veteranAssessment) {
        vassSrv.recordAllReportableScores(veteranAssessment);

    }

    @Override
    public Integer getModuleId(Integer pageId) {
        SurveyPage surveyPage = surveyPageRepository.findOne(pageId);
        return surveyPage.getSurvey().getSurveyId();
    }
}
