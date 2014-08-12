package gov.va.escreening.service;

import static com.google.common.base.Preconditions.checkArgument;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.context.AssessmentContext;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.Assessment;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.AssessmentResponse;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.ae.SurveyProgress;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.SurveyPage;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentAuditLog;
import gov.va.escreening.entity.VeteranAssessmentAuditLogHelper;
import gov.va.escreening.entity.VeteranAssessmentSurvey;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.measure.AnswerProcessor;
import gov.va.escreening.measure.AnswerSubmission;
import gov.va.escreening.measure.BooleanAnswerProcessor;
import gov.va.escreening.measure.NumberAnswerProcessor;
import gov.va.escreening.measure.StringAnswerProcessor;
import gov.va.escreening.repository.AssessmentStatusRepository;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.SurveyPageRepository;
import gov.va.escreening.repository.VeteranAssessmentAuditLogRepository;
import gov.va.escreening.repository.VeteranAssessmentMeasureVisibilityRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.repository.VeteranAssessmentSurveyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ListMultimap;

@Transactional
@Service("assessmentEngineService")
public class AssessmentEngineServiceImpl implements AssessmentEngineService {

	@Autowired
	private AssessmentContext assessmentContext;
	@Autowired
	private AssessmentStatusRepository assessmentStatusRepository;
	@Autowired
	private MeasureRepository measureRepository;
	@Autowired
	private SurveyMeasureResponseRepository surveyMeasureResponseRepository;
	@Autowired
	private SurveyPageRepository surveyPageRepository;
	@Autowired
	private SurveyPageService surveyPageService;
	@Autowired
	private VeteranAssessmentAuditLogRepository veteranAssessmentAuditLogRepository;
	@Autowired
	private VeteranAssessmentRepository veteranAssessmentRepository;
	@Autowired
	private VeteranService veteranService;
	@Autowired
	private VeteranAssessmentSurveyRepository veteranAssessmentSurveyRepository;
	@Autowired
	private VeteranAssessmentSurveyService veteranAssessmentSurveyService;
	@Autowired
	private MeasureAnswerRepository measureAnswerRepository;
	@Autowired
	private RuleService ruleProcessorService;
	@Autowired
	private VeteranAssessmentService veteranAssessmentService;
	@Autowired
	private VeteranAssessmentMeasureVisibilityRepository measureVisibilityRepository;

	private static final Logger logger = LoggerFactory.getLogger(AssessmentEngineServiceImpl.class);

	private static final AnswerProcessor answerProcessor;
	static {
		// create chain or processors
		answerProcessor = new StringAnswerProcessor();
		answerProcessor.setNext(new BooleanAnswerProcessor()).setNext(new NumberAnswerProcessor());
	}

	@Override
	public AssessmentResponse processPage(AssessmentRequest assessmentRequest) {

		// First validate and save data.
		List<SurveyMeasureResponse> savedResponses = saveUserInput(assessmentRequest);

		// evaluate rules
		ruleProcessorService.processRules(assessmentContext.getVeteranAssessmentId(), savedResponses);

		// If all went well, get the next set of question for the user, if any.
		return getAssessmentResponse(assessmentRequest);
	}

	@Override
	public Map<Integer, Boolean> getUpdatedVisibility(
			AssessmentRequest assessmentRequest) {
		SurveyPage page = surveyPageRepository.findOne(assessmentRequest.getPageId());
		checkArgument(page != null, "Invalid page ID given");

		// we set the assessment ID from the context (not from the request)
		assessmentRequest.setAssessmentId(assessmentContext.getVeteranAssessmentId());

		try {
			// First validate and save data.
			saveUserInput(assessmentRequest);
		} catch (Throwable t) {
			logger.warn("Error during save of user data for follow-up question visibility update:\n{}", t.getLocalizedMessage());
		}

		// update visibility for the measures found on the page
		ruleProcessorService.updateVisibilityForQuestions(assessmentContext.getVeteranAssessmentId(), page.getMeasures());

		return measureVisibilityRepository.getVisibilityMapForSurveyPage(assessmentRequest.getAssessmentId(), assessmentRequest.getPageId());
	}

