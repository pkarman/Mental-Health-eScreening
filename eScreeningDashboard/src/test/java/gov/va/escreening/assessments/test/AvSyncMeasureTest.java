package gov.va.escreening.assessments.test;

import gov.va.escreening.service.AssessmentVariableService;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.gson.Gson;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class AvSyncMeasureTest {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(type = AssessmentVariableService.class)
	AssessmentVariableService avs;

	@Test
	public void testAssessmentVariablesForSurveyId1() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(1);
		showAssessmentsAsJson(1, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId2() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(2);
		showAssessmentsAsJson(2, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId3() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(3);
		showAssessmentsAsJson(3, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId4() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(4);
		showAssessmentsAsJson(4, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId5() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(5);
		showAssessmentsAsJson(5, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId6() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(6);
		showAssessmentsAsJson(6, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId7() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(7);
		showAssessmentsAsJson(7, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId8() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(8);
		showAssessmentsAsJson(8, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId9() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(9);
		showAssessmentsAsJson(9, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId10() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(10);
		showAssessmentsAsJson(10, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId11() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(11);
		showAssessmentsAsJson(11, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId12() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(12);
		showAssessmentsAsJson(12, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId13() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(13);
		showAssessmentsAsJson(13, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId14() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(14);
		showAssessmentsAsJson(14, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId15() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(15);
		showAssessmentsAsJson(15, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId16() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(16);
		showAssessmentsAsJson(16, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId17() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(17);
		showAssessmentsAsJson(17, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId18() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(18);
		showAssessmentsAsJson(18, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId19() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(19);
		showAssessmentsAsJson(19, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId20() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(20);
		showAssessmentsAsJson(20, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId21() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(21);
		showAssessmentsAsJson(21, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId22() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(22);
		showAssessmentsAsJson(22, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId23() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(23);
		showAssessmentsAsJson(23, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId24() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(24);
		showAssessmentsAsJson(24, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId25() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(25);
		showAssessmentsAsJson(25, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId26() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(26);
		showAssessmentsAsJson(26, assessments);
	}

	private void showAssessmentsAsJson(int surveyId,
			Table<String, String, Object> assessments) {

		List<Map<String, Object>> avs = Lists.newArrayList();

		for (String rowKey : assessments.rowKeySet()) {
			Map<String, Object> m = Maps.newHashMap(assessments.row(rowKey));

			// replace all 0 with null
			for (Entry<String, Object> e : m.entrySet()) {
				if (e.getValue().equals(0)) {
					e.setValue(null);
				}
			}

			avs.add(m);
		}


		Gson gson = new Gson();
		String json = gson.toJson(avs);
		logger.info(String.format("Asssessment Variables for Survey Id %s==>%s", surveyId, json));
	}

	@Test
	public void testAssessmentVariablesForSurveyId27() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(27);
		showAssessmentsAsJson(27, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId28() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(28);
		showAssessmentsAsJson(28, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId29() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(29);
		showAssessmentsAsJson(29, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId30() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(30);
		showAssessmentsAsJson(30, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId31() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(31);
		showAssessmentsAsJson(31, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId32() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(32);
		showAssessmentsAsJson(32, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId33() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(33);
		showAssessmentsAsJson(33, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId34() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(34);
		showAssessmentsAsJson(34, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId35() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(35);
		showAssessmentsAsJson(35, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId36() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(36);
		showAssessmentsAsJson(36, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId37() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(37);
		showAssessmentsAsJson(37, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId38() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(38);
		showAssessmentsAsJson(38, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId39() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(39);
		showAssessmentsAsJson(39, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId40() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(40);
		showAssessmentsAsJson(40, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId41() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(41);
		showAssessmentsAsJson(41, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId42() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(42);
		showAssessmentsAsJson(42, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId43() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(43);
		showAssessmentsAsJson(43, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId44() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(44);
		showAssessmentsAsJson(44, assessments);
	}

	@Test
	public void testAssessmentVariablesForSurveyId45() {
		Table<String, String, Object> assessments = avs.getAssessmentVarsFor(45);
		showAssessmentsAsJson(45, assessments);
	}
}
