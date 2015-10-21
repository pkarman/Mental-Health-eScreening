package gov.va.escreening.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import gov.va.escreening.dto.VeteranAssessmentProgressDto;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.entity.*;
import gov.va.escreening.repository.SurveyAttemptRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.repository.VeteranAssessmentSurveyRepository;

import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;

import gov.va.escreening.util.ReportsUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service
public class VeteranAssessmentSurveyServiceImpl implements
        VeteranAssessmentSurveyService {

    private static final Logger logger = LoggerFactory
            .getLogger(VeteranAssessmentSurveyServiceImpl.class);

    @Autowired
    private VeteranAssessmentSurveyRepository veteranAssessmentSurveyRepository;

    @Autowired
    private VeteranAssessmentRepository veteranAssessmentRepository;

    @Resource(type = SurveyAttemptRepository.class)
    SurveyAttemptRepository sar;

    @Transactional(readOnly = true)
    @Override
    public List<Survey> getSurveys(Integer veteranAssessmentId) {
        return veteranAssessmentSurveyRepository
                .findSurveyListByVeteranAssessmentId(veteranAssessmentId);
    }

    @Override
    public void updateProgress(int veteranAssessmentId, long startTime) {
        logger.trace("updateProgress");

        // Run the SQL and get the list of counts.
        List<Object[]> resultList = veteranAssessmentSurveyRepository
                .calculateProgress(veteranAssessmentId);

        // Update the fields accordingly and save to database.
        int runningTotalResponses = 0;
        int runningTotalQuestions = 0;
        for (Object[] row : resultList) {
            int surveyId = Integer.valueOf(row[0].toString());
            int countOfTotalQuestions = Integer.valueOf(row[1].toString());
            int countOfTotalResponses = Integer.valueOf(row[2].toString());

            gov.va.escreening.entity.VeteranAssessmentSurvey veteranAssessmentSurvey = veteranAssessmentSurveyRepository
                    .getByVeteranAssessmentIdAndSurveyId(veteranAssessmentId,
                            surveyId);
            veteranAssessmentSurvey
                    .setTotalQuestionCount(countOfTotalQuestions);
            veteranAssessmentSurvey
                    .setTotalResponseCount(countOfTotalResponses);
            veteranAssessmentSurveyRepository.update(veteranAssessmentSurvey);

            runningTotalResponses += countOfTotalResponses;
            runningTotalQuestions += countOfTotalQuestions;
        }
        updateVeteranAssessmentProgress(veteranAssessmentId, startTime,
                runningTotalResponses, runningTotalQuestions);
    }

    @Override
    public void updateProgress(VeteranAssessment va, AssessmentRequest req,
                               Survey survey, List<VeteranAssessmentMeasureVisibility> visList) {
        int total = 0;
        int answered = 0;

        // va.getSurveyMeasureResponseList()
        Set<Integer> answeredMeasures = new HashSet<Integer>();
        for (SurveyMeasureResponse resp : va.getSurveyMeasureResponseList()) {

            if (resp.getBooleanValue() == null || resp.getBooleanValue()) {
                answeredMeasures.add(resp.getMeasure().getMeasureId());
                if (resp.getMeasure().getParent() != null) {
                    answeredMeasures.add(resp.getMeasure().getParent().getMeasureId());
                }
            }
        }

        Set<Integer> invisibleMeasureList = new HashSet<Integer>();
        for (VeteranAssessmentMeasureVisibility vis : visList) {
            if (vis.getIsVisible() != null && !vis.getIsVisible()) {
                invisibleMeasureList.add(vis.getMeasure().getMeasureId());
            }
        }
        // First calculate total measures
        List<SurveyPage> pageList = survey.getSurveyPageList();
        for (SurveyPage sp : pageList) {
            for (Measure m : sp.getMeasures()) {
                if (!invisibleMeasureList.contains(m.getMeasureId())) {
                    if (m.getMeasureType().getMeasureTypeId() != 8 && (m.getMeasureType().getMeasureTypeId() == 4 ||
                            m.getChildren() == null || m.getChildren().isEmpty())) {
                        total++;
                        if (answeredMeasures.contains(m.getMeasureId())) {
                            answered++;
                        }
                    } else {
                        total += m.getChildren().size();

                        for (Measure c : m.getChildren()) {
                            if (answeredMeasures.contains(c.getMeasureId())) {
                                answered++;
                            }
                        }
                    }
                }

            }
        }
        gov.va.escreening.entity.VeteranAssessmentSurvey veteranAssessmentSurvey = veteranAssessmentSurveyRepository
                .getByVeteranAssessmentIdAndSurveyId(
                        va.getVeteranAssessmentId(), survey.getSurveyId());
        veteranAssessmentSurvey.setTotalQuestionCount(total);
        veteranAssessmentSurvey.setTotalResponseCount(answered);
        veteranAssessmentSurveyRepository.update(veteranAssessmentSurvey);

        updateVeteranAssessmentProgress(va, req);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Map<String, String>> calculateAvgTimePerSurvey(Map<String, Object> requestData) {
        List<Integer> clinics = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
        final LocalDate fromDate = dtf.parseLocalDate(requestData.get(ReportsUtil.FROMDATE).toString());
        final LocalDate toDate = dtf.parseLocalDate(requestData.get(ReportsUtil.TODATE).toString());

        List<VeteranAssessmentSurvey> vasLst = veteranAssessmentSurveyRepository.findByClinicAndDateRange(clinics, fromDate.toDate(), toDate.toDate());

        // group these by surveys
        Multimap<Survey, VeteranAssessmentSurvey> surveyMap = ArrayListMultimap.create();
        for (VeteranAssessmentSurvey vas : vasLst) {
            surveyMap.put(vas.getSurvey(), vas);
        }

        // find average
        Map<Integer, Map<String, String>> avgMap = Maps.newHashMap();
        for (Survey s : surveyMap.keySet()) {
            avgMap.put(s.getSurveyId(), findAvgElapsedTime(surveyMap.get(s)));
        }

        return avgMap;
    }

    private Map<String, String> findAvgElapsedTime(Collection<VeteranAssessmentSurvey> veteranAssessmentSurveys) {
        Period totalPeriod = Period.seconds(0);
        int totalSurveyAttempts = 0;
        for (VeteranAssessmentSurvey vas : veteranAssessmentSurveys) {
            List<SurveyAttempt> surveyAttempts = vas.getSurveyAttemptList();
            if (surveyAttempts != null) {
                totalSurveyAttempts += surveyAttempts.size();
                for (SurveyAttempt sa : surveyAttempts) {
                    DateTime dtStart = new DateTime(sa.getStartDate().getTime());
                    DateTime dtEnd = new DateTime(sa.getEndDate().getTime());
                    Period period = new Period(dtStart, dtEnd);
                    totalPeriod = period.plus(totalPeriod);
                }
            }
        }
        // calculate the avg
        long secs = totalPeriod.toStandardDuration().getStandardSeconds();
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        String standardSecs=nf.format(totalSurveyAttempts==0?0:secs / totalSurveyAttempts);
        double avgTotalSecs = Double.parseDouble(standardSecs);

        int avgMin = (int) avgTotalSecs / 60;
        int avgSec = (int) avgTotalSecs % 60;

        Map<String, String> m = Maps.newHashMap();
        m.put("MODULE_TOTAL_TIME", String.valueOf(totalSurveyAttempts));
        m.put("MODULE_AVG_SEC", String.format("%02d",avgSec));
        m.put("MODULE_AVG_MIN", String.format("%02d",avgMin));
        m.put("AVG_TIME_AS_STRING", String.format("%02dm %02ds n=%s", avgMin, avgSec, totalSurveyAttempts));


        return m;
    }

    private void updateVeteranAssessmentProgress(VeteranAssessment va,
                                                 AssessmentRequest assessmentRequest) {
        int total = 0;
        int answered = 0;
        for (VeteranAssessmentSurvey vas : va.getVeteranAssessmentSurveyList()) {
            if (vas.getTotalQuestionCount() != null) {
                total += vas.getTotalQuestionCount();
            }
            if (vas.getTotalResponseCount() != null) {
                answered += vas.getTotalResponseCount();
            }

            // add the timing it took to complete this survey
            addSurveyAttempt(vas, assessmentRequest);
        }

        int percentCompleted = (int) ((float) answered / total * 100);
        va.setPercentComplete(percentCompleted);

        int durationCurrent = getAssessmentProgressInSeconds(assessmentRequest.getAssessmentStartTime());
        Integer previousDuration = va.getDuration();
        if (previousDuration == null) {
            previousDuration = 0;
        }
        va.setDuration(previousDuration + durationCurrent);
        va.setDateUpdated(new Date());
        veteranAssessmentRepository.update(va);
    }

    private void addSurveyAttempt(VeteranAssessmentSurvey vas, AssessmentRequest assessmentRequest) {
        Long startTs = assessmentRequest.getModuleStartTime(vas.getSurvey().getSurveyId());
        if (startTs != null) {
            SurveyAttempt sa = new SurveyAttempt();
            sa.setVeteranAssessmentSurvey(vas);
            sa.setStartDate(new Date(startTs));
            sa.setEndDate(new Date());
            sa.setDateCreated(new Date());
            sar.update(sa);
        }
    }

    private void updateVeteranAssessmentProgress(int veteranAssessmentId,
                                                 long startTime, int countOfTotalResponses, int countOfTotalQuestions) {

        VeteranAssessment va = veteranAssessmentRepository
                .findOne(veteranAssessmentId);

        // determine the percentage completed
        int percentCompleted = (int) ((float) countOfTotalResponses
                / countOfTotalQuestions * 100);
        va.setPercentComplete(percentCompleted);

        int durationCurrent = getAssessmentProgressInSeconds(startTime);
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

    private int getAssessmentProgressInSeconds(long st) {
        long now = System.currentTimeMillis();
        int elapsedMilli = (int) (now - st);
        Period p = new Period(elapsedMilli);
        int secs = (int) p.toStandardDuration().getStandardSeconds();
        return secs;
    }

    @Override
    public Boolean doesVeteranAssessmentContainSurvey(int veteranAssessmentId,
                                                      int surveyId) {
        logger.trace("doesVeteranAssessmentContainSurvey");

        List<VeteranAssessmentSurvey> assignedSurveys = veteranAssessmentSurveyRepository
                .findSurveyBySurveyAssessment(veteranAssessmentId, surveyId);

        if (assignedSurveys.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public List<VeteranAssessmentProgressDto> getProgress(
            int veteranAssessmentId) {

        List<VeteranAssessmentSurvey> veteranAssessmentSurveyList = veteranAssessmentSurveyRepository
                .forVeteranAssessmentId(veteranAssessmentId);

        LinkedHashMap<Integer, VeteranAssessmentProgressDto> map = new LinkedHashMap<Integer, VeteranAssessmentProgressDto>();

        for (VeteranAssessmentSurvey veteranAssessmentSurvey : veteranAssessmentSurveyList) {

            Integer surveySectionId = veteranAssessmentSurvey.getSurvey()
                    .getSurveySection().getSurveySectionId();
            VeteranAssessmentProgressDto veteranAssessmentProgressDto = null;

            if (map.containsKey(surveySectionId)) {
                veteranAssessmentProgressDto = map.get(surveySectionId);
            } else {
                veteranAssessmentProgressDto = new VeteranAssessmentProgressDto();
                veteranAssessmentProgressDto
                        .setVeteranAssessmentId(veteranAssessmentId);
                veteranAssessmentProgressDto
                        .setSurveySectionId(veteranAssessmentSurvey.getSurvey()
                                .getSurveySection().getSurveySectionId());
                veteranAssessmentProgressDto
                        .setSurveySectionName(veteranAssessmentSurvey
                                .getSurvey().getSurveySection().getName());
                veteranAssessmentProgressDto.setTotalQuestionCount(0);
                veteranAssessmentProgressDto.setTotalResponseCount(0);
                veteranAssessmentProgressDto.setPercentComplete(0);

                map.put(surveySectionId, veteranAssessmentProgressDto);
            }

            int totalQuestionCount = veteranAssessmentProgressDto
                    .getTotalQuestionCount();

            if (veteranAssessmentSurvey.getTotalQuestionCount() != null) {
                totalQuestionCount += veteranAssessmentSurvey
                        .getTotalQuestionCount();
            }

            int totalResponseCount = veteranAssessmentProgressDto
                    .getTotalResponseCount();

            if (veteranAssessmentSurvey.getTotalResponseCount() != null) {
                totalResponseCount += veteranAssessmentSurvey
                        .getTotalResponseCount();
            }

            veteranAssessmentProgressDto
                    .setTotalQuestionCount(totalQuestionCount);
            veteranAssessmentProgressDto
                    .setTotalResponseCount(totalResponseCount);

            if (totalQuestionCount > 0) {
                veteranAssessmentProgressDto
                        .setPercentComplete(Math
                                .round(((float) totalResponseCount / (float) totalQuestionCount) * 100));
            }
        }

        List<VeteranAssessmentProgressDto> results = null;

        if (map != null) {
            results = new ArrayList<VeteranAssessmentProgressDto>();

            Iterator<Entry<Integer, VeteranAssessmentProgressDto>> it = map
                    .entrySet().iterator();

            while (it.hasNext()) {
                results.add(it.next().getValue());
            }
        }

        return results;
    }
}