	/**
	 * Returns the next set of question.
	 * 
	 * @param veteranAssessmentId
	 * @param surveyPageId
	 * @return
	 */
	private AssessmentResponse getAssessmentResponse(
			AssessmentRequest assessmentRequest) {

		AssessmentResponse assessmentResponse = new AssessmentResponse();
		assessmentResponse.setStatus(HttpStatus.OK.value());

		// Get assessment for next
		Assessment assessment = getAssessmentForNextPage(assessmentRequest);
		assessmentResponse.setAssessment(assessment);

		Integer veteranAssessmentId = assessmentRequest.getAssessmentId();
		// Now that we have saved the submitted data, update the progress and
		// get it.
		veteranAssessmentSurveyService.updateProgress(veteranAssessmentId, assessmentRequest.getStartTime());
		assessmentResponse.setSurveyProgresses(getProgressStatuses(veteranAssessmentId));

		// set the assessment's date last modified
		assessmentResponse.setDateModified(veteranAssessmentRepository.getDateModified(veteranAssessmentId).getTime());

		Page page = getPage(veteranAssessmentId, assessment.getPageId());
		assessmentResponse.setPage(page);

		return assessmentResponse;
	}

	/**
	 * Given a request for assessment, this method will generate an Assessment. <br/>
	 * Valid navigations are:
	 * <ul>
	 * <li>"next" - the next survey page in the assessment unless we are at the
	 * last page in which case isComplete is set to true and the same page is
	 * shown</li>
	 * <li>"previous" - the previous page unless we are at the first page which
	 * will respond with first page</li>
	 * <li>"nextSkipped" - the next page containing at least one skipped
	 * question (required or not) or the "end" page when all questions are
	 * answered</li>
	 * <li>"last" - the last page of the assessment that contains questions</li>
	 * <li>"end" - the "assessment complete" page which is after all survey
	 * pages have been traversed</li>
	 * </ul>
	 * 
	 * @param assessmentRequest
	 * @return the next assessment
	 */
	private Assessment getAssessmentForNextPage(
			AssessmentRequest assessmentRequest) {

		String navigation = Strings.nullToEmpty(assessmentRequest.getNavigation()).toLowerCase();
		Integer veteranAssessmentId = assessmentRequest.getAssessmentId();

		// Get all the survey pages for the assessment.
		List<SurveyPage> surveyPageList = surveyPageRepository.getSurveyPagesForVeteranAssessmentId(veteranAssessmentId);

		Integer currentSurveyPageId;
		if (assessmentRequest.getTargetSection() != null) {
			currentSurveyPageId = surveyPageService.getFirstUnansweredSurveyPage(veteranAssessmentId, assessmentRequest.getTargetSection());
		} else if ("firstskipped".equals(navigation)) {
			currentSurveyPageId = surveyPageService.getFirstUnansweredSurveyPage(veteranAssessmentId, null);
		} else if ("nextskipped".equals(navigation)) {
			Optional<Integer> nextSkippedSurveyPageId = surveyPageService.getNextUnansweredSurveyPage(veteranAssessmentId, assessmentRequest.getPageId());
			if (nextSkippedSurveyPageId.isPresent()) {
				currentSurveyPageId = nextSkippedSurveyPageId.get();
			} else {
				navigation = "end";
				currentSurveyPageId = assessmentRequest.getPageId();
			}
		} else {
			currentSurveyPageId = assessmentRequest.getPageId();
		}

		boolean found = false;

		// Find the current page.
		for (int i = 0; i < surveyPageList.size(); ++i) {
			if (currentSurveyPageId == null || currentSurveyPageId < 1) {
				i = 0;
				found = true;
			} else if (surveyPageList.get(i).getSurveyPageId().intValue() == currentSurveyPageId.intValue()) {
				found = true;
			}

			if (found) {
				// Navigate to the next, previous or stay in current page.
				if ("next".equals(navigation)) {
					++i;
				} else if ("previous".equals(navigation)) {
					--i;
				} else if ("end".equals(navigation)) { // go to end page
					i = surveyPageList.size();
				} else if ("last".equals(navigation)) {
					i = surveyPageList.size() - 1;
				} else {
					// "current" or the targeted survey first page with skipped
					// answers
				}

				// Check out of bounds
				if (i < 0) {
					i = 0;
				}

				boolean isFirstPage = i == 0;
				boolean isLastPage = i >= surveyPageList.size() - 1;
				boolean isComplete = i > surveyPageList.size() - 1;

				if (i > surveyPageList.size() - 1)
					i = surveyPageList.size() - 1;

				return new Assessment(surveyPageList.get(i), isFirstPage, isLastPage, isComplete);
			}
		}

		// Throw exception. This can happen if an invalid veteranAssessmentId or
		// surveyPageId was passed.
		throw new IllegalArgumentException(String.format("VeteranAssessment %s does not have SurveyPageId %s", veteranAssessmentId, currentSurveyPageId));
	}

