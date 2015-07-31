package gov.va.escreening.delegate;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.BatteryInfo;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveyPageInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.service.BatteryService;
import gov.va.escreening.service.BatterySurveyService;
import gov.va.escreening.service.MeasureService;
import gov.va.escreening.service.SurveySectionService;
import gov.va.escreening.service.SurveyService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EditorsDelegateImpl implements EditorsDelegate {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory
            .getLogger(EditorsDelegateImpl.class);

    private final BatteryService batteryService;
    private final SurveyService surveyService;
    private final SurveySectionService surveySectionService;
    private final MeasureService measureService;

    @Autowired
    EditorsDelegateImpl(BatterySurveyService batterySurveyService,
            BatteryService batteryService,
            SurveySectionService surveySectionService,
            SurveyService surveyService, MeasureService measureService) {
        this.batteryService = checkNotNull(batteryService);
        this.surveySectionService = checkNotNull(surveySectionService);
        this.surveyService = checkNotNull(surveyService);
        this.measureService = checkNotNull(measureService);
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
        // only insert a new survey section
        surveySectionService.create(surveySectionInfo);
        // now that survey section is added to the db. ask to update, which will
        // also take care of surveys
        SurveySectionInfo surveySectionInfo1 = updateSection(surveySectionInfo);
        return surveySectionInfo1.getSurveySectionId();

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
        return surveySectionService.update(surveySectionInfo);
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
    public SurveyInfo findSurvey(Integer surveyId) {
        return surveyService.findSurveyById(surveyId);
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
    public List<SurveyPageInfo> getSurveyPages(Integer surveyId, int pageNumber) {
        return surveyService.getSurveyPages(surveyId, pageNumber);
    }

    @Override
    public SurveyInfo createSurvey(SurveyInfo survey) {

        return surveyService.createSurvey(survey);
    }

    @Override
    public Measure findMeasure(Integer measureId) {
        return measureService.findMeasure(measureId);
    }

    @Override
    public SurveyPageInfo getSurveyPage(Integer surveyId, Integer pageId) {
        return surveyService.getSurveyPage(surveyId, pageId);
    }

    @Override
    public void deleteSurveyPage(Integer surveyId, Integer pageId) {
        surveyService.removeSurveyPage(surveyId, pageId);
    }
}
