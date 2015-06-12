package gov.va.escreening.context;

import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Table;

@Component("veteranAssessmentSmrList")
public class VeteranAssessmentSmrList {
	@Resource(type = SurveyMeasureResponseRepository.class)
	SurveyMeasureResponseRepository smrr;

	private ThreadLocal<Cache> t = new ThreadLocal<>();

	public List<SurveyMeasureResponse> fetchCachedSmr(int vetAssId) {
	    Cache cache = getCache();
	    
		if (cache.allResponses == null) {
		    cache.allResponses = smrr.findForVeteranAssessmentId(vetAssId);
		}
		return cache.allResponses;
	}

	/**
	 * clear the thread with its weight as we are in a thread pool
	 */
	public void clearSmrFromCache() {
	    getCache().clear();
		t.remove();
	}

	public ListMultimap<Integer, SurveyMeasureResponse> fetchCachedSmrBySurvey(
	        Integer veteranAssessmentId, Integer surveyId) {
	    return getCache().getSmrBySurvey(veteranAssessmentId, surveyId);
    }
	
    public void setCachedSmrBySurvey(Integer veteranAssessmentId, Integer surveyId,
            ListMultimap<Integer, SurveyMeasureResponse> responseMap) {
        getCache().getSmrBySurvey(veteranAssessmentId, surveyId, responseMap);
    }

    private Cache getCache(){
        Cache cache = t.get();
        if(cache == null){
            cache = new Cache();
            t.set(cache);
        }
        return cache;
    }
    
    private static class Cache{
        
        private List<SurveyMeasureResponse> allResponses;
        private Table<Integer, Integer, ListMultimap<Integer, SurveyMeasureResponse>> smrBySurveyTable = HashBasedTable.create();
        
        private void clear(){
            smrBySurveyTable.clear();
            allResponses = null;
        }
        
        private ListMultimap<Integer, SurveyMeasureResponse> getSmrBySurvey(Integer veteranAssessmentId, Integer surveyId){
            return smrBySurveyTable.get(veteranAssessmentId, surveyId);
        }
        
        private void getSmrBySurvey(Integer veteranAssessmentId, Integer surveyId, ListMultimap<Integer, SurveyMeasureResponse> responseMap){
            smrBySurveyTable.put(veteranAssessmentId, surveyId, responseMap);
        }
        
    }
}
