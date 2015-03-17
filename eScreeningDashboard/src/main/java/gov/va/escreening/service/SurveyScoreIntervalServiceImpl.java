package gov.va.escreening.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.va.escreening.entity.SurveyScoreInterval;
import gov.va.escreening.repository.SurveyScoreIntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public Map<String, Object> generateMetadata(Integer surveyId) {

        List<SurveyScoreInterval> intervals = intervalRepository.getIntervalsBySurvey(surveyId);
        if (intervals == null || intervals.isEmpty()) {
            return null;
        }

        String max = "-1";
        String min = "100000";

        Map<String, Object> metaDataMap = createTemplateMetaData();
        List<Integer> ticks = Lists.newArrayList();
        Map<String, Object> intervalsMap = Maps.newHashMap();


        for (SurveyScoreInterval interval : intervals) {

            if (!interval.isException()) {
                if (Float.parseFloat(min) > Float.parseFloat(interval.getMin())) {
                    min = interval.getMin();
                }
                if (Float.parseFloat(max) < Float.parseFloat(interval.getMax())) {
                    max = interval.getMax();
                }

                ticks.add(Integer.valueOf(interval.getMin()));
                intervalsMap.put(interval.getMeaning(), Integer.valueOf(interval.getMin()));
            }
        }

        metaDataMap.put("ticks", ticks);
        metaDataMap.put("intervals", intervalsMap);
        metaDataMap.put("maxXPoint", Integer.valueOf(max));
        return metaDataMap;
    }

    private Map<String, Object> createTemplateMetaData() {
        Map<String, Object> metaDataMap = Maps.newHashMap();
        metaDataMap.put("score", 16);
        metaDataMap.put("footer", "");
        metaDataMap.put("varId", 1599);
        metaDataMap.put("title", "My Score");
        metaDataMap.put("numberOfMonths", 12);
        return metaDataMap;
    }
}
