package gov.va.escreening.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.dto.report.DataPointDTO;
import gov.va.escreening.dto.report.ImageInputDTO;
import gov.va.escreening.dto.report.IntervalDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @Test
    public void test() throws Exception {
        ImageInputDTO imageInputDTO = new ImageInputDTO();

        List<IntervalDTO> intervalDTOs = new ArrayList<>();

        IntervalDTO intervalDTO = new IntervalDTO();
        intervalDTO.setScore(0);
        intervalDTO.setScoreMeaning("None");

        intervalDTOs.add(intervalDTO);

        intervalDTO = new IntervalDTO();
        intervalDTO.setScore(5);
        intervalDTO.setScoreMeaning("Mild");
        intervalDTOs.add(intervalDTO);

        imageInputDTO.setIntervals(intervalDTOs);

        List<DataPointDTO> dataPointDTOs = new ArrayList<>();

        imageInputDTO.setDataPoints(dataPointDTOs);

        DataPointDTO dataPointDTO = new DataPointDTO();

        dataPointDTO.setScore(20);
        Date scoreDate = new Date();
        scoreDate.setMonth(1);
        scoreDate.setDate(1);
        dataPointDTO.setScoreDate(scoreDate );

        dataPointDTOs.add(dataPointDTO);

        dataPointDTO = new DataPointDTO();

        dataPointDTO.setScore(5);
        scoreDate = new Date();
        scoreDate.setMonth(2);
        scoreDate.setDate(1);
        dataPointDTO.setScoreDate(scoreDate) ;

        dataPointDTOs.add(dataPointDTO);

        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        System.out.println((om).writeValueAsString(imageInputDTO));

    }
}
