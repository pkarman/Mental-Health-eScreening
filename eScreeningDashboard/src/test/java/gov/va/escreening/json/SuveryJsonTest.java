package gov.va.escreening.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Created by pouncilt on 7/16/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/data-config.xml",
        "file:src/main/webapp/WEB-INF/spring/spring-security.xml",
        "file:src/main/webapp/WEB-INF/spring/business-config.xml",
        "/application-context-vistalink-test.xml"
})
public class SuveryJsonTest {
    @Value("classpath:json/survey.json")
    private Resource surveyJsonResource;
    @Value("classpath:json/survey-section.json")
    private Resource surveySectionJsonResource;
    @Value("classpath:json/survey-pretty-format.json")
    private Resource surveyPrettyFormatJsonResource;
    @Value("classpath:json/survey-section-pretty-format.json")
    private Resource surveySectionPrettyFormatJsonResource;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSurveyJsonPrettyFormatConversionToObject() throws Exception {
        SurveyInfo survey = mapper.readValue(surveyPrettyFormatJsonResource.getFile(), SurveyInfo.class);
        Assert.assertNotNull(survey);
    }

    @Test
    public void testSurveyJsonConversionToObject() throws Exception {
        SurveyInfo survey = mapper.readValue(surveyJsonResource.getFile(), SurveyInfo.class);
        Assert.assertNotNull(survey);
    }

    @Test
    public void testSurveySectionJsonPrettyConversionToObject() throws Exception {
        SurveySectionInfo surveySection = mapper.readValue(surveySectionPrettyFormatJsonResource.getFile(), SurveySectionInfo.class);
        Assert.assertNotNull(surveySection);
    }

    @Test
    public void testSurveySectionConversionToObject() throws Exception {
        SurveySectionInfo surveySection = mapper.readValue(surveySectionJsonResource.getFile(), SurveySectionInfo.class);
        Assert.assertNotNull(surveySection);
    }
}