	/**
	 * Populates the progress status.
	 * 
	 * @param veteranAssessmentId
	 * @return list of progress objects for each survey
	 */
	private List<SurveyProgress> getProgressStatuses(int veteranAssessmentId) {

		List<VeteranAssessmentSurvey> veteranAssessmentSurveyList = veteranAssessmentSurveyRepository.forVeteranAssessmentId(veteranAssessmentId);

		List<SurveyProgress> progressStatuses = new ArrayList<SurveyProgress>(veteranAssessmentSurveyList.size());
		for (VeteranAssessmentSurvey veteranAssessmentSurvey : veteranAssessmentSurveyList) {
			progressStatuses.add(new SurveyProgress(veteranAssessmentSurvey));
		}

		return progressStatuses;
	}

	/**
	 * Gets the survey page, the measures, answer choices, and what the veteran
	 * has already answered.
	 * 
	 * @param surveyPageId
	 * @return
	 */
	private Page getPage(int veteranAssessmentId, int surveyPageId) {

		Page page = new Page();

		// Get all the page and all the questions for this page.
		SurveyPage surveyPage = surveyPageRepository.findOne(surveyPageId);

		// Now get all the answers user entered previously.
		ListMultimap<Integer, SurveyMeasureResponse> responseMap = surveyMeasureResponseRepository.getForVeteranAssessmentAndSurvey(veteranAssessmentId, surveyPage.getSurvey().getSurveyId());

		Map<Integer, Boolean> measureVisibilityMap = measureVisibilityRepository.getVisibilityMapForSurveyPage(veteranAssessmentId, surveyPageId);
		page.setPageTitle(surveyPage.getTitle());

		if (!surveyPage.getMeasures().isEmpty()) {
			page.setMeasures(new ArrayList<Measure>());
			int i = 0;
			List<gov.va.escreening.entity.Measure> measures = surveyPage.getMeasures();
			for (gov.va.escreening.entity.Measure dbMeasure : measures) {

				if (dbMeasure != null) { // it should never been null but just
											// in case I'm checking for it
					Measure measure = new Measure(dbMeasure, responseMap, measureVisibilityMap);
					// dbMeasure is give in display order so we don't need the
					// actual display order
					measure.setDisplayOrder(i++);

					page.getMeasures().add(measure);
				}
			}
		}
		return page;
	}

