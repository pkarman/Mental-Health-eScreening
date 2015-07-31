package gov.va.escreening.assessments.test;

import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentSurvey;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.TemplateProcessorException;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.templateprocessor.TemplateProcessorService;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class TemplateServiceTest extends AssessmentTestBase {
	private static Logger logger = Logger.getLogger(TemplateServiceTest.class);
	@Resource
	TemplateProcessorService templateSvc;
	
	@Resource
	TemplateService templateService;

	@Resource
	TemplateRepository templateRepo;

	@Resource
	BatteryRepository batteryRepo;

	@Resource
	SurveyRepository surveyRepo;

	@Test
	public void testPromisEmotionModue() throws IOException // throws
															// IOException
	{
		VeteranAssessment assessment = createTestAssessment();

		SurveyMeasureResponse res = createResponse(101, 1011, true,
				assessment.getVeteranAssessmentId(), 6);
		assessment
				.setSurveyMeasureResponseList(new ArrayList<SurveyMeasureResponse>());
		assessment.getSurveyMeasureResponseList().add(res);

		res = createResponse(102, 1022, true,
				assessment.getVeteranAssessmentId(), 6);
		assessment.getSurveyMeasureResponseList().add(res);

		res = createResponse(103, 1031, true,
				assessment.getVeteranAssessmentId(), 6);
		//assessment.getSurveyMeasureResponseList().add(res);

		res = createResponse(104, 1041, true,
				assessment.getVeteranAssessmentId(), 6);
		assessment.getSurveyMeasureResponseList().add(res);

		assessment.setBattery(batteryRepo.findOne(5));

		VeteranAssessmentSurvey vas = new VeteranAssessmentSurvey(assessment,
				surveyRepo.findOne(6));
		List<VeteranAssessmentSurvey> list = new ArrayList<VeteranAssessmentSurvey>();
		list.add(vas);
		assessment.setVeteranAssessmentSurveyList(list);

		vetAssessmentRepo.update(assessment);
		vetAssessmentRepo.commit();

		Template template = templateRepo.findOne(7);

		FileReader fr = null;
		try {
			fr = new FileReader(
					"src/test/resources/template/CN_PROMISEMOTION.ftl");

			char[] buffer = new char[2048];
			fr.read(buffer);
			template.setTemplateFile(new String(buffer).trim());
			templateRepo.update(template);
			templateRepo.commit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (fr != null)
				fr.close();
		}

		try {
			logger.debug(templateSvc.generateCPRSNote(
					assessment.getVeteranAssessmentId(), ViewType.TEXT));
		} catch (TemplateProcessorException | IllegalSystemStateException e) {

			e.printStackTrace();
			Assert.fail("Exception occurred");
		}

	}
}
