package gov.va.escreening.service;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.QuestionInfo;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveyPageInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.entity.*;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyPageRepository;
import gov.va.escreening.repository.SurveyRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gov.va.escreening.repository.SurveySectionRepository;
import gov.va.escreening.transformer.EditorsQuestionViewTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
@Service
public class SurveyServiceImpl implements SurveyService {

	private static final Logger logger = LoggerFactory.getLogger(SurveyServiceImpl.class);

	private SurveyRepository surveyRepository;
	
	@Autowired
	private SurveySectionRepository surveySectionRepository;

	@Autowired
	private MeasureRepository measureRepository;
	
	@Autowired
	private SurveyPageRepository surveyPageRepository;
	
	@Autowired
	private AssessmentVariableRepository assessmentVariableRepository;
	
	@Autowired
	public void setSurveyRepository(SurveyRepository surveyRepository) {
		this.surveyRepository = surveyRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SurveyDto> getAssignableSurveys() {
		logger.debug("getAssignableSurveys()");

		List<Survey> surveys = surveyRepository.getAssignableSurveys();

		// create adapter object for view
		List<SurveyDto> surveyDtoList = new ArrayList<SurveyDto>();
		for (Survey survey : surveys) {
			surveyDtoList.add(new SurveyDto(survey));
		}

		return surveyDtoList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SurveyDto> getRequiredSurveys() {
		logger.debug("getRequiredSurveys()");

		List<Survey> surveys = surveyRepository.getRequiredSurveys();

		List<SurveyDto> surveyDtoList = new ArrayList<SurveyDto>();
		for (Survey survey : surveys) {
			surveyDtoList.add(new SurveyDto(survey));
		}

		return surveyDtoList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Survey> findForVeteranAssessmentId(int veteranAssessmentId) {

		List<Survey> surveyList = surveyRepository.findForVeteranAssessmentId(veteranAssessmentId);

		// We need to iterate through the collections until we can get eager fetch working using JQL or find a
		// workaround for it.
		if (surveyList != null) {
			for (Survey survey : surveyList) {
				// logger.debug(survey.getName());

				List<SurveyPage> spLst = survey.getSurveyPageList();
				if (spLst != null) {
					for (SurveyPage surveyPage : spLst) {
						// logger.debug(surveyPage.getTitle());

						List<Measure> mLst = surveyPage != null ? surveyPage.getMeasures() : null;
						if (mLst != null) {
							for (Measure measure : mLst) {
								// logger.debug(measure.getMeasureText());

								List<MeasureAnswer> maLst = measure != null ? measure.getMeasureAnswerList() : null;
								if (maLst != null) {
									for (MeasureAnswer measureAnswer : maLst) {
										// logger.debug(measureAnswer.getAnswerText());
									}
								}
							}
						}
					}
				}
			}
		}

		return surveyList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SurveyDto> getSurveyListForVeteranAssessment(
			int veteranAssessmentId) {
		logger.debug("getVeteranAssessmentSurveys()");

		List<Survey> surveys = surveyRepository.findForVeteranAssessmentId(veteranAssessmentId);

		List<SurveyDto> surveyDtoList = new ArrayList<SurveyDto>();
		for (Survey survey : surveys) {
			surveyDtoList.add(new SurveyDto(survey));
		}

		return surveyDtoList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SurveyDto> getSurveyList() {
		logger.debug("getSurveyList()");

		List<Survey> surveys = surveyRepository.getSurveyList();

		List<SurveyDto> surveyDtoList = new ArrayList<SurveyDto>();
		for (Survey survey : surveys) {
			surveyDtoList.add(new SurveyDto(survey));
		}

		return surveyDtoList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SurveyInfo> getSurveyItemList() {

		List<SurveyInfo> surveyInfoList = new ArrayList<SurveyInfo>();

		List<Survey> surveys = surveyRepository.getSurveyList();

		for (Survey survey : surveys) {
			surveyInfoList.add(convertToSurveyItem(survey));
		}

		return surveyInfoList;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public SurveyInfo update(SurveyInfo surveyInfo) {
		Survey survey = surveyRepository.findOne(surveyInfo.getSurveyId());
		SurveySection surveySection = surveySectionRepository.findOne(surveyInfo.getSurveySectionInfo().getSurveySectionId());


        BeanUtils.copyProperties(surveyInfo, survey);
		survey.setSurveySection(surveySection);

		survey = surveyRepository.update(survey);
		return convertToSurveyItem(survey);
	}

	@Override
	public SurveyInfo convertToSurveyItem(Survey survey) {

		if (survey == null) {
			return null;
		}

		SurveyInfo surveyInfo = new SurveyInfo();
		BeanUtils.copyProperties(survey, surveyInfo);

		if (survey.getSurveySection() != null) {
			SurveySectionInfo surveySectionInfo = new SurveySectionInfo();

			surveySectionInfo.setSurveySectionId(survey.getSurveySection().getSurveySectionId());
            surveySectionInfo.setDescription(survey.getSurveySection().getDescription());
			surveySectionInfo.setName(survey.getSurveySection().getName());
			surveySectionInfo.setDisplayOrder(survey.getSurveySection().getDisplayOrder());
            surveySectionInfo.setDateCreated(survey.getSurveySection().getDateCreated());

			surveyInfo.setSurveySectionInfo(surveySectionInfo);
		}

		return surveyInfo;
	}

	@Override
	public Survey findOne(int surveyId) {

		return surveyRepository.findOne(surveyId);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void removeMeasureFromSurvey(Integer surveyId, Integer questionId) {
		
		Measure measure = measureRepository.findOne(questionId);
		
		if (measure!=null)
		{
			if (measure.getParent()!=null)
			{
				measure.setParent(null);
				measureRepository.update(measure);
				measureRepository.commit();
			}
			else
			{
				SurveyPage sp = surveyPageRepository.getSurveyPageByMeasureId(questionId);
				
				if (sp!=null)
				{
					sp.getMeasures().remove(measure);
					surveyPageRepository.update(sp);
					surveyPageRepository.commit();
				}
				
			}
		}
		
	}

	@Override
	public void createSurveyPage(Integer surveyId, Page page) {
		Survey survey = surveyRepository.findOne(surveyId);
		
		SurveyPage surveyPage = new SurveyPage();
		surveyPage.setPageNumber(page.getPageNumber());
		surveyPage.setDescription(page.getDescription());
		surveyPage.setTitle(page.getPageTitle());
		surveyPage.setSurvey(survey);
		
		surveyPageRepository.create(surveyPage);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void updateSurveyPages(Integer surveyId,
			List<SurveyPageInfo> surveyPageInfos) {
		
		
		Survey survey = surveyRepository.findOne(surveyId);
		
		List<SurveyPage> surveyPageList = new ArrayList<SurveyPage>();
		
		String surveyPageTitle = survey.getSurveySection().getName();
		
		for(SurveyPageInfo surveyPageInfo : surveyPageInfos)
		{
			
			SurveyPage surveyPage = null;
			
			if (surveyPageInfo.getId() == null)
			{
				surveyPage = new SurveyPage();
			}
			else
			{
				surveyPage = surveyPageRepository.findOne(surveyPageInfo.getId());
			}
			
			surveyPage.setPageNumber(surveyPageInfo.getPageNumber());
			surveyPage.setDescription(surveyPageInfo.getDescription());
			surveyPage.setTitle(surveyPageTitle);
			surveyPage.setSurveyPageId(surveyPageInfo.getId());
			
			if (surveyPageInfo.getDateCreated()==null)
			{
				surveyPage.setDateCreated(new Date());
			}
			else
			{
				surveyPage.setDateCreated(surveyPageInfo.getDateCreated());
			}
			surveyPage.setSurvey(survey);
			
			List<Measure> measures = new ArrayList<Measure>();
			surveyPage.setMeasures(measures);
			
			for(QuestionInfo questionInfo : surveyPageInfo.getQuestions())
			{
				Integer measureId = questionInfo.getId();
				if (measureId != null && measureId >-1)
				{
					measureRepository.updateMeasure(EditorsQuestionViewTransformer.transformQuestionInfo(questionInfo));
					measures.add(measureRepository.findOne(questionInfo.getId()));			
				}
				else
				{
					gov.va.escreening.dto.ae.Measure measureDTO = measureRepository.createMeasure(EditorsQuestionViewTransformer.transformQuestionInfo(questionInfo));	
					
					Measure measure = measureRepository.findOne(measureDTO.getMeasureId());
					
					AssessmentVariable av = new AssessmentVariable();
					av.setMeasure(measure);
					av.setAssessmentVariableTypeId(new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE));
					av.setDisplayName(measure.getMeasureText());
					assessmentVariableRepository.create(av);
					List<AssessmentVariable> assessmentVariableList = new ArrayList<AssessmentVariable>();
					assessmentVariableList.add(av);
					measure.setAssessmentVariableList(assessmentVariableList);
					measureRepository.update(measure);
					measures.add(measure);
					
					// update questionInfo's id with measure id
					questionInfo.setId(measure.getMeasureId());
				}
			}
			
			if (surveyPageInfo.getId() == null)
			{
				surveyPageRepository.create(surveyPage);
			}
			else
				surveyPageRepository.update(surveyPage);
			
			surveyPageList.add(surveyPage);
		}
		
		System.out.println("aaaaaab");
		survey.setSurveyPageList(surveyPageList);
		surveyRepository.update(survey);
	}

	@Override
	public List<SurveyPageInfo> getSurveyPages(Integer surveyId) {
		Survey survey = surveyRepository.findOne(surveyId);
		List<SurveyPage> surveyPages = survey.getSurveyPageList();
		
		List<SurveyPageInfo> surveyPageInfos = new ArrayList<SurveyPageInfo>();
		for(SurveyPage surveyPage : surveyPages)
		{
			SurveyPageInfo spi = new SurveyPageInfo();
			spi.setId(surveyPage.getSurveyPageId());
		    spi.setDescription(surveyPage.getDescription());
		    spi.setPageNumber(surveyPage.getPageNumber());
		    spi.setTitle(surveyPage.getTitle());
		    spi.setDateCreated(surveyPage.getDateCreated());
		    
		    spi.setQuestions(new ArrayList<QuestionInfo>());
		    for(Measure measure : surveyPage.getMeasures())
		    {
		    	if (measure==null)
		    	{
		    		continue;
		    	}
		    	spi.getQuestions().add(EditorsQuestionViewTransformer.transformQuestion(new gov.va.escreening.dto.ae.Measure(measure, null, null)));
		    }
		    surveyPageInfos.add(spi);
		    
		}
		return surveyPageInfos;
	}

	@Override
	public SurveyInfo createSurvey(SurveyInfo surveyInfo) {
		Survey survey = new Survey();
		
		BeanUtils.copyProperties(surveyInfo, survey);
		
		SurveySection surveySection = surveySectionRepository.findOne(surveyInfo.getSurveySectionInfo().getSurveySectionId());
		
		survey.setSurveySection(surveySection);
		//surveySection.getSurveyList().add(survey);
		surveyRepository.create(survey);
		//surveySectionRepository.update(surveySection);
				
		return convertToSurveyItem(survey);
	}

}
