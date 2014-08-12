package gov.va.escreening.delegate;

import static gov.va.escreening.constants.AssessmentConstants.ASSESSMENT_EVENT_MARKED_COMPLETED;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_IDENTIFICATION_CALL_TIME;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_IDENTIFICATION_EMAIL;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_IDENTIFICATION_FIRST_NAME_ID;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_IDENTIFICATION_LAST_NAME_ID;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_IDENTIFICATION_MIDDLE_NAME_ID;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_IDENTIFICATION_PHONE_;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_IDENTIFICATION_SSN_LAST_FOUR;
import static gov.va.escreening.constants.AssessmentConstants.PERSON_TYPE_VETERAN;
import static gov.va.escreening.constants.AssessmentConstants.SURVEY_IDENTIFICATION_ID;
import gov.va.escreening.context.AssessmentContext;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.domain.VeteranDtoHelper;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.AssessmentResponse;
import gov.va.escreening.dto.ae.CompletionResponse;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentAuditLog;
import gov.va.escreening.entity.VeteranAssessmentAuditLogHelper;
import gov.va.escreening.exception.InvalidAssessmentContextException;
import gov.va.escreening.repository.AssessmentStatusRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.SurveySectionRepository;
import gov.va.escreening.repository.VeteranAssessmentAuditLogRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.service.AssessmentEngineService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.service.VeteranAssessmentSurveyService;
import gov.va.escreening.service.VeteranService;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

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

    @Override
    public List<VeteranDto> findVeterans(VeteranDto veteran) {
        logger.debug("findVeterans");
        return veteranService.findVeterans(veteran);
    }

    @Override
    public VeteranAssessment getAvailableVeteranAssessment(Integer veteranId) {

        List<VeteranAssessment> veteranAssessments = veteranAssessmentService
                .getAvailableAssessmentsForVeteran(veteranId);

        if (veteranAssessments == null || veteranAssessments.size() < 1) {
            return null;
        }
        else {
            for (VeteranAssessment assessment : veteranAssessments) {
                if (surveyRepository.findForVeteranAssessmentId(assessment.getVeteranAssessmentId()).size() > 0)
                    return assessment;
            }
            return null;
        }
    }

    @Override
    public void setUpAssessmentContext(VeteranDto veteran, VeteranAssessment veteranAssessment) {
        logger.debug("Set up assessment context");

        assessmentContext.setVeteran(veteran);
        assessmentContext.setVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());
        assessmentContext.setIsInitialized(true);
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

        AssessmentResponse response = assessmentEngineService.processPage(assessmentRequest);

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
        if (veteran != null && response.getAssessment() != null &&
                response.getAssessment().getCurrentSurveyId() == SURVEY_IDENTIFICATION_ID) {
            for (Measure measure : response.getPage().getMeasures()) {
                
                String responseText = null;                
                if(!measure.getAnswers().isEmpty() && measure.getAnswers().get(0) != null){
                    switch(measure.getMeasureId()){
                
                        case MEASURE_IDENTIFICATION_FIRST_NAME_ID:
                            responseText = veteran.getFirstName();
                            break;
                        case MEASURE_IDENTIFICATION_MIDDLE_NAME_ID:
                            responseText = veteran.getMiddleName();
                            break;
                        //case MEASURE_IDENTIFICATION_SUFFIX_ID:
                        //    responseText = veteran.getSuffix();
                        //    break;
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
                            if(!Strings.isNullOrEmpty(veteran.getBestTimeToCall())){
                                // pre populate best time to call
                                try{
                                    String prevResponse = veteran.getBestTimeToCall();
                                    for(Answer answer : measure.getAnswers()){
                                        if(prevResponse.equalsIgnoreCase(answer.getAnswerText())){
                                            answer.setAnswerResponse("true");
                                            answer.setOtherAnswerResponse(veteran.getBestTimeToCallOther());
                                        }
                                    }
                                }
                                catch(NumberFormatException e){
                                    logger.error("Error parsing previously saved call time measure answer", e);
                                }
                            }
                            return;
                    }
                    
                    if(responseText != null){
                        measure.getAnswers().get(0).setAnswerResponse(responseText);
                    }
                }
                
            }
        }
    }

    @Override
    public String getVeteranFullName() {
        if(assessmentContext.getVeteran() == null){
            return null;
        }
        
        if (StringUtils.isNotBlank(assessmentContext.getVeteran().getFirstName())) {
            return assessmentContext.getVeteran().getLastName() + ", " + assessmentContext.getVeteran().getFirstName();
        }
        else {
            return assessmentContext.getVeteran().getLastName();
        }
    }

    @Override
    public CompletionResponse getCompletionResponse() {

        // TODO: we will be integrating with database in new ticket

        CompletionResponse response = new CompletionResponse();

        // test data program specific completion text
        response.setCompletionText("<b>Thank you for your time and effort.</b> Next, you will finish your "
                +
                "enrollment for health benefits and receive a individualized print-out of health recommendations based on the "
                +
                "information you provided. If you have any questions or comments about this process, please call the " +
                "Intake eScreening Study Office at (858) 552-8585 x5550.");

        // test data for summary notes
        response.setSummaryNotes(Arrays
                .asList(
                        "<b>Weight Issue</b><br/>This is an example note telling you about an something that was triggered by your responses. Sudden she seeing garret far regard. By hardly it direct if pretty up regret. Ability thought enquire settled prudent you sir. Or easy knew sold on well come year. Something consulted age extremely end procuring. Collecting preference he inquietude projection me in by. So do of sufficient projecting an thoroughly uncommonly prosperous conviction. Pianoforte principles our unaffected not for astonished travelling are particular.",
                        "<b>Drug Abuse</b><br/>Her companions instrument set estimating sex remarkably solicitude motionless. Property men the why smallest graceful day insisted required. Inquiry justice country old placing sitting any ten age. Looking venture justice in evident in totally he do ability. Be is lose girl long of up give. Trifling wondered unpacked ye at he. In household certainty an on tolerably smallness difficult. Many no each like up be is next neat. Put not enjoyment behaviour her supposing. At he pulled object others.",
                        "<b>Sleep Patterns</b><br/>Him rendered may attended concerns jennings reserved now. Sympathize did now preference unpleasing mrs few. Mrs for hour game room want are fond dare. For detract charmed add talking age. Shy resolution instrument unreserved man few. She did open find pain some out. If we landlord stanhill mr whatever pleasure supplied concerns so. Exquisite by it admitting cordially september newspaper an. Acceptance middletons am it favourable. It it oh happen lovers afraid. "
                ));

        return response;
    }

    @Override
    public void markAssessmentAsComplete() {
        Integer assessmentId = assessmentContext.getVeteranAssessmentId();
        VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(assessmentId);

        // update the status of the assessment to complete
        AssessmentStatus status = assessmentStatusRepository.findOne(AssessmentStatusEnum.COMPLETE.getAssessmentStatusId());
        veteranAssessment.setAssessmentStatus(status);
        veteranAssessmentRepository.update(veteranAssessment);

        // TODO: Currently only a Veteran can take the assessment, person type will need to be detected once a
        // user can take an assessment to properly track the person_id
        veteranAssessment = veteranAssessmentRepository.findOne(assessmentId);
        VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(
                veteranAssessment, ASSESSMENT_EVENT_MARKED_COMPLETED,
                veteranAssessment.getAssessmentStatus().getAssessmentStatusId(),
                PERSON_TYPE_VETERAN);
        veteranAssessmentAuditLogRepository.update(auditLogEntry);
    }
}
