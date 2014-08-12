package gov.va.escreening.service.export;

import gov.va.escreening.domain.ExportDataDefaultValuesEnum;
import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.domain.MeasureTypeEnum;
import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.dto.dashboard.DataExportFilterOptions;
import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.ExportType;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.DataExportException;
import gov.va.escreening.form.ExportDataFormBean;
import gov.va.escreening.repository.ExportLogRepository;
import gov.va.escreening.repository.ExportTypeRepository;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.ProgramRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.repository.VeteranRepository;
import gov.va.escreening.service.MeasureService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.service.VeteranAssessmentSurveyService;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("exportDataService")
public class ExportDataServiceImpl implements ExportDataService, BeanFactoryAware {

	private BeanFactory bf;

	@Autowired
	private ExportLogRepository exportLogRepository;
	@Autowired
	private ExportTypeRepository exportTypeRepository;
	@Autowired
	private MeasureAnswerRepository measureAnswerRepository;
	@Autowired
	private MeasureRepository measureRepository;
	@Autowired
	private MeasureService measureService;
	@Autowired
	private ProgramRepository programRepository;
	@Autowired
	private SurveyMeasureResponseRepository surveyMeasureResponseRepository;
	@Autowired
	private SurveyRepository surveyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VeteranAssessmentService veteranAssessmentService;
	@Autowired
	private VeteranAssessmentSurveyService veteranAssessmentSurveyService;
	@Autowired
	private VeteranRepository veteranRepository;

	private static final Integer DEFAULT_TABULAR_ROW_INDEX = 0;

	@Override
	public AssessmentDataExport getAssessmentDataExport(
			ExportDataFormBean exportDataFormBean) {
		AssessmentDataExport assessmentDataExport = new AssessmentDataExport();

		List<VeteranAssessment> filteredAssessments = null;

		// 1) Use the passed in filter criteria to pull in the matching assessments
		if (exportDataFormBean.getHasParameter()) {
			filteredAssessments = veteranAssessmentService.searchVeteranAssessmentForExport(exportDataFormBean);
		} else {
			return assessmentDataExport;
		}

		// 2) Get a list of all modules included from all of the matched assessments
		// List<Integer> distinctSurveys = getDistinctSurveys(filteredAssessments);

		// 3) Create a hashmap to keep track of the number of rows for every table type question
		// Map<Integer, Integer> tableTypeRowCount = getTableQuestionTypeCountsForAssessment(distinctSurveys,
		// filteredAssessments);

		// 4) Populate the table contents with the answer values
		assessmentDataExport.setTableContent(getTableContent(filteredAssessments, exportDataFormBean.getExportTypeId()));

		return assessmentDataExport;
	}

	@Override
	public void saveDataExport(HSSFWorkbook workbook,
			AssessmentDataExport dataExport) throws Exception {

		String fileFullPath = getFileFullPath();

		// TODO temporarily commented out
		// writefile(workbook, fileFullPath);
		dataExport.getFilterOptions().setFilePath(fileFullPath);

		// Add an entry to the exportLog table
		ExportLog exportLog = createExportLogFromOptions(dataExport.getFilterOptions());
		exportLogRepository.create(exportLog);
	}

	private String getFileFullPath() {
		// TODO move this to a config file

		String directory = "C:\\Users\\jocchiuzzo\\Desktop\\test\\";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString() + ".xls";
		String fileFullPath = directory + fileName;

		return fileFullPath;
	}

