package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentMeasureVisibility;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VeteranAssessmentMeasureVisibilityRepository extends RepositoryInterface<VeteranAssessmentMeasureVisibility> {

    /**
     * Retrieves visibility mapping for the given veteran assessment ID
     * @param VeteranAssessmentId
     * @return
     */
    public Map<Integer, Boolean> getMeasureVisibilityMapFor(int veteranAssessmentId);
    
    /**
     * Updates the visibility mapping for the given veteran assessment ID, for the measures found in the given map.
     * This method will not add any measures found in the given map that are not currently tracked. So the map 
     * will be used to update measure visibility for any currently tracked measures (e.g. previously set by calling 
     * {@link gov.va.escreening.service.VeteranAssessmentService.initializeVisibilityFor()}
     * @param VeteranAssessmentId
     * @param measureVisibilityMap - map from measure ID to visibility boolean. Note currently that the entries that are
     * for follow-up questions will be removed from this map.  This doesn't have to be but it makes stuff faster.
     * @return a set containing measures from the passed in visibility map that are not follow-up questions
     */
    public Set<Integer> setMeasureVisibilityFor(int veteranAssessmentId, Map<Integer, Boolean> measureVisibilityMap);

    
    /**
     * Retrieves List of measure visibilities for given assessment 
     * @param veteranAssessmentId
     * @return
     */
    public List<VeteranAssessmentMeasureVisibility> getVisibilityListFor(int veteranAssessmentId);

    /**
     * Remove any visibility entries for given assessment
     * @param veteranAssessmentId
     */
    void clearVisibilityFor(int veteranAssessmentId);
    
    /**
     * Retrieves the visibility map for a given assessment and survey page
     * @param veteranAssessmentId
     * @param surveyPageId
     * @return
     */
    public Map<Integer, Boolean> getVisibilityMapForSurveyPage(int veteranAssessmentId, int surveyPageId);
    
    /**
     * Retrieves list of measure visibilities for a given assessment and survey page
     * @param veteranAssessmentId
     * @param surveyPageId
     * @return
     */
    public List<VeteranAssessmentMeasureVisibility> getVisibilityListForSurveyPage(int veteranAssessmentId, int surveyPageId);
}