	/**
	 * Validate and save user input. <b>Note:</b> This saves all responses even
	 * if they are hidden. As a result you must call
	 * ruleProcessorService.processRules or
	 * ruleProcessorService.updateVisibilityForQuestions so that the visibility
	 * rules are run and invisible questions are removed from the database. This
	 * is so the visibility rules can take all answers into account. This
	 * results in the UI question visibility to work correctly when a user
	 * switches from one answer to another and then back again.
	 * 
	 * @param assessmentRequest
	 */
	private List<SurveyMeasureResponse> saveUserInput(
			AssessmentRequest assessmentRequest) {

		ErrorResponse errorResponse = new ErrorResponse();
		List<SurveyMeasureResponse> surveyMeasureResponseList = new ArrayList<SurveyMeasureResponse>();

		// See if we need to do anything.
		if (assessmentRequest.getUserAnswers() == null || assessmentRequest.getUserAnswers().size() < 1) {
			// Nothing to process.
			return surveyMeasureResponseList;
		}

		// Validate veteranAssessment
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(assessmentContext.getVeteranAssessmentId());

		if (veteranAssessment == null) {
			// Invalid veteran assessment id;
			errorResponse.setCode(10);
			errorResponse.setProperty("system");
			errorResponse.reject("page", "0", "Unable to process submitted data.");
			errorResponse.addDeveloperMessage("Veteran assessment could not be found in database: " + assessmentRequest.getAssessmentId());
			throw new AssessmentEngineDataValidationException(errorResponse);
		}

		// Validate surveyPage
		SurveyPage surveyPage = surveyPageRepository.findOne(assessmentRequest.getPageId());
		if (surveyPage == null) {
			// Invalid survey page.
			errorResponse.setCode(10);
			errorResponse.setProperty("system");
			errorResponse.reject("page", "0", "Unable to process submitted data.");
			errorResponse.addDeveloperMessage("Survey page could not be found in database: " + assessmentRequest.getPageId());
			throw new AssessmentEngineDataValidationException(errorResponse);
		}

		// Validate veteranAssessmentSurvey
		Survey survey = surveyPage.getSurvey();

		if (!veteranAssessment.containsSurvey(survey)) {
			// Invalid veteran assessment survey.
			errorResponse.setCode(10);
			errorResponse.setProperty("system");
			errorResponse.reject("page", "0", "Unable to process submitted data.");
			errorResponse.addDeveloperMessage("Veteran not associated with survey page: " + assessmentRequest.getPageId());
			throw new AssessmentEngineDataValidationException(errorResponse);
		}

		// Do the basic NULL validation check here.
		validateMeasureAndAnswersAreNotNull(errorResponse, assessmentRequest);

		// We can't proceed if we have errors here, so throw an exception.
		if (!errorResponse.getErrorMessages().isEmpty()) {
			throw new AssessmentEngineDataValidationException(errorResponse.setCode(10).setProperty("system"));
		}

		//
		// Well, if we got this far, then we can start applying the data
		// validation rule defined in the survey tables.

		Map<Integer, Boolean> visMap = measureVisibilityRepository.getVisibilityMapForSurveyPage(assessmentRequest.getAssessmentId(), assessmentRequest.getPageId());

		AnswerSubmission.Builder submissionBuilder = new AnswerSubmission.Builder(visMap).setErrorResponse(errorResponse);
		Set<gov.va.escreening.entity.Measure> tableMeasures = new HashSet<gov.va.escreening.entity.Measure>();
		for (Measure measure : assessmentRequest.getUserAnswers()) {

			// Get the data validation for this measure.
			gov.va.escreening.entity.Measure dbMeasure = measureRepository.findOne(measure.getMeasureId());

			if (dbMeasure == null) {
				logger.error("Invalid measure posted by client " + measure.getMeasureId());
				errorResponse.reject("page", "0", "Submitted data could not be processed");
				errorResponse.addDeveloperMessage("Invalid measure posted by client " + measure.getMeasureId());
				continue;
			}

			if (dbMeasure.getMeasureType().getMeasureTypeId() != null && dbMeasure.getMeasureType().getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_TABLE) {
				tableMeasures.add(dbMeasure);
			}

			// set the new measure
			submissionBuilder.setMeasure(measure, dbMeasure);

			if (measure.getAnswers() != null) {
				// Now, for each answer, we need to validate and then save add
				// response to surveyMeasureResponseList.
				prepareSurveyResponseList(surveyMeasureResponseList, errorResponse, assessmentRequest, measure, submissionBuilder, veteranAssessment, surveyPage, dbMeasure);
			}
		}

		if (!errorResponse.getErrorMessages().isEmpty()) {
			errorResponse.setCode(11);
			errorResponse.setProperty("dataValidation");

			throw new AssessmentEngineDataValidationException(errorResponse);
		}

		// remove table question child answers before we save
		deleteTableChildQuestionsFromDB(tableMeasures, veteranAssessment, surveyPage, surveyMeasureResponseList);

		// Save to database.
		try {
			for (SurveyMeasureResponse response : surveyMeasureResponseList) {

				if (response.getSurveyMeasureResponseId() == null) {
					surveyMeasureResponseRepository.create(response);
				} else {
					surveyMeasureResponseRepository.update(response);
				}
			}

			// TODO: Currently only a Veteran can take the assessment, person
			// type will need to be detected once a
			// user can take an assessment to properly track the person_id
			VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_UPDATED, veteranAssessment.getAssessmentStatus().getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_VETERAN);
			veteranAssessmentAuditLogRepository.update(auditLogEntry);
		} catch (Exception ex) {
			errorResponse.setCode(10);
			errorResponse.setProperty("system");
			errorResponse.reject("page", "0", "Unable to process submitted data");
			errorResponse.addDeveloperMessage("An error occured saving to the database.");
			logger.error("Error when saving to database.", ex);
			throw new AssessmentEngineDataValidationException(errorResponse);
		}

