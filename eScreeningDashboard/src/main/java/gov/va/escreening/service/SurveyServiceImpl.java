package gov.va.escreening.service;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.domain.MeasureTypeEnum;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.QuestionInfo;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveyPageInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.entity.*;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.EscreeningDataValidationException;
import gov.va.escreening.repository.*;
import gov.va.escreening.transformer.EditorsQuestionViewTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Transactional(readOnly = true)
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
    private ClinicalReminderSurveyRepository clinicalReminderSurveyRepo;
    
    @Autowired
    private ClinicalReminderRepository clinicalReminderRepo;
    
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


        List<Survey> surveys = surveyRepository.getSurveyList();
        List<SurveyInfo> surveyInfoList = toSurveyInfo(surveys);

        return surveyInfoList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SurveyInfo update(SurveyInfo surveyInfo) {
        
        if(surveyInfo.getSurveySectionInfo() == null || 
                surveyInfo.getSurveySectionInfo().getSurveySectionId() == null){
            ErrorBuilder.throwing(EscreeningDataValidationException.class)
            .toAdmin("surveyInfo passed in with null section ID. Debug UI.")
            .toUser("Invalid request. Please contact support")
            .throwIt();
        }
        
        Survey survey = surveyRepository.findOne(surveyInfo.getSurveyId());
        SurveySection newSurveySection = surveySectionRepository.findOne(surveyInfo.getSurveySectionInfo().getSurveySectionId());
            
        boolean isNewSection = !survey.getSurveySection().getSurveySectionId().equals(newSurveySection.getSurveySectionId());
            
        if(isNewSection && surveyInfo.getDisplayOrderForSection() == null){
            //the module's section has changed and no order was set so set it the the next larger index
            surveyInfo.setDisplayOrderForSection(newSurveySection.getSurveyList().size()+1);
        }
            
        // copy any changed properties from incoming surveyInfo to the data for database 'survey'
        copyProperties(surveyInfo, survey);

        // and now make sure that surveyInfo's survey Section is also reflected back to the survey
        survey.setSurveySection(newSurveySection);

        surveyRepository.update(survey);
        
        Integer clinicalReminderId = surveyInfo.getClinicalReminderId();
        clinicalReminderSurveyRepo.removeSurveyMapping(surveyInfo.getSurveyId());
        
        if(clinicalReminderId!=null && clinicalReminderId >0)
        {
         clinicalReminderSurveyRepo.createClinicalReminderSurvey(clinicalReminderId, surveyInfo.getSurveyId());;
        }
        return surveyInfo;
    }

    @Override
    public Survey findOne(int surveyId) {
        return surveyRepository.findOne(surveyId);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void removeMeasureFromSurvey(Integer surveyId, Integer questionId) {

        Measure measure = measureRepository.findOne(questionId);

        if (measure != null) {
            if (measure.getParent() != null) {
                measure.setParent(null);
                measureRepository.update(measure);
                measureRepository.commit();
            } else {
                SurveyPage sp = surveyPageRepository.getSurveyPageByMeasureId(questionId);

                if (sp != null) {
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateSurveyPages(Integer surveyId,
                                  List<SurveyPageInfo> surveyPageInfos) {


        Survey survey = surveyRepository.findOne(surveyId);

        List<SurveyPage> surveyPageList = new ArrayList<SurveyPage>();

        String surveyPageTitle = survey.getSurveySection().getName();

        for (SurveyPageInfo surveyPageInfo : surveyPageInfos) {

            SurveyPage surveyPage = null;

            if (surveyPageInfo.getSurveyPageId() == null) {
                surveyPage = new SurveyPage();
            } else {
                surveyPage = surveyPageRepository.findOne(surveyPageInfo.getSurveyPageId());
            }

            surveyPage.setPageNumber(surveyPageInfo.getPageNumber());
            surveyPage.setDescription(surveyPageInfo.getDescription());
            surveyPage.setTitle(surveyPageTitle);
            surveyPage.setSurveyPageId(surveyPageInfo.getSurveyPageId());

            if (surveyPageInfo.getDateCreated() == null) {
                surveyPage.setDateCreated(new Date());
            } else {
                surveyPage.setDateCreated(surveyPageInfo.getDateCreated());
            }
            surveyPage.setSurvey(survey);

            List<Measure> measures = new ArrayList<Measure>();
            surveyPage.setMeasures(measures);

            for (final QuestionInfo questionInfo : surveyPageInfo.getQuestions()) {
                Integer measureId = questionInfo.getId();
                if (measureId != null && measureId > -1) {
                    measureRepository.updateMeasure(EditorsQuestionViewTransformer.transformQuestionInfo(questionInfo));
                    measures.add(measureRepository.findOne(questionInfo.getId()));
                } else {
                    gov.va.escreening.dto.ae.Measure measureDTO = measureRepository.createMeasure(EditorsQuestionViewTransformer.transformQuestionInfo(questionInfo));
                    measures.add(measureRepository.findOne(measureDTO.getMeasureId()));
                    
                    // update questionInfo's id with measure id
                    questionInfo.setId(measureDTO.getMeasureId());
                }
            }

            if (surveyPageInfo.getSurveyPageId() == null) {
                surveyPageRepository.create(surveyPage);
                surveyPageInfo.setSurveyPageId(surveyPage.getSurveyPageId());
            } else {
                surveyPageRepository.update(surveyPage);
            }

            surveyPageList.add(surveyPage);
        }

        survey.setSurveyPageList(surveyPageList);
        surveyRepository.update(survey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyPageInfo> getSurveyPages(Integer surveyId, int pageNumber) {
        Survey survey = surveyRepository.findOne(surveyId);
        List<SurveyPage> surveyPages = survey.getSurveyPageList();

        List<SurveyPageInfo> surveyPageInfos = new ArrayList<SurveyPageInfo>();
        for (SurveyPage surveyPage : surveyPages) {

            if (pageNumber==-1 || surveyPage.getPageNumber()==pageNumber) {
                SurveyPageInfo spi = new SurveyPageInfo();
                BeanUtils.copyProperties(surveyPage, spi);

                spi.setQuestions(new ArrayList<QuestionInfo>());
                for (Measure measure : surveyPage.getMeasures()) {
                    spi.getQuestions().add(EditorsQuestionViewTransformer.transformQuestion(new gov.va.escreening.dto.ae.Measure(measure)));
                }
                surveyPageInfos.add(spi);
            }
        }
        return surveyPageInfos;
    }

    @Override
    @Transactional
    public SurveyInfo createSurvey(SurveyInfo surveyInfo) {
        SurveySection surveySection = surveySectionRepository.findOne(surveyInfo.getSurveySectionInfo().getSurveySectionId());
        
        //we have to always set this to the last index of the section
        surveyInfo.setDisplayOrderForSection(surveySection.getSurveyList().size()+1);
        
        Survey survey = new Survey();
        BeanUtils.copyProperties(surveyInfo, survey);
        survey.setSurveySection(surveySection);
        

        surveyRepository.create(survey);
        
        if(surveyInfo.getClinicalReminderId() != null && surveyInfo.getClinicalReminderId() > 0)
        {
        	ClinicalReminderSurvey cr = new ClinicalReminderSurvey();
        	ClinicalReminder reminder = clinicalReminderRepo.findOne(surveyInfo.getClinicalReminderId());
        	cr.setClinicalReminder(reminder);
        	cr.setSurvey(survey);
        	clinicalReminderSurveyRepo.create(cr);
        	
        }
        return toSurveyInfo(Arrays.asList(survey)).iterator().next();
    }

    @Override
    public List<SurveyInfo> toSurveyInfo(List<Survey> surveyList) {

        Function<Survey, SurveyInfo> transformerFun = new Function<Survey, SurveyInfo>() {
            @Nullable
            @Override
            public SurveyInfo apply(Survey survey) {
                SurveyInfo si = new SurveyInfo();
                BeanUtils.copyProperties(survey, si);

                SurveySectionInfo ssInfo = new SurveySectionInfo();
                copyProperties(survey.getSurveySection(), ssInfo);

                si.setSurveySectionInfo(ssInfo);
                
                if(survey.getClinicalReminderSurveyList()!=null && !survey.getClinicalReminderSurveyList().isEmpty())
                {
                	si.setClinicalReminderId(survey.getClinicalReminderSurveyList().get(0).getClinicalReminder().getClinicalReminderId());
                }
                return si;
            }
        };

        return Lists.newArrayList(Collections2.transform(surveyList, transformerFun));
    }

    @Override
    public SurveyInfo findSurveyById(Integer surveyId) {
        Survey survey = surveyRepository.findOne(surveyId);
        return toSurveyInfo(Arrays.asList(survey)).iterator().next();
    }

    @Override
    public SurveyPageInfo getSurveyPage(Integer surveyId, Integer pageId) {
        Survey survey = surveyRepository.findOne(surveyId);
        List<SurveyPage> surveyPages = survey.getSurveyPageList();

        for (SurveyPage surveyPage : surveyPages) {
            if (surveyPage.getSurveyPageId().equals(pageId)) {
                SurveyPageInfo spi = new SurveyPageInfo();
                BeanUtils.copyProperties(surveyPage, spi);

                spi.setQuestions(new ArrayList<QuestionInfo>());
                for (Measure measure : surveyPage.getMeasures()) {
                    spi.getQuestions().add(EditorsQuestionViewTransformer.transformQuestion(new gov.va.escreening.dto.ae.Measure(measure)));
                }

                return spi;

            }
        }
        return null;
    }

    @Override
    @Transactional
    public void removeSurveyPage(Integer surveyId, Integer pageId) {
        surveyPageRepository.deleteById(pageId);
    }
}
