package gov.va.escreening.service;

import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveyPageInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.entity.Survey;

import java.util.Collection;
import java.util.List;

public interface SurveyService {

    /**
     * Retrieves all the survey assigned to the veteran.
     * @param veteranAssessmentId
     * @return
     */
    List<Survey> findForVeteranAssessmentId(int veteranAssessmentId);

    /**
     * Retrieves all the survey assigned to the veteran.
     * @param veteranAssessmentId
     * @return
     */
    List<SurveyDto> getSurveyListForVeteranAssessment(int veteranAssessmentId);
    
    /**
     * Retrieves all published surveys.<br/>
     * Note: Some care should be used because the requirement is that we must 
     * keep the association of a survey to an assessment even if it has become 
     * unpublished. 
     * @return
     */
    List<SurveyDto> getSurveyList();
    
    /**
     * Retrieves surveys that are either published or already assigned to the veteran assessment with the given ID.
     * @param veteranAssessmentId
     * @return
     */
    List<SurveyDto> getSurveyListUnionAssessment(int veteranAssessmentId);
    
    /**
     * Retrieves all the survey.
     * @return
     */
    List<SurveyInfo> getSurveyItemList();

    SurveyInfo update(SurveyInfo surveyInfo);
    
    /**
     * 
     * @param surveyId
     * @return
     */
    Survey findOne(int surveyId);

	void removeMeasureFromSurvey(Integer surveyId, Integer questionId);

	void createSurveyPage(Integer surveyId, Page surveyPage);

	void updateSurveyPages(Integer surveyId, List<SurveyPageInfo> surveyPageInfo);

	List<SurveyPageInfo> getSurveyPages(Integer surveyId, int pageNumber);

	SurveyInfo createSurvey(SurveyInfo survey);

    List<SurveyInfo> toSurveyInfo(List<Survey> surveyList);

    SurveyInfo findSurveyById(Integer surveyId);

    SurveyPageInfo getSurveyPage(Integer surveyId, Integer pageId);

    void removeSurveyPage(Integer surveyId, Integer pageId);

    List<SurveyDto> getSurveyListByNames(List<String> surveyNames);
}