		// Save identity section
		if (assessmentRequest.getPageId().intValue() == AssessmentConstants.SURVEY_IDENTIFICATION_ID) {
			persistToNonAssessmentStructure(assessmentRequest, surveyPage);
		}

		return surveyMeasureResponseList;
	}

	@Override
	public boolean transitionAssessmentStatusTo(Integer veteranAssessmentId,
			AssessmentStatusEnum requestedState) {
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		if (isValidTransition(veteranAssessment.getAssessmentStatus(), requestedState)) {
			transitionAssessmentTo(veteranAssessment, requestedState);
			return true;
		} else {
			return false;
		}
	}

	private void transitionAssessmentTo(VeteranAssessment veteranAssessment,
			AssessmentStatusEnum requestedState) {

		AssessmentStatus status = assessmentStatusRepository.findOne(requestedState.getAssessmentStatusId());

		veteranAssessment.setAssessmentStatus(status);
		veteranAssessmentRepository.update(veteranAssessment);

		// Also initialize question visibility
		veteranAssessmentService.initializeVisibilityFor(veteranAssessment);

	}

	private boolean isValidTransition(AssessmentStatus fromStatus,
			AssessmentStatusEnum requestedState) {

		boolean isValid = true;
		if (fromStatus.getAssessmentStatusId() == AssessmentStatusEnum.CLEAN.getAssessmentStatusId()) {
			isValid = requestedState == AssessmentStatusEnum.INCOMPLETE;
		}

		if (!isValid) {
			return isValid;
		} else {
			return true;
		}
	}

	// Delete existing table type questions to account for items removed from
	// the list.
	private void deleteTableChildQuestionsFromDB(
			Collection<gov.va.escreening.entity.Measure> tableMeasures,
			VeteranAssessment veteranAssessment, SurveyPage surveyPage,
			List<SurveyMeasureResponse> surveyMeasureResponseList) {

		// TODO: There is probably a better way to do this so update as
		// necessary
		// Collect all of the table child responses that exist and will be
		// updated (they don't need to be deleted)
		// (Note: this isn't just an optimization, removing this logic causes a
		// bug where previously saved responses that are deleted will not be
		// re-inserted)
		StringBuilder savedResponses = new StringBuilder();
		for (SurveyMeasureResponse response : surveyMeasureResponseList) {
			boolean hasTableParent = response.getMeasure() != null && response.getMeasure().getParent() != null && response.getMeasure().getParent().getMeasureType().getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_TABLE;
			if (hasTableParent && response.getSurveyMeasureResponseId() != null) {
				savedResponses.append(response.getSurveyMeasureResponseId()).append(',');
			}
		}
		if (savedResponses.length() > 0) {
			savedResponses = savedResponses.deleteCharAt(savedResponses.length() - 1);
		}

		String responsesToLeave = savedResponses.toString();

		// For each table measure remove each response of its child measures
		for (gov.va.escreening.entity.Measure measure : tableMeasures) {
			for (gov.va.escreening.entity.Measure childMeasure : measure.getChildren()) {

				surveyMeasureResponseRepository.deleteResponseForMeasureAnswerId(veteranAssessment.getVeteranAssessmentId(), surveyPage.getSurvey().getSurveyId(), childMeasure, responsesToLeave);
			}
		}
	}

	// TODO: this should be simplified by possibly having the builder hold more
	// of the passed in objects
	private void prepareSurveyResponseList(
			List<SurveyMeasureResponse> surveyMeasureResponseList,
			ErrorResponse errorResponse, AssessmentRequest assessmentRequest,
			Measure measure, AnswerSubmission.Builder submissionBuilder,
			VeteranAssessment veteranAssessment, SurveyPage surveyPage,
			gov.va.escreening.entity.Measure dbMeasure) {

		// Now, for each answer, we need to validate and then save.
		for (Answer answer : measure.getAnswers()) {
			final Integer answerId = answer.getAnswerId();
			final String userResponse = answer.getAnswerResponse();

			submissionBuilder.setUserAnswer(answer);

			if (!submissionBuilder.answerIdIsValid()) {
				errorResponse.reject("measure", measure.getMeasureId().toString(), "Invalid answer ID submitted");
				continue;
			}

			// See if this has already been answered. If not, create a new one.
			SurveyMeasureResponse surveyMeasureResponse = surveyMeasureResponseRepository.find(assessmentRequest.getAssessmentId(), answerId, answer.getRowId());

			// This is a 'transient' hibernate object since we created it.
			if (surveyMeasureResponse == null) {
				surveyMeasureResponse = new SurveyMeasureResponse();
				surveyMeasureResponse.setVeteranAssessment(veteranAssessment);
				surveyMeasureResponse.setSurvey(surveyPage.getSurvey());
				surveyMeasureResponse.setMeasure(dbMeasure);
				surveyMeasureResponse.setTabularRow(answer.getRowId());
			}

			submissionBuilder.setSurveyMeasureResponse(surveyMeasureResponse);

			// Set the error collection if there are any parsing exceptions.
			if (StringUtils.isBlank(userResponse)) {
				surveyMeasureResponse.setBooleanValue(null);
				surveyMeasureResponse.setNumberValue(null);
				surveyMeasureResponse.setTextValue(null);
			} else {
				// set response values and validate
				if (answer.getRowId() == null)
					answerProcessor.process(submissionBuilder.build(), 0);
				else {
					answerProcessor.process(submissionBuilder.build(), answer.getRowId());
				}
			}

			// Add to queue to save to database if everything is okay to save.
			surveyMeasureResponseList.add(surveyMeasureResponse);
		}
	}

	private void validateMeasureAndAnswersAreNotNull(
			ErrorResponse errorResponse, AssessmentRequest assessmentRequest) {

		for (Measure measure : assessmentRequest.getUserAnswers()) {
			if (measure.getMeasureId() == null) {
				errorResponse.reject("page", "0", "Measure ID cannot be NULL");
				errorResponse.addDeveloperMessage("A measure was posted back that did not have a measureId");
			} else {
				if (measure.getTableAnswers() != null) {
					for (List<Answer> tableAnswer : measure.getTableAnswers()) {
						if (tableAnswer != null) {
							for (Answer answer : tableAnswer) {
								if (answer.getAnswerId() == null) {
									errorResponse.reject("measure", measure.getMeasureId().toString(), "Unable to process submitted data.");
									errorResponse.addDeveloperMessage("A measure was posted back with a NULL answerId for measureId: " + measure.getMeasureId());
								}
							}
						}
					}
				}

				if (measure.getAnswers() != null) {
					for (Answer answer : measure.getAnswers()) {
						if (answer.getAnswerId() == null) {
							errorResponse.reject("measure", measure.getMeasureId().toString(), "Unable to process submitted data.");
							errorResponse.addDeveloperMessage("A measure was posted back with a NULL answerId for measureId: " + measure.getMeasureId());
						}
					}
				}
			}
		}
	}

	private void persistToNonAssessmentStructure(AssessmentRequest response,
			SurveyPage surveyPage) {

		VeteranDto veteran = assessmentContext.getVeteran();

		// persist the identification answers to the Veteran datasource
		if (veteran != null && response != null && response.getUserAnswers() != null && surveyPage != null && surveyPage.getSurvey() != null && surveyPage.getSurvey().getSurveyId() != null && surveyPage.getSurvey().getSurveyId().intValue() == AssessmentConstants.SURVEY_IDENTIFICATION_ID) {

			for (Measure measure : response.getUserAnswers()) {
				if (measure.getMeasureId().intValue() == AssessmentConstants.MEASURE_IDENTIFICATION_FIRST_NAME_ID) {
					if (measure.getAnswers() != null && measure.getAnswers().get(0) != null)
						veteran.setFirstName(measure.getAnswers().get(0).getAnswerResponse());
				} else if (measure.getMeasureId().intValue() == AssessmentConstants.MEASURE_IDENTIFICATION_MIDDLE_NAME_ID) {
					if (measure.getAnswers() != null && measure.getAnswers().get(0) != null)
						veteran.setMiddleName(measure.getAnswers().get(0).getAnswerResponse());
				}
				// else if (measure.getMeasureId().intValue() ==
				// AssessmentConstants.MEASURE_IDENTIFICATION_SUFFIX_ID) {
				// if (measure.getAnswers() != null &&
				// measure.getAnswers().get(0) != null)
				// veteran.setSuffix(measure.getAnswers().get(0).getAnswerResponse());
				// }
				else if (measure.getMeasureId().intValue() == AssessmentConstants.MEASURE_IDENTIFICATION_EMAIL) {
					if (measure.getAnswers() != null && measure.getAnswers().get(0) != null)
						veteran.setEmail(measure.getAnswers().get(0).getAnswerResponse());
				} else if (measure.getMeasureId().intValue() == AssessmentConstants.MEASURE_IDENTIFICATION_PHONE_) {
					if (measure.getAnswers() != null && measure.getAnswers().get(0) != null)
						veteran.setPhone(measure.getAnswers().get(0).getAnswerResponse());
				} else if (measure.getMeasureId().intValue() == AssessmentConstants.MEASURE_IDENTIFICATION_CALL_TIME && measure.getAnswers() != null) {

					for (Answer answer : measure.getAnswers()) {
						if (!Strings.isNullOrEmpty(answer.getAnswerResponse())) {
							String userResponse = answer.getAnswerResponse().trim();
							if (userResponse.equalsIgnoreCase("true")) {
								MeasureAnswer selectedAnswer = measureAnswerRepository.findOne(answer.getAnswerId());
								if (selectedAnswer != null) {
									// use the text of the answer in case the
									// answer ID changes due to new answers
									// being added/removed to the question
									veteran.setBestTimeToCall(selectedAnswer.getAnswerText());
									veteran.setBestTimeToCallOther(answer.getOtherAnswerResponse());
								}
							}
						}
					}
				}
			}

			veteranService.updateDemographicsData(veteran);
		}
	}
}
