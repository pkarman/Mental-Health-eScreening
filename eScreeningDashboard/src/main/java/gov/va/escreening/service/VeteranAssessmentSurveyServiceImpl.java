package gov.va.escreening.service;

import gov.va.escreening.dto.VeteranAssessmentProgressDto;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentSurvey;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.repository.VeteranAssessmentSurveyRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class VeteranAssessmentSurveyServiceImpl implements VeteranAssessmentSurveyService {

	private static final Logger logger = LoggerFactory.getLogger(VeteranAssessmentSurveyServiceImpl.class);

	@Autowired
	private VeteranAssessmentSurveyRepository veteranAssessmentSurveyRepository;

	@Autowired
	private VeteranAssessmentRepository veteranAssessmentRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Survey> getSurveys(Integer veteranAssessmentId) {
		return veteranAssessmentSurveyRepository.findSurveyListByVeteranAssessmentId(veteranAssessmentId);
	}

	@Override
	public void updateProgress(int veteranAssessmentId, long startTime) {
		logger.debug("updateProgress");

		// Run the SQL and get the list of counts.
		List<Object[]> resultList = veteranAssessmentSurveyRepository.calculateProgress(veteranAssessmentId);

		// Update the fields accordingly and save to database.
		int runningTotalResponses = 0;
		int runningTotalQuestions = 0;
		for (Object[] row : resultList) {
			int surveyId = Integer.valueOf(row[0].toString());
			int countOfTotalQuestions = Integer.valueOf(row[1].toString());
			int countOfTotalResponses = Integer.valueOf(row[2].toString());

			gov.va.escreening.entity.VeteranAssessmentSurvey veteranAssessmentSurvey = veteranAssessmentSurveyRepository.getByVeteranAssessmentIdAndSurveyId(veteranAssessmentId, surveyId);
			veteranAssessmentSurvey.setTotalQuestionCount(countOfTotalQuestions);
			veteranAssessmentSurvey.setTotalResponseCount(countOfTotalResponses);
			veteranAssessmentSurveyRepository.update(veteranAssessmentSurvey);

			runningTotalResponses += countOfTotalResponses;
			runningTotalQuestions += countOfTotalQuestions;
		}
		updateVeteranAssessmentProgress(veteranAssessmentId, startTime, runningTotalResponses, runningTotalQuestions);
	}

	private void updateVeteranAssessmentProgress(int veteranAssessmentId,
			long startTime, int countOfTotalResponses,
			int countOfTotalQuestions) {

		VeteranAssessment va = veteranAssessmentRepository.findOne(veteranAssessmentId);

		// determine the percentage completed
		int percentCompleted = (int) ((float) countOfTotalResponses / countOfTotalQuestions * 100);
		va.setPercentComplete(percentCompleted);

		int durationCurrent = getAssessmentProgressDurationInminutes(startTime);
		Integer previousDuration = va.getDuration();
		if (previousDuration == null) {
			previousDuration = 0;
		}
		va.setDuration(previousDuration + durationCurrent);
		// determine the duration this veteran is on this assessment (in
		// minutes)

		va.setDateUpdated(new Date());
		veteranAssessmentRepository.update(va);
	}

	private int getAssessmentProgressDurationInminutes(long sessionCreationTime) {
		DateTime ft = new DateTime();
		int minutesOfHour = ft.minus(sessionCreationTime).getMinuteOfHour();
		return minutesOfHour;
	}

	@Override
	public Boolean doesVeteranAssessmentContainSurvey(int veteranAssessmentId,
			int surveyId) {
		logger.trace("doesVeteranAssessmentContainSurvey");

		List<VeteranAssessmentSurvey> assignedSurveys = veteranAssessmentSurveyRepository.findSurveyBySurveyAssessment(veteranAssessmentId, surveyId);

		if (assignedSurveys.size() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public List<VeteranAssessmentProgressDto> getProgress(
			int veteranAssessmentId) {

		List<VeteranAssessmentSurvey> veteranAssessmentSurveyList = veteranAssessmentSurveyRepository.forVeteranAssessmentId(veteranAssessmentId);

		LinkedHashMap<Integer, VeteranAssessmentProgressDto> map = new LinkedHashMap<Integer, VeteranAssessmentProgressDto>();

		for (VeteranAssessmentSurvey veteranAssessmentSurvey : veteranAssessmentSurveyList) {

			Integer surveySectionId = veteranAssessmentSurvey.getSurvey().getSurveySection().getSurveySectionId();
			VeteranAssessmentProgressDto veteranAssessmentProgressDto = null;

			if (map.containsKey(surveySectionId)) {
				veteranAssessmentProgressDto = map.get(surveySectionId);
			} else {
				veteranAssessmentProgressDto = new VeteranAssessmentProgressDto();
				veteranAssessmentProgressDto.setVeteranAssessmentId(veteranAssessmentId);
				veteranAssessmentProgressDto.setSurveySectionId(veteranAssessmentSurvey.getSurvey().getSurveySection().getSurveySectionId());
				veteranAssessmentProgressDto.setSurveySectionName(veteranAssessmentSurvey.getSurvey().getSurveySection().getName());
				veteranAssessmentProgressDto.setTotalQuestionCount(0);
				veteranAssessmentProgressDto.setTotalResponseCount(0);
				veteranAssessmentProgressDto.setPercentComplete(0);

				map.put(surveySectionId, veteranAssessmentProgressDto);
			}

			int totalQuestionCount = veteranAssessmentProgressDto.getTotalQuestionCount();

			if (veteranAssessmentSurvey.getTotalQuestionCount() != null) {
				totalQuestionCount += veteranAssessmentSurvey.getTotalQuestionCount();
			}

			int totalResponseCount = veteranAssessmentProgressDto.getTotalResponseCount();

			if (veteranAssessmentSurvey.getTotalResponseCount() != null) {
				totalResponseCount += veteranAssessmentSurvey.getTotalResponseCount();
			}

			veteranAssessmentProgressDto.setTotalQuestionCount(totalQuestionCount);
			veteranAssessmentProgressDto.setTotalResponseCount(totalResponseCount);

			if (totalQuestionCount > 0) {
				veteranAssessmentProgressDto.setPercentComplete(Math.round(((float) totalResponseCount / (float) totalQuestionCount) * 100));
			}
		}

		List<VeteranAssessmentProgressDto> results = null;

		if (map != null) {
			results = new ArrayList<VeteranAssessmentProgressDto>();

			Iterator<Entry<Integer, VeteranAssessmentProgressDto>> it = map.entrySet().iterator();

			while (it.hasNext()) {
				results.add(it.next().getValue());
			}
		}

		return results;
	}
}