	private void writefile(HSSFWorkbook workbook, String fileFullPath) throws Exception {
		// OutputStream fos = new FileOutputStream("C:\\Users\\jocchiuzzo\\Desktop\\test\\test2.xls");
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(fileFullPath);
			workbook.write(fos);
		} catch (Exception e) {
			throw e;
		} finally {
			fos.close();
		}
	}

	private ExportLog createExportLogFromOptions(DataExportFilterOptions options) throws Exception {
		if (options == null)
			throw new Exception("DataExportFilterOptions was null");

		ExportLog exportLog = new ExportLog();

		if (options.getAssessmentEnd() != null) {
			exportLog.setAssessmentEndFilter(options.getAssessmentEnd());
		}

		if (options.getAssessmentStart() != null) {
			exportLog.setAssessmentStartFilter(options.getAssessmentStart());
		}

		if (options.getClinicianUserId() != null) {
			User clinician = userRepository.findOne(options.getClinicianUserId());
			exportLog.setClinician(clinician);
		}

		if (options.getComment() != null && !options.getComment().isEmpty()) {
			exportLog.setComment(options.getComment());
		}

		if (options.getCreatedByUserId() != null) {
			User createdByUser = userRepository.findOne(options.getCreatedByUserId());
			exportLog.setCreatedByUser(createdByUser);
		}

		if (options.getExportedByUserId() != null) {
			User exportedByUser = userRepository.findOne(options.getExportedByUserId());
			exportLog.setExportedByUser(exportedByUser);
		}

		if (options.getExportTypeId() != null) {
			ExportType exportType = exportTypeRepository.findOne(options.getExportTypeId());
			exportLog.setExportType(exportType);
		}

		if (options.getFilePath() != null && !options.getFilePath().isEmpty()) {
			exportLog.setFilePath(options.getFilePath());
		}

		if (options.getProgramId() != null) {
			Program program = programRepository.findOne(options.getProgramId());
			exportLog.setProgram(program);
		}

		if (options.getVeteranId() != null) {
			Veteran veteran = veteranRepository.findOne(options.getVeteranId());
			exportLog.setVeteran(veteran);
		}

		return exportLog;
	}

	private List<Integer> getDistinctSurveys(
			List<VeteranAssessment> veteranAssessments) {

		Set<Integer> distinctSurveyIds = new HashSet<Integer>();

		if (veteranAssessments != null && veteranAssessments.size() > 0) {
			for (VeteranAssessment assessment : veteranAssessments) {
				for (Survey survey : assessment.getSurveys()) {
					distinctSurveyIds.add(survey.getSurveyId());
				}
			}
		}

		List<Integer> distinctSurveyIdList = new ArrayList<Integer>(distinctSurveyIds);
		return distinctSurveyIdList;
	}

	// HashMap<Integer, Integer> -> HashMap<ParentMeasureId, GreatestRowCount>
	private Map<Integer, Integer> getTableQuestionTypeCountsForAssessment(
			List<Integer> distinctSurveys,
			List<VeteranAssessment> filteredAssessments) {

		// first get a list of all table type measure ids from each each survey across all assessments
		Set<Measure> distictTableMeasureIds = new HashSet<Measure>();
		for (Integer surveyId : distinctSurveys) {
			List<Measure> measureIds = measureService.getMeasureIdsForTableTypeQuestions(surveyId);
			distictTableMeasureIds.addAll(measureIds);
		}

		// next create a hash map to contain the row count for each table type question
		HashMap<Integer, Integer> tableTypeRowCount = new HashMap<Integer, Integer>();
		for (VeteranAssessment assessment : filteredAssessments) {
			List<Measure> measures = new ArrayList<Measure>();
			measures.addAll(distictTableMeasureIds);
			for (Measure measure : measures) {
				Integer rowCount = surveyMeasureResponseRepository.getNumRowsForAssessmentIdMeasure(assessment.getVeteranAssessmentId(), measure);

				if (rowCount == null)
					rowCount = 0;

				Integer measureId = measure.getMeasureId();
				if (!tableTypeRowCount.containsKey(measureId))
					tableTypeRowCount.put(measureId, rowCount);
				else {
					Integer measureRowCount = tableTypeRowCount.get(measureId);
					if (measureRowCount < rowCount) {
						tableTypeRowCount.put(measureId, rowCount);
					}
				}
			}
		}

		return tableTypeRowCount;
	}

	private List<List<DataExportCell>> getTableContent(
			List<VeteranAssessment> filteredAssessments,
			List<Integer> distinctSurveys,
			Map<Integer, Integer> tableTypeRowCount,
			Integer identifiedExportType) {

		List<List<DataExportCell>> tableContent = new ArrayList<List<DataExportCell>>();

		// build an export row for each assessment
		for (VeteranAssessment assessment : filteredAssessments) {
			List<DataExportCell> row = buildExportDataForAssessment(assessment, identifiedExportType);
			tableContent.add(row);
		}

		return tableContent;
	}

	private List<List<DataExportCell>> getTableContent(
			List<VeteranAssessment> filteredAssessments,
			Integer identifiedExportType) {

		List<List<DataExportCell>> tableContent = new ArrayList<List<DataExportCell>>();

		// build an export row for each assessment
		for (VeteranAssessment assessment : filteredAssessments) {
			List<DataExportCell> row = buildExportDataForAssessment(assessment, identifiedExportType);
			tableContent.add(row);
		}

		return tableContent;
	}

	@Override
	public List<DataExportCell> buildExportDataForAssessment(
			VeteranAssessment assessment, Integer identifiedExportType) {

		// Iterate the list of modules and add them to the export row
		List<DataExportCell> row = new ArrayList<DataExportCell>();
		for (ModuleEnum module : ModuleEnum.values()) {
			List<DataExportCell> cells = module.apply(this.bf, assessment, identifiedExportType);
			if (cells != null && !cells.isEmpty()) {
				row.addAll(cells);
			}
		}

		// for(Integer surveyId : distinctSurveys) {
		// //Check to see if the current assessment has the current survey
		// Boolean containsSurvey = veteranAssessmentSurveyService.doesVeteranAssessmentContainSurvey(
		// assessment.getVeteranAssessmentId(), surveyId);
		//
		// List<DataExportCell> exportCells = createExportCellsForAssignedSurvey(surveyId,
		// assessment.getVeteranAssessmentId(), tableTypeRowCount, containsSurvey, identifiedExportType);
		// row.addAll(exportCells);
		// }

		return row;
	}

	private List<DataExportCell> createExportCellsForAssignedSurvey(
			int surveyId, int veteranAssessmentId,
			Map<Integer, Integer> tableTypeRowCount, boolean surveyAssigned,
			Integer identifiedExportType) {

		List<DataExportCell> surveyDataExportCells = new ArrayList<DataExportCell>();

		// Iterate thru all of the questions in the survey and create the table cell for it.
		List<Measure> measures = measureService.getMeasuresBySurvey(surveyId);
		for (Measure measure : measures) {

			List<DataExportCell> measureExportCells = createDataExportCellsForAssignedMeasure(measure, veteranAssessmentId, tableTypeRowCount, surveyAssigned, null, identifiedExportType);
			surveyDataExportCells.addAll(measureExportCells);
		}

		return surveyDataExportCells;
	}

	// This method is recursively called to handle table type questions.
	private List<DataExportCell> createDataExportCellsForAssignedMeasure(
			Measure measure, int veteranAssessmentId,
			Map<Integer, Integer> tableTypeRowCount, boolean surveyAssigned,
			Integer tabularRowIndex, Integer identifiedExportType) {

		List<DataExportCell> measureExportCells = new ArrayList<DataExportCell>();

		// DO NOT EXPORT A QUESTION WHEN THE QUESTION HAS THE ISMEASUREPPI ATTRIBUTE SET TO TRUE AND EXPORT TYPE IS
		// DE-IDENTIFIED!!!
		Boolean isMeasurePPI = measure.getIsPatientProtectedInfo();
		if (isMeasurePPI == null)
			throw new DataExportException(String.format("Measure must have patient protected attribute set, AssessmentId: %s, MeasureId: %s", veteranAssessmentId, measure.getMeasureId()));

		if (isMeasurePPI && (ExportTypeEnum.DEIDENTIFIED.getExportTypeId() == identifiedExportType)) {
			// this is PPI and the export method is de-identified do not export this measure!!
			return measureExportCells;
		}

		Integer measureTypeId = measure.getMeasureType().getMeasureTypeId();
		if (measureTypeId == MeasureTypeEnum.FREETEXT.getMeasureTypeId())
			measureExportCells.add(buildTextExportCell(veteranAssessmentId, measure, surveyAssigned, tabularRowIndex));
		else if (measureTypeId == MeasureTypeEnum.INSTRUCTION.getMeasureTypeId()) {
			// Do not create a cell for this type of measure
		} else if (measureTypeId == MeasureTypeEnum.READONLYTEXT.getMeasureTypeId())
			measureExportCells.add(buildTextExportCell(veteranAssessmentId, measure, surveyAssigned, tabularRowIndex));
		else if (measureTypeId == MeasureTypeEnum.SELECTMULTI.getMeasureTypeId())
			measureExportCells.addAll(buildSelectMultiExportCells(veteranAssessmentId, measure, surveyAssigned, tabularRowIndex));
		else if (measureTypeId == MeasureTypeEnum.SELECTMULTIMATRIX.getMeasureTypeId())
			measureExportCells.addAll(buildSelectMultiMatrixExportCells(veteranAssessmentId, measure, surveyAssigned, tabularRowIndex));
		else if (measureTypeId == MeasureTypeEnum.SELECTONE.getMeasureTypeId())
			measureExportCells.addAll(buildSelectOneExportCells(veteranAssessmentId, measure, surveyAssigned, tabularRowIndex));
		else if (measureTypeId == MeasureTypeEnum.SELECTONEMATRIX.getMeasureTypeId())
			measureExportCells.addAll(buildSelectOneMatrixExportCells(veteranAssessmentId, measure, surveyAssigned, tabularRowIndex));
		else if (measureTypeId == MeasureTypeEnum.TABLEQUESTION.getMeasureTypeId())
			measureExportCells.addAll(buildTableTypeQuestionExportCells(veteranAssessmentId, measure, surveyAssigned, tableTypeRowCount, identifiedExportType));
		else
			throw new DataExportException(String.format("Attempted to export unhandled measure type. MeasureTypeId: %s, MeasureId %s, VeteranAssessmentId: %s.", measureTypeId, measure.getMeasureId(), veteranAssessmentId));

		return measureExportCells;
	}

	private List<DataExportCell> buildTableTypeQuestionExportCells(
			Integer veteranAssessmentId, Measure parentTemplateMeasure,
			Boolean surveyAssigned, Map<Integer, Integer> tableTypeRowCount,
			Integer identifiedExportType) {

		List<DataExportCell> tableExportCells = new ArrayList<DataExportCell>();

		// get max number of rows from all exported assessments
		Integer maxNumRows = tableTypeRowCount.get(parentTemplateMeasure.getMeasureId());
		if (maxNumRows == null || maxNumRows == 0) {
			// create one empty row
			tableExportCells.addAll(buildTableRow(veteranAssessmentId, parentTemplateMeasure, false, tableTypeRowCount, DEFAULT_TABULAR_ROW_INDEX, identifiedExportType));
			return tableExportCells;
		}

		for (int loopCount = 0; loopCount < maxNumRows; loopCount++)
			tableExportCells.addAll(buildTableRow(veteranAssessmentId, parentTemplateMeasure, surveyAssigned, tableTypeRowCount, loopCount, identifiedExportType));

		return tableExportCells;
	}

	private List<DataExportCell> buildTableRow(Integer veteranAssessmentId,
			Measure parentTemplateMeasure, Boolean surveyAssigned,
			Map<Integer, Integer> tableTypeRowCount, Integer tabularRowIndex,
			Integer identifiedExportType) {

		List<Measure> childMeasures = measureRepository.getChildMeasures(parentTemplateMeasure);

		List<DataExportCell> childMeasureExportCells = new ArrayList<DataExportCell>();
		if (childMeasures != null && childMeasures.size() > 0) {
			for (Measure childMeasure : childMeasures)
				childMeasureExportCells.addAll(createDataExportCellsForAssignedMeasure(childMeasure, veteranAssessmentId, tableTypeRowCount, surveyAssigned, tabularRowIndex, identifiedExportType));
		}

		return childMeasureExportCells;
	}

	private List<DataExportCell> buildSelectOneMatrixExportCells(
			Integer veteranAssessmentId, Measure parentTemplateMeasure,
			Boolean surveyAssigned, Integer tabularRowIndex) {

		List<Measure> childMeasures = measureRepository.getChildMeasures(parentTemplateMeasure);

		List<DataExportCell> selectSingleMatrixExportCells = new ArrayList<DataExportCell>();
		if (childMeasures != null && childMeasures.size() > 0) {
			for (Measure childMeasure : childMeasures) {
				List<DataExportCell> childSelectSingleMatrixCells = buildSelectOneExportCells(veteranAssessmentId, childMeasure, surveyAssigned, tabularRowIndex);
				selectSingleMatrixExportCells.addAll(childSelectSingleMatrixCells);
			}
		}

		return selectSingleMatrixExportCells;
	}

	private List<DataExportCell> buildSelectOneExportCells(
			Integer veteranAssessmentId, Measure templateMeasure,
			Boolean surveyAssigned, Integer tabularRowIndex) {

		// Validate
		if (templateMeasure.getMeasureAnswerList() == null)
			throw new DataExportException(String.format("Measure of type select single was missing an answer, measureId: %s veteranAssessmentId: %s", templateMeasure.getMeasureId(), veteranAssessmentId));

		DataExportCell selectSingleExportCell = new DataExportCell();
		String columnName = getCellExportName(templateMeasure.getMeasureAnswerList().get(0), veteranAssessmentId, tabularRowIndex);
		selectSingleExportCell.setColumnName(columnName);

		List<SurveyMeasureResponse> responses = null;
		if (surveyAssigned) {
			if (tabularRowIndex == null)
				responses = surveyMeasureResponseRepository.getForVeteranAssessmentAndMeasure(veteranAssessmentId, templateMeasure.getMeasureId());
			else
				responses = surveyMeasureResponseRepository.findForAssessmentIdMeasureRow(veteranAssessmentId, templateMeasure.getMeasureId(), tabularRowIndex);
		}

		DataExportCell otherDataExportCell = getDataExportCellForOther(templateMeasure, veteranAssessmentId, tabularRowIndex);
		if (responses == null || responses.size() == 0) {
			selectSingleExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
			if (otherDataExportCell != null)
				setOtherExportDataCellValue(otherDataExportCell, null);
		} else {
			SurveyMeasureResponse userSelection = getSingleSelectResponse(responses);
			if (userSelection == null)
				throw new DataExportException(String.format("Measure of type select single had a response but all answers were false, measureId: %s veteranAssessmentId: %s", templateMeasure.getMeasureId(), veteranAssessmentId));

			String exportValue = userSelection.getMeasureAnswer().getCalculationValue();
			selectSingleExportCell.setCellValue(exportValue);

			if (otherDataExportCell != null)
				setOtherExportDataCellValue(otherDataExportCell, userSelection.getOtherValue());
		}

		List<DataExportCell> selectSingleExportCells = new ArrayList<DataExportCell>();
		selectSingleExportCells.add(selectSingleExportCell);
		if (otherDataExportCell != null)
			selectSingleExportCells.add(otherDataExportCell);

		return selectSingleExportCells;
	}

	private List<DataExportCell> buildSelectMultiMatrixExportCells(
			int veteranAssessmentId, Measure parentTemplateMeasure,
			boolean surveyAssigned, Integer tabularRowIndex) {

		// get list of child measure templates from the container measure template
		List<Measure> childMeasures = measureRepository.getChildMeasures(parentTemplateMeasure);

		List<DataExportCell> selectMultiMatrixExportCells = new ArrayList<DataExportCell>();
		if (childMeasures != null && childMeasures.size() > 0) {
			for (Measure childMeasure : childMeasures)
				selectMultiMatrixExportCells.addAll(buildSelectMultiExportCells(veteranAssessmentId, childMeasure, surveyAssigned, tabularRowIndex));
		}

		return selectMultiMatrixExportCells;
	}

	private List<DataExportCell> buildSelectMultiExportCells(
			int veteranAssessmentId, Measure templateMeasure,
			boolean surveyAssigned, Integer tabularRowIndex) {

		// Validate input
		Integer templateMeasureId = templateMeasure.getMeasureId();
		if (templateMeasure.getMeasureAnswerList() == null)
			throw new DataExportException(String.format("Measure of type SelectMulti was missing an answer list, measureId:%s veteranAssessmentId: %s", templateMeasureId, veteranAssessmentId));

		// get list of answers for the template measure
		List<MeasureAnswer> templateMeasureAnswers = measureAnswerRepository.getAnswersForMeasure(templateMeasureId);

		// create a cell for each measure_answer
		List<DataExportCell> selectMultiExportCells = new ArrayList<DataExportCell>();
		for (MeasureAnswer templateAnswer : templateMeasureAnswers) {

			// special processing for "other" type answers
			if (templateAnswer.getAnswerType() != null && templateAnswer.getAnswerType().equalsIgnoreCase("other"))
				addSelectMultiOtherCells(templateAnswer, veteranAssessmentId, surveyAssigned, selectMultiExportCells, tabularRowIndex);
			else {
				DataExportCell multiSelectExportCell = new DataExportCell();
				multiSelectExportCell.setColumnName(getCellExportName(templateAnswer, veteranAssessmentId, tabularRowIndex));

				if (surveyAssigned) {
					SurveyMeasureResponse response = surveyMeasureResponseRepository.find(veteranAssessmentId, templateAnswer.getMeasureAnswerId(), tabularRowIndex);

					if (response == null)
						multiSelectExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
					else {
						if (response.getBooleanValue())
							multiSelectExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.TRUE.getDefaultValueNum()));
						else
							multiSelectExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.FALSE.getDefaultValueNum()));
					}
				} else
					multiSelectExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));

				selectMultiExportCells.add(multiSelectExportCell);
			}
		}

		return selectMultiExportCells;
	}

	private String getCellExportName(MeasureAnswer measureAnswer,
			int veteranAssessmentId, Integer tabularRowIndex) {

		if (measureAnswer.getExportName() == null || measureAnswer.getExportName().isEmpty())
			throw new DataExportException(String.format("Export name was not set for measureAnswerId: %s veteranAssessmentId: %s", measureAnswer.getMeasureAnswerId(), veteranAssessmentId));

		String columnName = measureAnswer.getExportName();

		// additional processing for table type question
		if (tabularRowIndex != null)
			columnName = columnName + (tabularRowIndex + 1);

		return columnName;
	}

	private String getCellOtherExportName(MeasureAnswer measureAnswer,
			int veteranAssessmentId, Integer tabularRowIndex) {

		if (measureAnswer.getOtherExportName() == null || measureAnswer.getOtherExportName().isEmpty())
			throw new DataExportException(String.format("Other export name was expected but not set for measureAnswerId: %s veteranAssessmentId: %s", measureAnswer.getMeasureAnswerId(), veteranAssessmentId));

		String otherColumnName = measureAnswer.getOtherExportName();

		// additional processing for table type question
		if (tabularRowIndex != null)
			otherColumnName = otherColumnName + (tabularRowIndex + 1);

		return otherColumnName;
	}

	private void setOtherExportDataCellValue(DataExportCell otherExportCell,
			String otherValue) {
		if (otherValue == null || otherValue.isEmpty())
			otherExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
		else
			otherExportCell.setCellValue(otherValue);
	}

	private SurveyMeasureResponse getSingleSelectResponse(
			List<SurveyMeasureResponse> responses) {
		// get the true value
		for (SurveyMeasureResponse response : responses) {
			if (response.getBooleanValue() != null && response.getBooleanValue().booleanValue()) {
				return response;
			}
		}

		// should never get here
		return null;
	}

	private DataExportCell getDataExportCellForOther(Measure templateMeasure,
			Integer veteranAssessmentId, Integer tabularRowIndex) {
		List<MeasureAnswer> templateAnswers = templateMeasure.getMeasureAnswerList();

		DataExportCell otherExportCell = null;

		// there should only be one other field for single select type questions
		for (MeasureAnswer templateAnswer : templateAnswers) {
			if (templateAnswer.getAnswerType() != null && !templateAnswer.getAnswerType().isEmpty() && templateAnswer.getAnswerType().equalsIgnoreCase("other")) {

				otherExportCell = new DataExportCell();
				String otherColumnName = getCellOtherExportName(templateAnswer, veteranAssessmentId, tabularRowIndex);
				otherExportCell.setColumnName(otherColumnName);
				break;
			}
		}

		return otherExportCell;
	}

	private void addSelectMultiOtherCells(MeasureAnswer templateAnswer,
			int veteranAssessmentId, boolean surveyAssigned,
			List<DataExportCell> selectMultiExportCells, Integer tabularRowIndex) {

		DataExportCell otherExportCell = new DataExportCell();
		otherExportCell.setColumnName(templateAnswer.getExportName());
		DataExportCell otherSpecificExportCell = new DataExportCell();
		otherSpecificExportCell.setColumnName(getCellOtherExportName(templateAnswer, veteranAssessmentId, tabularRowIndex));

		if (surveyAssigned) {
			SurveyMeasureResponse response = surveyMeasureResponseRepository.find(veteranAssessmentId, templateAnswer.getMeasureAnswerId(), tabularRowIndex);

			if (response == null) {
				otherExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
				otherSpecificExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
			} else {
				// has to be broken into two checks, one for boolean and one for text because both items need to be
				// exported.
				// Check to see if this answer was selected
				if (response.getBooleanValue())
					otherExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.TRUE.getDefaultValueNum()));
				else
					otherExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.FALSE.getDefaultValueNum()));

				// Get the user entered other value
				String textValue = response.getOtherValue();
				if (textValue == null || textValue.isEmpty())
					otherSpecificExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
				else
					otherSpecificExportCell.setCellValue(textValue);
			}
		} else {
			otherExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
			otherSpecificExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
		}

		selectMultiExportCells.add(otherExportCell);
		selectMultiExportCells.add(otherSpecificExportCell);
	}

	private DataExportCell buildTextExportCell(int veteranAssessmentId,
			Measure templateMeasure, boolean surveyAssigned,
			Integer tabularRowIndex) {

		// Validate input
		if (templateMeasure.getMeasureAnswerList() == null || templateMeasure.getMeasureAnswerList().size() != 1)
			throw new DataExportException(String.format("Measure of type free text either was either missing an answer or had more than one measureId:%s veteranAssessmentId: %s", templateMeasure.getMeasureId(), veteranAssessmentId));

		DataExportCell textBasedExportCell = new DataExportCell();
		List<SurveyMeasureResponse> responses = null;
		if (surveyAssigned) {
			// Check to see if processing for a table type question
			if (tabularRowIndex == null)
				responses = surveyMeasureResponseRepository.getForVeteranAssessmentAndMeasure(veteranAssessmentId, templateMeasure.getMeasureId());
			else
				responses = surveyMeasureResponseRepository.findForAssessmentIdMeasureRow(veteranAssessmentId, templateMeasure.getMeasureId(), tabularRowIndex);
		}

		if (responses == null || responses.size() == 0)
			textBasedExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
		else {
			// Can only have one response row for this question type
			SurveyMeasureResponse response = responses.get(0);

			// Get the user entered value
			if (response.getBooleanValue() != null)
				textBasedExportCell.setCellValue(String.valueOf(response.getBooleanValue()));
			else if (response.getNumberValue() != null)
				textBasedExportCell.setCellValue(String.valueOf(response.getNumberValue()));
			else if (response.getTextValue() != null && !response.getTextValue().isEmpty())
				textBasedExportCell.setCellValue(String.valueOf(response.getTextValue()));
			else
				textBasedExportCell.setCellValue(String.valueOf(ExportDataDefaultValuesEnum.MISSINGVALUE.getDefaultValueNum()));
		}

		// There should only ever be one answer for a question of this type
		textBasedExportCell.setColumnName(getCellExportName(templateMeasure.getMeasureAnswerList().get(0), veteranAssessmentId, tabularRowIndex));

		return textBasedExportCell;
	}

	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.bf = arg0;
	}
}