package gov.va.escreening.service;

import gov.va.escreening.entity.SurveyScoreInterval;
import gov.va.escreening.repository.SurveyScoreIntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kliu on 3/6/15.
 */
@Service
public class SurveyScoreIntervalServiceImpl implements SurveyScoreIntervalService {

    @Autowired
    private SurveyScoreIntervalRepository intervalRepository;

    @Transactional(readOnly = true)
    @Override
    public String getScoreMeaning(Integer surveyId, Number score) {

        List<SurveyScoreInterval> intervals = intervalRepository.getIntervalsBySurvey(surveyId);

        for (SurveyScoreInterval interval : intervals) {

            if (score.floatValue() >= Float.parseFloat(interval.getMin())
                    &&
                    score.floatValue() <= Float.parseFloat(interval.getMax())) {
                return interval.getMeaning();
            }
        }
        return null;

    }


    @Transactional(readOnly = true)
    @Override
    public String generateMetadataJson(Integer surveyId) {

        List<SurveyScoreInterval> intervals = intervalRepository.getIntervalsBySurvey(surveyId);

        if (intervals == null || intervals.isEmpty()) {
            return null;
        }

        String max = "-1";

        String min = "100000";

        StringBuffer tick = new StringBuffer();
        StringBuffer sbInterval = new StringBuffer();

        for (SurveyScoreInterval interval : intervals) {

            if (!interval.isException()) {
                if (Float.parseFloat(min) > Float.parseFloat(interval.getMin())) {
                    min = interval.getMin();
                }
                if (Float.parseFloat(max) < Float.parseFloat(interval.getMax())) {
                    max = interval.getMax();
                }

                if (tick.length()==0){
                    tick.append(interval.getMin());
                }
                else {
                    tick.append("," + interval.getMin());
                }

                if (sbInterval.length() == 0){
                    sbInterval.append("\""+interval.getMeaning()+"\":"+interval.getMin());
                }
                else {
                    sbInterval.append(",\""+interval.getMeaning()+"\":"+interval.getMin());
                }
            }


        }

        return String.format(intervalTemplate, tick.toString(), sbInterval.toString(), max);


    }

    public static final String intervalTemplate = "{ ticks: [%s], \"score\": 16, \n" +
            "  \"footer\": \"\", \n" +
            "  \"varId\": 1599, \n" +
            "  \"title\": \"My Score\", \n" +
            "  \"intervals\": {%s}, \n" +
            "  \"maxXPoint\": %s, \n" +
            "  \"numberOfMonths\": 12\n" +
            "}";

}
