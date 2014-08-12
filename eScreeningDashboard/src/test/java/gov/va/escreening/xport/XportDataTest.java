package gov.va.escreening.xport;

import static org.junit.Assert.assertNotNull;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.NoteTitle;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.RuleRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.service.RuleService;
import gov.va.escreening.service.SurveyMeasureResponseService;
import gov.va.escreening.service.export.ExportDataService;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class XportDataTest {

	private static final int OOO_BATTERY_ID = 4;
	private static final int TEST_USER_ID = 5;
	private static final int TEST_VET_ID = 20;

	Logger logger = Logger.getLogger(XportDataTest.class);
	@Resource
	private AssessmentDelegate assessmentDelegate;

	@Resource
	RuleService ruleService;

	@Resource
	VeteranAssessmentRepository vetAssessmentRepo;

	@Resource(name = "exportDataService")
	ExportDataService exportDataService;

	@Resource
	RuleRepository ruleRepo;

	@Resource
	CreateAssessmentDelegate createAsses;

	@Resource
	SurveyMeasureResponseService surveyMeasureRespSvc;

	@Resource
	MeasureAnswerRepository measureAnswerRepo;

	@Resource
	MeasureRepository measureRepo;

	@Resource
	SurveyRepository surveyRepo;

	@Before
	public void testSetup() {
		assertNotNull(assessmentDelegate);
		assertNotNull(vetAssessmentRepo);
	}

	@Test
	public void testRuleRepository() {
		List<Rule> rules = ruleRepo.getRuleForAssessment(1);
		assertNotNull(rules);
	}

	private SurveyMeasureResponse createSMR(Measure m, MeasureAnswer ma,
			boolean val, VeteranAssessment va, Survey survey) {
		SurveyMeasureResponse res = new SurveyMeasureResponse();
		res.setBooleanValue(val);
		res.setMeasure(m);
		res.setMeasureAnswer(ma);
		res.setVeteranAssessment(va);
		res.setSurvey(survey);
		return res;
	}

	private VeteranAssessment createTestAssessment() {
		VeteranAssessment assessment = new VeteranAssessment();
		assessment.setBattery(new Battery(5));
		assessment.setClinic(new Clinic(17));
		assessment.setClinician(new User(TEST_USER_ID));
		assessment.setCreatedByUser(new User(TEST_USER_ID));
		assessment.setProgram(new Program(OOO_BATTERY_ID));
		assessment.setVeteran(new Veteran(TEST_VET_ID));
		assessment.setNoteTitle(new NoteTitle(1));
		assessment.setAccessMode(0);
		assessment.setDateCreated(Calendar.getInstance().getTime());
		assessment.setAssessmentStatus(new AssessmentStatus(2));

		vetAssessmentRepo.create(assessment);

		assessment = vetAssessmentRepo.findByVeteranId(TEST_VET_ID).get(0);
		return assessment;
	}

	@Test
	public void roas() throws UnsupportedEncodingException, IOException {
		Assert.assertTrue(exportDataTester("roas.js"));
	}

	@Test
	public void pc_ptsd() throws UnsupportedEncodingException, IOException {
		Assert.assertTrue(exportDataTester("pc_ptsd.js"));
	}

	@Test
	public void btbis() throws UnsupportedEncodingException, IOException {
		Assert.assertTrue(exportDataTester("btbis.js"));
	}

	// @Rollback(value = false)
	@Test
	public void testEveryFileForExportData() throws UnsupportedEncodingException, IOException {
		File dir = new File(System.getProperty("ROOT_DIR", "target/test-classes/exports"));
		for (String fileName : dir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".js");
			}
		})) {
			Assert.assertTrue(fileName, exportDataTester(fileName));
		}
	}

	@Test
	public void scenario_1() throws UnsupportedEncodingException, IOException {
		Assert.assertTrue(exportDataTester("scenario_1.js"));
	}

	private boolean exportDataTester(String jsonFileName) throws UnsupportedEncodingException, IOException {
		Object[] testTuple = createTestAssessment(jsonFileName);
		return exportDataVerifier(testTuple);
	}

	private boolean exportDataVerifier(Object[] testTuple) {
		AssesmentTestData atd = (AssesmentTestData) testTuple[0];
		VeteranAssessment va = (VeteranAssessment) testTuple[1];
		List<DataExportCell> exportedData = exportDataService.buildExportDataForAssessment(va, 1);

		return exportDataVerifierResult(atd, exportedData);
	}

	private boolean exportDataVerifierResult(AssesmentTestData atd,
			List<DataExportCell> exportedData) {

		for (DataExportCell dec : exportedData) {
			if (!"999".equals(dec.getCellValue())) {
				if (!decMatchesWithTestData(dec, atd)) {
					return false;
				}
			}
		}

		// now we also need to make sure all smr entered by user in the json file were tested and found correct
		for (TestSurvey ts : atd.testSurveys) {
			Assert.assertTrue(String.format("user provided data =>%s->%s has following export name(s) with no corresponding result in exported data %s", atd.testName, ts.surveyName, ts.smrMap), ts.smrMap.isEmpty());
		}

		return true;
	}

	private boolean decMatchesWithTestData(DataExportCell dec,
			AssesmentTestData atd) {
		String expectedVal = null;
		for (TestSurvey ts : atd.testSurveys) {
			expectedVal = ts.smrMap.remove(dec.getColumnName());
			if (expectedVal != null) {
				boolean same = dec.getCellValue().equals(expectedVal);
				Assert.assertEquals(atd.testName + "->" + ts.surveyName + "->" + dec.getColumnName(), expectedVal, dec.getCellValue());
				return same;
			}
		}
		Assert.assertNotNull(String.format("export data=>%s is not provided in user provided json", dec.getColumnName()), expectedVal);

		return false;
	}

	private Object[] createTestAssessment(String fileName) throws UnsupportedEncodingException, IOException {

		VeteranAssessment assessment = createTestAssessment();
		assessment.setSurveyMeasureResponseList(new ArrayList<SurveyMeasureResponse>());

		Map<String, List<MeasureAnswer>> maMap = createMeasureAnswerMap();
		Map<String, Survey> surveyMap = createSurveyMap();

		AssesmentTestData atd = createAssesmentTestData(fileName);

		for (TestSurvey ts : atd.testSurveys) {
			Survey s = surveyMap.get(ts.surveyName);
			Assert.assertNotNull(ts.surveyName, s);
			for (Entry<String, String> entry : ts.smrMap.entrySet()) {
				MeasureAnswer ma = findMeasureAnswer(atd.testName, ts.surveyName, maMap, entry.getKey(), entry.getValue());
				if (ma != null) {
					SurveyMeasureResponse res = createSMR(ma.getMeasure(), ma, true, assessment, s);
					assessment.getSurveyMeasureResponseList().add(res);
				}
			}
		}
		assessment = vetAssessmentRepo.update(assessment);

		return new Object[] { atd, assessment };
	}

	private AssesmentTestData createAssesmentTestData(String fileName) throws IOException {
		// String fileContents = new Scanner(new File(System.getProperty("TEST_JSON", "/atd.js")))
		File f = new File(System.getProperty("ROOT_DIR", "target/test-classes/exports"), fileName);
		List<String> fileContentsLst = Files.readLines(f, Charsets.UTF_8);
		StringBuilder fileContents = new StringBuilder();
		for (String line : fileContentsLst) {
			fileContents.append(line.trim()).append("\n");
		}
		Gson gson = new GsonBuilder().create();
		try {
			AssesmentTestData atd = gson.fromJson(fileContents.toString(), AssesmentTestData.class);
			return atd;
		} catch (JsonSyntaxException jse) {
			throw new IllegalStateException("JsonParsing xception found in file=>" + fileName + ":" + jse.getMessage(), jse);
		}
	}

	private boolean compare(Map<String, String> testData,
			List<DataExportCell> exportedData) {
		boolean equal = true;
		for (DataExportCell dec : exportedData) {
			String decValue = dec.getCellValue();
			if ("999".equals(decValue)) {
				continue;
			}
			String tdValue = testData.get(dec.getColumnName());
			equal &= decValue.equals(tdValue);
		}
		return equal;
	}

	private MeasureAnswer findMeasureAnswer(String testName, String surveyName,
			Map<String, List<MeasureAnswer>> maMap, String exportName,
			String calculationValue) {
		Assert.assertNotNull(maMap);
		List<MeasureAnswer> m = maMap.get(exportName);

		if (m != null) {
			for (MeasureAnswer ma : m) {
				if (ma.getCalculationValue() == null) {
					return ma;
				} else if (ma.getCalculationValue().equals(calculationValue)) {
					return ma;
				}
			}
		}
		return null;

	}

	private Map<String, List<MeasureAnswer>> createMeasureAnswerMap() {
		List<MeasureAnswer> maLst = measureAnswerRepo.findAll();

		Map<String, List<MeasureAnswer>> maMap = new HashMap<String, List<MeasureAnswer>>();
		int totalMasAdded = 0;
		for (MeasureAnswer ma : maLst) {
			String exportName = ma.getExportName();
			List<MeasureAnswer> exportLst = maMap.get(exportName);
			if (exportLst == null) {
				exportLst = new ArrayList<MeasureAnswer>();
				maMap.put(exportName, exportLst);
			}
			exportLst.add(ma);
		}

		Set<String> exportNames = maMap.keySet();
		for (String k : exportNames) {
			totalMasAdded += maMap.get(k).size();
		}

		Assert.assertTrue(maLst.size() == totalMasAdded);

		return maMap;
	}

	private Map<String, Survey> createSurveyMap() {
		List<Survey> sLst = surveyRepo.findAll();
		Map<String, Survey> sMap = new HashMap<String, Survey>();
		for (Survey s : sLst) {
			sMap.put(s.getName(), s);
		}
		return sMap;
	}

	class AssesmentTestData {
		String testName;
		List<TestSurvey> testSurveys;
	}

	class TestSurvey {
		String surveyName;
		Map<String, String> smrMap;
	}
}
