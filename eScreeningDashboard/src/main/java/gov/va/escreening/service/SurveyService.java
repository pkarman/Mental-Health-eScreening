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
     * Retrieves all non auto assigned surveys.
     * @return
     */
    List<SurveyDto> getAssignableSurveys();

    /**
     * Retrieves auto assigned
     * @return
     */
    List<SurveyDto> getRequiredSurveys();

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
     * Retrieves all the survey.
     * @return
     */
    List<SurveyDto> getSurveyList();
    
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

	List<SurveyPageInfo> getSurveyPages(Integer surveyId);

	SurveyInfo createSurvey(SurveyInfo survey);

    List<SurveyInfo> toSurveyInfo(List<Survey> surveyList);

    SurveyInfo findSurveyById(Integer surveyId);

    SurveyPageInfo getSurveyPage(Integer surveyId, Integer pageId);

    void removeSurveyPage(Integer surveyId, Integer pageId);
}
