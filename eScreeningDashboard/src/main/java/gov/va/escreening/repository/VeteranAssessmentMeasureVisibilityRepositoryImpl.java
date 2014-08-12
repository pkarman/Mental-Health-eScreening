package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentMeasureVisibility;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

@Repository
public class VeteranAssessmentMeasureVisibilityRepositoryImpl extends AbstractHibernateRepository<VeteranAssessmentMeasureVisibility> 
    implements VeteranAssessmentMeasureVisibilityRepository{
    
    private static final Logger logger = LoggerFactory.getLogger(VeteranAssessmentMeasureVisibilityRepositoryImpl.class);

    public VeteranAssessmentMeasureVisibilityRepositoryImpl() {
        super();
        setClazz(VeteranAssessmentMeasureVisibility.class);
    }

    @Override
    public Map<Integer, Boolean> getMeasureVisibilityMapFor(int veteranAssessmentId) {
        List<VeteranAssessmentMeasureVisibility> visibilities = getVisibilityListFor(veteranAssessmentId);
        Map<Integer, Boolean> visibilityMap = Maps.newHashMapWithExpectedSize(visibilities.size());
        
        for(VeteranAssessmentMeasureVisibility vis : visibilities){
            visibilityMap.put(vis.getMeasure().getMeasureId(), vis.getIsVisible());
        }
        
        return visibilityMap;
    }

    @Override
    public Set<Integer> setMeasureVisibilityFor(int veteranAssessmentId, Map<Integer, Boolean> measureVisibilityMap) {
        for(VeteranAssessmentMeasureVisibility vis : getVisibilityListFor(veteranAssessmentId)){
            Boolean newVisibility = measureVisibilityMap.remove(vis.getMeasure().getMeasureId());
            if(newVisibility != null && !vis.getIsVisible().equals(newVisibility)){
                    vis.setIsVisible(newVisibility);
                    update(vis);
            }
            if(measureVisibilityMap.isEmpty())
                break;
        }
        return measureVisibilityMap.keySet();
    }
    
    @Override
    public List<VeteranAssessmentMeasureVisibility> getVisibilityListFor(int veteranAssessmentId){

        return entityManager
                .createQuery("SELECT vamv FROM VeteranAssessmentMeasureVisibility vamv WHERE vamv.veteranAssessment.veteranAssessmentId = :veteranAssessmentId",
                             VeteranAssessmentMeasureVisibility.class)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .getResultList();
    }
    
    @Override
    public void clearVisibilityFor(int veteranAssessmentId){
        for(VeteranAssessmentMeasureVisibility vis : getVisibilityListFor(veteranAssessmentId)){
            delete(vis);
        }
        flush();
    }
    
    @Override
    public Map<Integer, Boolean> getVisibilityMapForSurveyPage(int veteranAssessmentId, int surveyPageId) {
        List<VeteranAssessmentMeasureVisibility> visibilities = getVisibilityListForSurveyPage(veteranAssessmentId, surveyPageId);
        Map<Integer, Boolean> visibilityMap = Maps.newHashMapWithExpectedSize(visibilities.size());
        
        for(VeteranAssessmentMeasureVisibility vis : visibilities){
            visibilityMap.put(vis.getMeasure().getMeasureId(), vis.getIsVisible());
        }
        
        return visibilityMap;
    }
    
    @Override
    public List<VeteranAssessmentMeasureVisibility> getVisibilityListForSurveyPage(int veteranAssessmentId, int surveyPageId){

        return entityManager
                .createQuery("SELECT vamv FROM VeteranAssessmentMeasureVisibility vamv "
                           + "JOIN vamv.veteranAssessment va "
                           + "JOIN va.veteranAssessmentSurveyList vas "
                           + "JOIN vas.survey s "
                           + "JOIN s.surveyPageList spl "
                           + "WHERE vamv.veteranAssessment.veteranAssessmentId = :veteranAssessmentId "
                           + "AND spl.surveyPageId = :surveyPageId ",
                             VeteranAssessmentMeasureVisibility.class)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .setParameter("surveyPageId", surveyPageId)
                .getResultList();
    }
}
