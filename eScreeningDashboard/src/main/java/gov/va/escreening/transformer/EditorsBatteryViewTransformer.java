package gov.va.escreening.transformer;

import gov.va.escreening.dto.editors.BatteryInfo;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.BatterySurvey;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveySection;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by pouncilt on 8/2/14.
 */
public class EditorsBatteryViewTransformer {

    public static BatteryInfo transformBatterySurveys(List<BatterySurvey> batterySurveys) {
        BatteryInfo batteryInfo = null;

        if(batterySurveys!= null && batterySurveys.size() > 0) {
            batteryInfo = transformBattery(batterySurveys.get(0).getBattery());

            for (BatterySurvey batterySurvey : batterySurveys) {
                batteryInfo.getSurveys().add(transformBatterySurveyForEditorsView(batterySurvey));
            }
        }

        return batteryInfo;
    }

    private static SurveyInfo transformBatterySurveyForEditorsView(BatterySurvey batterySurvey) {
        SurveyInfo surveyInfo = new SurveyInfo();

        if(batterySurvey != null) {
            surveyInfo = transformSurvey(batterySurvey.getSurvey());
        }

        return surveyInfo;
    }

    private static BatteryInfo transformBattery(Battery battery) {
        BatteryInfo batteryInfo = null;

        if(battery != null) {
            batteryInfo = new BatteryInfo();
            BeanUtils.copyProperties(battery, batteryInfo);
        }

        return batteryInfo;
    }

    private static SurveyInfo transformSurvey(Survey survey) {
        SurveyInfo surveyInfo = null;

        if(survey != null) {
            surveyInfo = new SurveyInfo();
            BeanUtils.copyProperties(survey, surveyInfo);
            surveyInfo.setSurveySectionInfo(transformSurveySection(survey.getSurveySection()));
        }

        return surveyInfo;
    }

    private static SurveySectionInfo transformSurveySection(SurveySection surveySection) {
        SurveySectionInfo surveySectionInfo = new SurveySectionInfo();

        if (surveySection != null) {
            BeanUtils.copyProperties(surveySection, surveySectionInfo);
        }

        return surveySectionInfo;
    }
}