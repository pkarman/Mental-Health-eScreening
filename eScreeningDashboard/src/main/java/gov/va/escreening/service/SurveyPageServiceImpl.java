package gov.va.escreening.service;

import gov.va.escreening.repository.SurveyPageRepository;

import java.util.List;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

@Transactional
@Service
public class SurveyPageServiceImpl implements SurveyPageService {
    
    private static final Logger logger = LoggerFactory.getLogger(SurveyPageServiceImpl.class);

    @Autowired
    private SurveyPageRepository surveyPageRepository;

    @Transactional(readOnly = true)
    @Override
    public Integer getFirstUnansweredSurveyPage(int veteranAssessmentId, @Nullable Integer surveySectionId) {

        List<Object[]> resultList = surveyPageRepository.getSurveyPageSkippedStatuses(veteranAssessmentId, 
                                                                                      surveySectionId);
        for (Object[] cols : resultList) {
            boolean containsSkippedQuestion = Integer.parseInt(cols[1].toString()) == 1;
            
            if (containsSkippedQuestion) {
                return Integer.parseInt(cols[0].toString());
            }
        }

        // Everything has been answered, so return the first page.
        return Integer.parseInt(resultList.get(0)[0].toString());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Integer> getNextUnansweredSurveyPage(int veteranAssessmentId, int surveyPageId) {
        
        Integer nextSurveyPageId = null;
        boolean found = false;
        for(Object[] tuple : surveyPageRepository.getSurveyPageSkippedStatuses(veteranAssessmentId)){
            int currSurveyPageId = Integer.parseInt(tuple[0].toString());
            boolean containsSkippedQuestion = Integer.parseInt(tuple[1].toString()) == 1;
            
            if(found && containsSkippedQuestion){
                nextSurveyPageId = currSurveyPageId;
                break;
            }
            
            found = found || (currSurveyPageId == surveyPageId);
        }
        
        return Optional.fromNullable(nextSurveyPageId);
    }

}
