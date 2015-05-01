package gov.va.escreening.service;

import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.SurveySectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.*;

@Transactional
@Service
public class SurveySectionServiceImpl implements SurveySectionService {

    @Autowired
    private SurveyService surveyService;
    private SurveySectionRepository surveySectionRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    public void setBatteryRepository(
            SurveySectionRepository surveySectionRepository) {
        this.surveySectionRepository = surveySectionRepository;
    }

    public SurveySectionServiceImpl() {
        // Default constructor
    }

    @Override
    @Transactional
    public SurveySectionInfo create(SurveySectionInfo surveySectionInfo) {
        SurveySection surveySection = new SurveySection();
        copyProperties(surveySectionInfo, surveySection);

        surveySectionRepository.create(surveySection);

        surveySectionInfo.setSurveySectionId(surveySection.getSurveySectionId());

        return surveySectionInfo;
    }

    @Transactional(readOnly = true)
    @Override
    public SurveySectionInfo getSurveySectionItem(int surveySectionId) {

        SurveySection surveySection = surveySectionRepository.findOne(surveySectionId);

        if (surveySection == null) {
            return null;
        }

        SurveySectionInfo surveySectionInfo = convertToSurveySectionItem(surveySection);
        return surveySectionInfo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SurveySectionInfo> getSurveySectionList() {

        List<SurveySectionInfo> surveySectionInfoList = new ArrayList<SurveySectionInfo>();

        List<SurveySection> surveySectionList = surveySectionRepository.getSurveySectionList();

        for (SurveySection surveySection : surveySectionList) {
            surveySectionInfoList.add(convertToSurveySectionItem(surveySection));
        }

        return surveySectionInfoList;
    }

    @Override
    @Transactional
    public SurveySectionInfo update(SurveySectionInfo surveySectionInfo) {

        SurveySection surveySection = surveySectionRepository.findOne(surveySectionInfo.getSurveySectionId());
        copyProperties(surveySectionInfo, surveySection);

        List<Survey> surveySection__SurveyList = surveySection.getSurveyList();
        surveySection__SurveyList.clear();

        for (SurveyInfo s : surveySectionInfo.getSurveyInfoList()) {
            Survey survey = surveyService.findOne(s.getSurveyId());
            survey.setSurveySection(surveySection);
            survey.setDisplayOrderForSection(s.getDisplayOrderForSection());
            surveySection__SurveyList.add(survey);
        }

        surveySectionRepository.update(surveySection);

        return surveySectionInfo;
    }

    public SurveySectionInfo convertToSurveySectionItem(SurveySection surveySection) {
        if (surveySection == null) {
            return null;
        }

        SurveySectionInfo ssInfo = new SurveySectionInfo();
        copyProperties(surveySection, ssInfo);

        ssInfo.setSurveyInfoList(surveyService.toSurveyInfo(surveySection.getSurveyList()));
        return ssInfo;
    }

    @Override
    public void delete(Integer surveySectionId) {
        SurveySection surveySection = surveySectionRepository.findOne(surveySectionId);
        surveySectionRepository.delete(surveySection);
    }
}
