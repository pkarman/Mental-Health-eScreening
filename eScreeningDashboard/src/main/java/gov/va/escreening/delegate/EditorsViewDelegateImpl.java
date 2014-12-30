package gov.va.escreening.delegate;

import gov.va.escreening.dto.SearchDTO;
import gov.va.escreening.dto.SearchType;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.BatteryInfo;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveyPageInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.entity.BatterySurvey;
import gov.va.escreening.service.BatteryService;
import gov.va.escreening.service.BatterySurveyService;
import gov.va.escreening.service.SurveySectionService;
import gov.va.escreening.service.SurveyService;

import java.util.ArrayList;
import java.util.List;

import gov.va.escreening.transformer.EditorsBatteryViewTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public  class EditorsViewDelegateImpl implements EditorsViewDelegate {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(EditorsViewDelegateImpl.class);

    private BatterySurveyService batterySurveyService;
    private BatteryService batteryService;

    private SurveyService surveyService;

    private SurveySectionService surveySectionService;


    @Autowired
    public void setBatterySurveyService(BatterySurveyService batterySurveyService) {
        this.batterySurveyService = batterySurveyService;
    }
    @Autowired
    public void setBatteryService(BatteryService batteryService) {
        this.batteryService = batteryService;
    }
    @Autowired
    public void setSurveySectionServiceService(SurveySectionService surveySectionService) {
        this.surveySectionService = surveySectionService;
    }
    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    public EditorsViewDelegateImpl() {

    }

    @Override
    public BatteryInfo createBattery(BatteryInfo batteryInfo) {
        return batteryService.create(batteryInfo);
    }

    @Override
    public BatteryInfo getBattery(int batteryId) {
        return batteryService.getBattery(batteryId);
    }

    @Override
    public List<BatteryInfo> getBatteries() {
        return batteryService.getBatteryItemList();
    }

    @Override
    public void updateBattery(BatteryInfo batteryInfo) {
        batteryService.update(batteryInfo);
    }


    @Override
    public Integer createSection(SurveySectionInfo surveySectionInfo) {
        surveySectionService.create(surveySectionInfo);
        return surveySectionInfo.getSurveySectionId();

    }

    @Override
    public SurveySectionInfo getSection(Integer sectionId) {
        // Code for service classes
        return surveySectionService.getSurveySectionItem(sectionId);
    }

    @Override
    public List<SurveySectionInfo> getSections() {
        // Code for service classes
        return surveySectionService.getSurveySectionList();
    }

    @Override
    public SurveySectionInfo updateSection(SurveySectionInfo surveySectionInfo) {
        List<SurveyInfo> updatedSurveyInfoList = new ArrayList<SurveyInfo>();
        SurveySectionInfo updatedSurveySectionInfo;

        for(SurveyInfo surveyInfo : surveySectionInfo.getSurveyInfoList()){
            if(surveyInfo.getSurveySectionInfo().getSurveySectionId() != surveySectionInfo.getSurveySectionId()){
                surveyInfo.getSurveySectionInfo().setSurveySectionId(surveySectionInfo.getSurveySectionId());
            }
            surveyInfo = surveyService.update(surveyInfo);
            updatedSurveyInfoList.add(surveyInfo);
        }
        updatedSurveySectionInfo = surveySectionService.update(surveySectionInfo);
        return updatedSurveySectionInfo;
    }

    @Override
    public void deleteSection(Integer surveySectionId) {
        surveySectionService.delete(surveySectionId);
    }


    @Override
    public void deleteBattery(Integer batteryId) {
        batteryService.delete(batteryId);
    }

    @Override
    public List<SurveyInfo> getSurveys() {
        return surveyService.getSurveyItemList();
    }

    @Override
    public SurveyInfo updateSurvey(SurveyInfo surveyInfo) {
        return surveyService.update(surveyInfo);
    };

    @Override
    public void removeQuestionFromSurvey(Integer surveyId, Integer questionId) {
        surveyService.removeMeasureFromSurvey(surveyId, questionId);
    }
    @Override
    public void createSurveyPage(Integer surveyId, Page surveyPage) {
        surveyService.createSurveyPage(surveyId, surveyPage);
    }
    @Override
    public void updateSurveyPages(Integer surveyId,
                                  List<SurveyPageInfo> surveyPageInfo) {
        surveyService.updateSurveyPages(surveyId, surveyPageInfo);


    }
    @Override
    public List<SurveyPageInfo> getSurveyPages(Integer surveyId) {
        return surveyService.getSurveyPages(surveyId);
    }
    @Override
    public SurveyInfo createSurvey(SurveyInfo survey) {

        return surveyService.createSurvey(survey);
    }
}

