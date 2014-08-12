package gov.va.escreening.service;

import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.SurveySectionRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public SurveySectionInfo create(SurveySectionInfo surveySectionInfo) {
		SurveySection surveySection = new SurveySection();

		surveySection.setName(surveySectionInfo.getName());
		surveySection.setDescription(surveySectionInfo.getDescription());
		surveySection.setDisplayOrder(surveySectionInfo.getDisplayOrder());

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
	public SurveySectionInfo update(SurveySectionInfo surveySectionInfo) {

		SurveySection surveySection = surveySectionRepository.findOne(surveySectionInfo.getSurveySectionId());

		surveySection.setName(surveySectionInfo.getName());
		surveySection.setDescription(surveySectionInfo.getDescription());
		surveySection.setDisplayOrder(surveySectionInfo.getDisplayOrder());
		surveySection = surveySectionRepository.update(surveySection);
		SurveySectionInfo result = convertToSurveySectionItem(surveySection);
		surveySectionRepository.commit();
		return result;
	}

	public SurveySectionInfo convertToSurveySectionItem(SurveySection surveySection) {

		if (surveySection == null) {
			return null;
		}

		SurveySectionInfo surveySectionInfo = new SurveySectionInfo();
		surveySectionInfo.setSurveySectionId(surveySection.getSurveySectionId());
		surveySectionInfo.setName(surveySection.getName());
		surveySectionInfo.setDisplayOrder(surveySection.getDisplayOrder());
		surveySectionInfo.setDescription(surveySection.getDescription());
		surveySectionInfo.setDateCreated(surveySection.getDateCreated());

		List<SurveyInfo> surveyInfos = new ArrayList<SurveyInfo>();
		for (Survey survey : surveySection.getSurveyList()) {
			surveyInfos.add(surveyService.convertToSurveyItem(survey));
		}
		surveySectionInfo.setSurveyInfoList(surveyInfos);
		return surveySectionInfo;
	}

	@Override
	public void delete(Integer surveySectionId) {
		SurveySection surveySection = surveySectionRepository.findOne(surveySectionId);
        surveySectionRepository.delete(surveySection);
	}
}
