package gov.va.escreening.assessments.test;

import gov.va.escreening.service.AssessmentVariableService;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Table;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class AvSyncMeasureTest extends AssessmentTestBase {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(type = AssessmentVariableService.class)
	AssessmentVariableService avs;

	@Test
	public void testAssessmentVariablesForSurveyId1() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(1);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId2() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(2);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId3() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(3);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId4() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(4);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId5() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(5);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId6() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(6);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId7() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(7);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId8() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(8);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId9() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(9);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId10() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(10);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId11() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(11);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId12() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(12);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId13() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(13);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId14() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(14);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId15() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(15);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId16() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(16);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId17() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(17);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId18() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(18);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId19() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(19);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId20() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(20);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId21() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(21);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId22() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(22);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId23() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(23);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId24() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(24);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId25() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(25);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId26() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(26);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId27() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(27);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId28() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(28);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId29() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(29);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId30() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(30);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId31() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(31);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId32() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(32);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId33() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(33);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId34() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(34);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId35() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(35);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId36() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(36);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId37() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(37);
		logger.info(assessments.toString());
	}

	@Test
	public void testAssessmentVariablesForSurveyId38() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(38);
		logger.info(assessments.toString());
	}

	//@Test
	public void testAssessmentVariablesForSurveyId39() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(39);
		logger.info(assessments.toString());
	}

	//@Test
	public void testAssessmentVariablesForSurveyId40() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(40);
		logger.info(assessments.toString());
	}

	//@Test
	public void testAssessmentVariablesForSurveyId41() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(41);
		logger.info(assessments.toString());
	}

	//@Test
	public void testAssessmentVariablesForSurveyId42() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(42);
		logger.info(assessments.toString());
	}

	//@Test
	public void testAssessmentVariablesForSurveyId43() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(43);
		logger.info(assessments.toString());
	}

	//@Test
	public void testAssessmentVariablesForSurveyId44() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(44);
		logger.info(assessments.toString());
	}

	//@Test
	public void testAssessmentVariablesForSurveyId45() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(45);
		logger.info(assessments.toString());
	}
}
