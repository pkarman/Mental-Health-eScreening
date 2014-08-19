package gov.va.escreening.xport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.va.escreening.constants.TemplateConstants;
import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.service.export.ExportDataService;
import gov.va.escreening.templateprocessor.TemplateProcessorService;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	class AssesmentTestData {
		String testName;
		List<TestSurvey> testSurveys;
	}

	class TestSurvey {
		Map<String, String> smrMap;
		String surveyName;
	}

	private static final int OOO_BATTERY_ID = 4;

	private static final int TEST_USER_ID = 5;

	private static final int TEST_VET_ID = 20;

	private static final String minimum = System.getProperty("ROOT_DIR_MINIMUM", "target/test-classes/exports/minimum");
	private static final String detail = System.getProperty("ROOT_DIR_DETAIL", "target/test-classes/exports/detail");

	@Resource(name = "exportDataService")
	ExportDataService exportDataService;

	Logger logger = Logger.getLogger(XportDataTest.class);

	@Resource(type = TemplateProcessorService.class)
	private TemplateProcessorService templateProcessorService;

	@Resource(type = MeasureAnswerRepository.class)
	MeasureAnswerRepository measureAnswerRepo;

	@Resource(type = SurveyRepository.class)
	SurveyRepository surveyRepo;

	@Resource(name = "veteranAssessmentService")
	VeteranAssessmentService vas;

	@Resource(type = VeteranAssessmentRepository.class)
	VeteranAssessmentRepository var;

	private Map<String, SmrBldr> smrBldrMap;

	@Test
	public void btbis() throws UnsupportedEncodingException, IOException {
		assertTrue(exportDataTester("btbis.js", minimum));
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

	private AssesmentTestData createAssesmentTestData(String fileName,
			String root) throws IOException {
		File f = new File(root, fileName);
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

	private Map<String, List<MeasureAnswer>> createMeasureAnswerMap() {
		List<MeasureAnswer> maLst = measureAnswerRepo.findAll();

		Map<String, List<MeasureAnswer>> maMap = new HashMap<String, List<MeasureAnswer>>();
		for (MeasureAnswer ma : maLst) {

			String exportName = ma.getExportName();
			addMa(exportName, ma, maMap);

			String otherExportName = ma.getOtherExportName();
			addMa(otherExportName, ma, maMap);
		}

		return maMap;
	}

	private void addMa(String exportName, MeasureAnswer ma,
			Map<String, List<MeasureAnswer>> maMap) {

		if (exportName == null) {
			return;
		}

		List<MeasureAnswer> exportLst = maMap.get(exportName);
		if (exportLst == null) {
			exportLst = new ArrayList<MeasureAnswer>();
			maMap.put(exportName, exportLst);
		}
		exportLst.add(ma);
	}

	private Map<String, Survey> createSurveyMap() {
		List<Survey> sLst = surveyRepo.findAll();
		Map<String, Survey> sMap = new HashMap<String, Survey>();
		for (Survey s : sLst) {
			sMap.put(s.getName(), s);
		}
		return sMap;
	}

	private VeteranAssessment createTestAssessment(List<Integer> surveyIdList) {
		// VeteranAssessment assessment = new VeteranAssessment();
		// assessment.setBattery(new Battery(5));
		// assessment.setClinic(new Clinic(17));
		// assessment.setClinician(new User(TEST_USER_ID));
		// assessment.setCreatedByUser(new User(TEST_USER_ID));
		// assessment.setProgram(new Program(OOO_BATTERY_ID));
		// assessment.setVeteran(new Veteran(TEST_VET_ID));
		// assessment.setNoteTitle(new NoteTitle(1));
		// assessment.setAccessMode(0);
		// assessment.setDateCreated(Calendar.getInstance().getTime());
		// assessment.setAssessmentStatus(new AssessmentStatus(2));
		//
		// vetAssessmentRepo.create(assessment);
		//
		// assessment = vetAssessmentRepo.findByVeteranId(TEST_VET_ID).get(0);

		Integer assessmentId = vas.create(TEST_VET_ID, OOO_BATTERY_ID, 17, TEST_USER_ID, TEST_USER_ID, 1, 5, surveyIdList);

		VeteranAssessment assessment = var.findOne(assessmentId);

		return assessment;
	}

	private Object[] createTestAssessment(String fileName, String root) throws UnsupportedEncodingException, IOException {

		AssesmentTestData atd = createAssesmentTestData(fileName, root);
		Map<String, List<MeasureAnswer>> maMap = createMeasureAnswerMap();
		Map<String, Survey> surveyMap = createSurveyMap();

		List<Integer> surveyIdList = getTestAssessmentSurveys(atd, surveyMap);

		VeteranAssessment assessment = createTestAssessment(surveyIdList);
		assessment.setSurveyMeasureResponseList(new ArrayList<SurveyMeasureResponse>());

		for (TestSurvey ts : atd.testSurveys) {
			Survey survey = surveyMap.get(ts.surveyName);
			assertNotNull(String.format("%s=>%s id not a valid Survey Name", atd.testName, ts.surveyName), survey);

			for (Entry<String, String> entry : ts.smrMap.entrySet()) {
				String smrBldrKey = getSmrBldrKey(entry);
				SmrBldr smrBldr = smrBldrMap.get(smrBldrKey);
				assertNotNull(String.format("%s=>%s=>%s=>%s is not a valid export Name Type", atd.testName, ts.surveyName, entry.getKey(), smrBldrKey), smrBldr);
				smrBldr.build(atd, ts, maMap, survey, assessment, entry);
			}
			ts.smrMap = cleanSmrMap(ts.smrMap);
		}
		assessment = var.update(assessment);
		return new Object[] { atd, assessment };
	}

	private List<Integer> getTestAssessmentSurveys(AssesmentTestData atd,
			Map<String, Survey> surveyMap) {
		List<Integer> surveyIdList = new ArrayList<Integer>();
		for (TestSurvey ts : atd.testSurveys) {
			Survey survey = surveyMap.get(ts.surveyName);
			assertNotNull(String.format("%s=>%s id not a valid Survey Name", atd.testName, ts.surveyName), survey);
			surveyIdList.add(survey.getSurveyId());
		}
		return surveyIdList;
	}

	private Map<String, String> cleanSmrMap(Map<String, String> smrMap) {
		Map<String, String> csmrMap = new LinkedHashMap<String, String>();
		for (Entry<String, String> entry : smrMap.entrySet()) {
			String[] keyParts = entry.getKey().split("[$]");
			String val = smrMap.get(entry.getKey());
			csmrMap.put(keyParts[keyParts.length > 1 ? 1 : 0], val);
		}
		return csmrMap;
	}

	private String getSmrBldrKey(Entry<String, String> entry) {
		String[] keyParts = entry.getKey().split("[$]");
		String smrBldrKey = keyParts.length == 1 ? "default" : keyParts[0];
		return smrBldrKey;
	}

	private boolean decMatchesWithTestData(DataExportCell dec,
			AssesmentTestData atd) {
		String expectedVal = null;
		for (TestSurvey ts : atd.testSurveys) {
			expectedVal = ts.smrMap.remove(dec.getColumnName());
			if (expectedVal != null) {
				boolean same = dec.getCellValue().equals(expectedVal);
				assertEquals(atd.testName + "->" + ts.surveyName + "->" + dec.getColumnName(), expectedVal, dec.getCellValue());
				return same;
			}
		}
		assertNotNull(String.format("export data=>%s is not provided in user provided json", dec.getColumnName()), expectedVal);

		return false;
	}

	private boolean exportDataTester(String jsonFileName, String root) throws UnsupportedEncodingException, IOException {
		Object[] testTuple = createTestAssessment(jsonFileName, root);
		return exportDataVerifier(testTuple);
	}

	private boolean invokeTemplateTxtReview(String jsonFileName, String root) throws Exception {
		Object[] testTuple = createTestAssessment(jsonFileName, root);
		return templateDataVerifierTypeTxt(testTuple);
	}

	private boolean invokeTemplateHtmlReview(String jsonFileName, String root) throws Exception {
		Object[] testTuple = createTestAssessment(jsonFileName, root);
		return templateDataVerifierTypeHtml(testTuple);
	}

	private boolean exportDataVerifier(Object[] testTuple) {
		AssesmentTestData atd = (AssesmentTestData) testTuple[0];
		VeteranAssessment va = (VeteranAssessment) testTuple[1];
		List<DataExportCell> exportedData = exportDataService.buildExportDataForAssessment(va, 1);

		return exportDataVerifierResult(atd, exportedData);
	}

	private boolean templateDataVerifierTypeTxt(Object[] testTuple) throws Exception {
		AssesmentTestData atd = (AssesmentTestData) testTuple[0];
		VeteranAssessment va = (VeteranAssessment) testTuple[1];

		String progressNoteContent = templateProcessorService.generateCPRSNote(va.getVeteranAssessmentId(), TemplateConstants.ViewType.TEXT, EnumSet.of(TemplateType.VISTA_QA));

		return !progressNoteContent.isEmpty() && !progressNoteContent.contains("<") && !progressNoteContent.contains(">");
	}

	private boolean templateDataVerifierTypeHtml(Object[] testTuple) throws Exception {
		AssesmentTestData atd = (AssesmentTestData) testTuple[0];
		VeteranAssessment va = (VeteranAssessment) testTuple[1];

		String progressNoteContent = templateProcessorService.generateCPRSNote(va.getVeteranAssessmentId(), ViewType.HTML, EnumSet.of(TemplateType.ASSESS_SCORE_TABLE));

		return !progressNoteContent.isEmpty() && progressNoteContent.contains("<") && progressNoteContent.contains(">") && progressNoteContent.contains("</");
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
			assertTrue(String.format("user provided data =>%s->%s has following export name(s) with no corresponding result in exported data %s", atd.testName, ts.surveyName, ts.smrMap), ts.smrMap.isEmpty());
		}

		return true;
	}

	@Test
	public void pc_ptsd() throws UnsupportedEncodingException, IOException {
		assertTrue(exportDataTester("pc_ptsd.js", minimum));
	}

	@Test
	public void roas() throws UnsupportedEncodingException, IOException {
		assertTrue(exportDataTester("roas.js", minimum));
	}

	@Test
	public void scenario_1() throws UnsupportedEncodingException, IOException {
		assertTrue(exportDataTester("scenario_1.js", minimum));
	}

	// @Rollback(value = false)
	@Test
	public void testEveryFileForExportData() throws UnsupportedEncodingException, IOException {
		File dir = new File(minimum);
		for (String fileName : dir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".js");
			}
		})) {
			assertTrue(fileName, exportDataTester(fileName, minimum));
		}
	}

	// @Rollback(value = false)
	@Test
	public void testEveryFileForExportDataDetail() throws UnsupportedEncodingException, IOException {
		File dir = new File(detail);
		for (String fileName : dir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".js");
			}
		})) {
			assertTrue(fileName, exportDataTester(fileName, detail));
		}
	}

	// @Rollback(value = false)
	@Test
	public void testEveryFileWithJsonDetailForTemplatesCorrectnessWith__TEXT() throws Exception {
		File dir = new File(detail);
		for (String fileName : dir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".js");
			}
		})) {
			assertTrue(String.format("Template generation failed TXT type for %s", fileName), invokeTemplateTxtReview(fileName, detail));
		}
	}

	// @Rollback(value = false)
	@Test
	public void testEveryFileWithJsonDetailForTemplatesCorrectnessWith__HTML() throws Exception {
		File dir = new File(detail);
		for (String fileName : dir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".js");
			}
		})) {
			assertTrue(String.format("Template generation failed HTML type for %s", fileName), invokeTemplateHtmlReview(fileName, detail));
		}
	}

	@Before
	public void testSetup() {
		assertNotNull(var);
		assertNotNull(exportDataService);
		assertNotNull(measureAnswerRepo);
		assertNotNull(surveyRepo);
		assertNotNull(templateProcessorService);

		smrBldrMap = new HashMap<String, SmrBldr>();
		smrBldrMap.put("default", new SmrBldrDefault());
		smrBldrMap.put("vars", new SmrBldrVars());
		smrBldrMap.put("derive", new SmrBldrVars());
		smrBldrMap.put("date", new SmrBldrDate());
		smrBldrMap.put("numb", new SmrBldrNumb());
		smrBldrMap.put("other", new SmrBldrOther());
		smrBldrMap.put("text", new SmrBldrText());
	}

	interface SmrBldr {
		void build(AssesmentTestData atd, TestSurvey ts,
				Map<String, List<MeasureAnswer>> maMap, Survey survey,
				VeteranAssessment assessment, Entry<String, String> entry);
	}

	class SmrBldrDefault implements SmrBldr {
		@Override
		public void build(AssesmentTestData atd, TestSurvey ts,
				Map<String, List<MeasureAnswer>> maMap, Survey survey,
				VeteranAssessment assessment, Entry<String, String> entry) {
			MeasureAnswer ma = findMeasureAnswer(atd.testName, ts.surveyName, maMap, entry.getKey(), entry.getValue());
			if (ma != null) {
				SurveyMeasureResponse res = createSMR(ma.getMeasure(), ma, true, assessment, survey, entry.getValue());
				assessment.getSurveyMeasureResponseList().add(res);
			}
		}

		String cleanExportName(String key) {
			String[] keyParts = key.split("[$]");
			String smrBldrKey = keyParts.length == 1 ? key : keyParts[1];
			return smrBldrKey;
		}

		MeasureAnswer findMeasureAnswer(String testName, String surveyName,
				Map<String, List<MeasureAnswer>> maMap, String exportName,
				String value) {
			String cleanExportName = cleanExportName(exportName);
			List<MeasureAnswer> m = maMap.get(cleanExportName);
			if (m != null) {
				for (MeasureAnswer ma : m) {
					MeasureAnswer selectedMa = isValidMeasureAnswer(ma, exportName, value);
					if (selectedMa != null) {
						return selectedMa;
					}
				}
				assertTrue(String.format("%s=>%s=>%s=>%s is not a valid data or [%s] is out of range for %s", testName, surveyName, exportName, value, value, cleanExportName), false);
			}
			return null;
		}

		MeasureAnswer isValidMeasureAnswer(MeasureAnswer ma, String exportName,
				String value) {
			if (ma.getCalculationValue() == null) {
				return ma;
			} else if (ma.getCalculationValue().equals(getCleanValue(value)[0])) {
				return ma;
			}
			return null;
		}

		String[] getCleanValue(String value) {
			return value.split("[$]");
		}

		SurveyMeasureResponse createSMR(Measure m, MeasureAnswer ma,
				boolean val, VeteranAssessment va, Survey survey, String value) {
			SurveyMeasureResponse res = new SurveyMeasureResponse();
			res.setBooleanValue(val);
			res.setMeasure(m);
			res.setMeasureAnswer(ma);
			res.setVeteranAssessment(va);
			res.setSurvey(survey);
			return res;
		}

	}

	class SmrBldrVars extends SmrBldrDefault implements SmrBldr {
	}

	class SmrBldrDate extends SmrBldrDefault implements SmrBldr {

		@Override
		SurveyMeasureResponse createSMR(Measure m, MeasureAnswer ma,
				boolean val, VeteranAssessment va, Survey survey, String value) {
			SurveyMeasureResponse res = super.createSMR(m, ma, val, va, survey, value);
			res.setBooleanValue(null);
			res.setTextValue(value);
			return res;
		}
	}

	class SmrBldrText extends SmrBldrDefault implements SmrBldr {

		@Override
		SurveyMeasureResponse createSMR(Measure m, MeasureAnswer ma,
				boolean val, VeteranAssessment va, Survey survey, String value) {
			SurveyMeasureResponse res = super.createSMR(m, ma, val, va, survey, value);
			res.setBooleanValue(null);
			res.setTextValue(value);
			return res;
		}
	}

	class SmrBldrNumb extends SmrBldrDefault implements SmrBldr {
		@Override
		SurveyMeasureResponse createSMR(Measure m, MeasureAnswer ma,
				boolean val, VeteranAssessment va, Survey survey, String value) {
			SurveyMeasureResponse res = super.createSMR(m, ma, val, va, survey, value);
			res.setBooleanValue(null);
			try {
				Long lval = Long.valueOf(value);
				res.setNumberValue(lval);
			} catch (NumberFormatException nfe) {
				assertNull(String.format("%s=>%s is not a valid Number", ma.getExportName(), value), value);
			}

			return res;
		}

	}

	class SmrBldrOther extends SmrBldrDefault implements SmrBldr {

		@Override
		SurveyMeasureResponse createSMR(Measure m, MeasureAnswer ma,
				boolean val, VeteranAssessment va, Survey survey, String value) {
			SurveyMeasureResponse res = super.createSMR(m, ma, val, va, survey, value);
			String[] valParts = getCleanValue(value);
			res.setOtherValue(valParts.length > 1 ? valParts[1] : valParts[0]);
			return res;
		}

		@Override
		MeasureAnswer isValidMeasureAnswer(MeasureAnswer ma, String exportName,
				String value) {
			String[] valParts = getCleanValue(value);
			String otherExportName = cleanExportName(exportName);
			boolean validMa = "other".equals(ma.getAnswerType()) && otherExportName.equals(ma.getOtherExportName());
			validMa &= valParts.length > 1 && ma.getCalculationValue() != null ? valParts[0].equals(ma.getCalculationValue()) : true;
			return validMa ? ma : null;
		}

	}
}
