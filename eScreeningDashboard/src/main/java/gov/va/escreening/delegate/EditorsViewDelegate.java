package gov.va.escreening.delegate;

import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.BatteryInfo;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;

import java.util.List;

public interface EditorsViewDelegate {

    BatteryInfo createBattery(BatteryInfo batteryInfo);
    BatteryInfo getBattery(int batteryId);
    List<BatteryInfo> getBatteries();
    void updateBattery(BatteryInfo batteryInfo);
    void deleteBattery(Integer batteryId);
    
    List<SurveyInfo> getSurveys();
    SurveyInfo updateSurvey(SurveyInfo surveyInfo);

    
    Integer createSection(SurveySectionInfo surveySection);
   	SurveySectionInfo getSection(Integer sectionId);
   	List<SurveySectionInfo> getSections();
    SurveySectionInfo updateSection(SurveySectionInfo surveySectionInfo);
   	void deleteSection(Integer surveySectionId);
   	
   	void removeQuestionFromSurvey(Integer surveyId, Integer questionId);
	void createSurveyPage(Integer surveyId, Page surveyPage);
}
