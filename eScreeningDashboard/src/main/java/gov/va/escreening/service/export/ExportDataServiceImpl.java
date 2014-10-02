package gov.va.escreening.service.export;

import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.dto.dashboard.DataExportFilterOptions;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.ExportType;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.form.ExportDataFormBean;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.ExportLogRepository;
import gov.va.escreening.repository.ExportTypeRepository;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.ProgramRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.repository.VeteranRepository;
import gov.va.escreening.service.DataDictionaryService;
import gov.va.escreening.service.MeasureService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.service.VeteranAssessmentSurveyService;
import gov.va.escreening.util.SurveyResponsesHelper;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.VariableResolverService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

@Service("exportDataService")
public class ExportDataServiceImpl implements ExportDataService, MessageSourceAware {
	private static final Logger logger = LoggerFactory.getLogger(ExportDataServiceImpl.class);

	private MessageSource msgSrc;

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

	@Resource(type = VariableResolverService.class)
	VariableResolverService vrs;

	@Resource(type = AssessmentVariableRepository.class)
	AssessmentVariableRepository avr;

	@Resource(type = DataDictionaryService.class)
	DataDictionaryService dds;

	@Resource(type = SurveyResponsesHelper.class)
	SurveyResponsesHelper srh;

	@Override
	@Transactional
	public AssessmentDataExport getAssessmentDataExport(
			ExportDataFormBean exportDataFormBean) {

		AssessmentDataExport assessmentDataExport = new AssessmentDataExport();

		if (exportDataFormBean.getHasParameter()) {
			// 10) Use the passed in filter criteria to pull in the matching assessments
			List<VeteranAssessment> matchingAssessments = veteranAssessmentService.searchVeteranAssessmentForExport(exportDataFormBean);

			// 11) collect all the aggregates for these assessments
			Map<Integer, Map<String, String>> aggregates = deriveAggregates(matchingAssessments);

			// 12) get a fresh data dictionary representing the fresh meta data
			Map<String, Table<String, String, String>> dataDictionary = dds.createDataDictionary();

			// 20) prepare exportData from matching assessments
			assessmentDataExport.setTableContents(createDataExportReport(matchingAssessments, exportDataFormBean.getExportTypeId(), aggregates, dataDictionary));
			DataExportFilterOptions filterOptions = createFilterOptions(exportDataFormBean);
			assessmentDataExport.setFilterOptions(filterOptions);

			// 30) log this activity
			ExportLog exportLog = logDataExport(assessmentDataExport);

			if (exportLog != null) {
				assessmentDataExport.setExportLogId(exportLog.getExportLogId());
			}
		}

		return assessmentDataExport;
	}

	private Map<Integer, Map<String, String>> deriveAggregates(
			List<VeteranAssessment> matchingAssessments) {

		Map<Integer, Map<String, String>> aggregates = Maps.newHashMap();

		Iterable<AssessmentVariable> avList = avr.findAllFormulae();

		for (VeteranAssessment va : matchingAssessments) {
			Iterable<AssessmentVariableDto> aggreagteList = vrs.resolveVariablesFor(va.getVeteranAssessmentId(), avList);
			aggregates.put(va.getVeteranAssessmentId(), createAggregateMap(aggreagteList));
		}
		return aggregates;
	}

	private Map<String, String> createAggregateMap(
			Iterable<AssessmentVariableDto> aggreagteList) {
		Map<String, String> aggregateMap = Maps.newHashMap();
		for (AssessmentVariableDto dto : aggreagteList) {
			aggregateMap.put(dto.getDisplayName(), dto.getDisplayText());
		}
		return aggregateMap;
	}

	private DataExportFilterOptions createFilterOptions(
			ExportDataFormBean exportDataFormBean) {

		DataExportFilterOptions filterOptions = new DataExportFilterOptions();

		if (exportDataFormBean.getToAssessmentDate() != null)
			filterOptions.setAssessmentEnd(exportDataFormBean.getToAssessmentDate());

		if (exportDataFormBean.getFromAssessmentDate() != null)
			filterOptions.setAssessmentStart(exportDataFormBean.getFromAssessmentDate());

		if (exportDataFormBean.getClinicianId() != null)
			filterOptions.setClinicianUserId(exportDataFormBean.getClinicianId());

		if (exportDataFormBean.getCommentText() != null)
			filterOptions.setComment(exportDataFormBean.getCommentText());

		if (exportDataFormBean.getCreatedByUserId() != null)
			filterOptions.setCreatedByUserId(exportDataFormBean.getCreatedByUserId());

		if (exportDataFormBean.getExportedByUserId() != null)
			filterOptions.setExportedByUserId(exportDataFormBean.getExportedByUserId());

		if (exportDataFormBean.getExportLogId() != null)
			filterOptions.setExportLogId(exportDataFormBean.getExportLogId());

		if (exportDataFormBean.getExportTypeId() != null)
			filterOptions.setExportTypeId(exportDataFormBean.getExportTypeId());

		if (exportDataFormBean.getProgramId() != null)
			filterOptions.setProgramId(exportDataFormBean.getProgramId());

		if (exportDataFormBean.getVeteranId() != null)
			filterOptions.setVeteranId(exportDataFormBean.getVeteranId());

		String filePath = String.format("va_export_data_as_of_ts_%s.csv", System.nanoTime());
		filterOptions.setFilePath(filePath);

		return filterOptions;
	}

	@Override
	@Transactional
	public ExportLog logDataExport(AssessmentDataExport dataExport) {

		// Add an entry to the exportLog table
		ExportLog exportLog = createExportLogFromOptions(dataExport.getFilterOptions());

		if (addExportLogDataToExportLog(exportLog, dataExport)) {
			exportLogRepository.create(exportLog);
			return exportLog;
		}
		return null;
	}

	private boolean addExportLogDataToExportLog(ExportLog exportLog,
			AssessmentDataExport dataExport) {
		String header = createHeaderFromDataExport(dataExport);
		if (header != null) {
			List<String> data = createDataFromDataExport(dataExport);
			dataExport.setHeaderAndData(header, data);
			exportLog.setExportLogData(dataExport.getHeader(), dataExport.getData());
			return true;
		}
		return false;
	}

	private List<String> createDataFromDataExport(
			AssessmentDataExport dataExport) {

		List<List<DataExportCell>> tableContent = dataExport.getTableContent();

		if (tableContent != null && !tableContent.isEmpty()) {
			List<String> rows = new ArrayList<String>();
			for (List<DataExportCell> row : tableContent) {
				StringBuilder sb = new StringBuilder();
				for (DataExportCell cell : row) {
					sb.append(cell.getCellValue().replaceAll(",", "-"));
					sb.append(",");
				}
				if (logger.isDebugEnabled()) {
					if (logger.isDebugEnabled())
						logger.debug(String.format("row of length %s is being added [%s]", sb.length(), sb));
				}
				rows.add(sb.toString());
			}
			return rows;

		} else {
			// if export log is downloaded again
			return dataExport.getData();
		}
	}

	private String createHeaderFromDataExport(AssessmentDataExport dataExport) {
		List<List<DataExportCell>> tableContent = dataExport.getTableContent();

		String header = null;

		if (tableContent != null && !tableContent.isEmpty()) {
			List<DataExportCell> firstRow = tableContent.iterator().next();
			StringBuilder sb = new StringBuilder();
			for (DataExportCell dec : firstRow) {
				sb.append(dec.getColumnName().replaceAll(",", "-"));
				sb.append(",");
			}
			header = sb.toString();
		} else {
			// if export log is requested to be downloaded again
			header = dataExport.getHeader();
		}
		if (header != null && logger.isDebugEnabled()) {
			if (logger.isDebugEnabled())
				logger.debug(String.format("header of length %s is being added [%s]", header.length(), header));
		}
		return header;
	}

	private ExportLog createExportLogFromOptions(DataExportFilterOptions options) {
		Preconditions.checkNotNull(options, "DataExportFilterOptions value cannot be null");

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

	private List<List<DataExportCell>> createDataExportReport(
			List<VeteranAssessment> matchingAssessments,
			Integer identifiedExportType,
			Map<Integer, Map<String, String>> vid2AggregatesMap,
			Map<String, Table<String, String, String>> dataDictionary) {

		List<List<DataExportCell>> tableContent = new ArrayList<List<DataExportCell>>();

		// build an export row for each assessment
		for (VeteranAssessment assessment : matchingAssessments) {
			Map<String, String> aggregatesMap = vid2AggregatesMap.get(assessment.getVeteranAssessmentId());
			List<SurveyMeasureResponse> smrList = assessment.getSurveyMeasureResponseList();
			List<DataExportCell> exportDataRowCells = buildExportDataForOneAssessment(assessment, identifiedExportType, smrList, aggregatesMap, dataDictionary);
			tableContent.add(exportDataRowCells);
		}

		return tableContent;
	}

	private List<DataExportCell> buildExportDataForOneAssessment(
			VeteranAssessment assessment, Integer identifiedExportType,
			List<SurveyMeasureResponse> smrList,
			Map<String, String> aggregatesMap,
			Map<String, Table<String, String, String>> ddMap) {

		// has user asked to show the ppi info or not
		boolean show = ExportTypeEnum.DEIDENTIFIED.getExportTypeId() != identifiedExportType;

		// quickly gather mandatory columns data
		List<DataExportCell> exportDataRowCells = buildMandatoryColumns(assessment, show);

		for (String surveyName : ddMap.keySet()) {
			Map<String, String> usrRespMap = srh.prepareSurveyResponsesMap(surveyName, smrList, show);

			// get the table data (one sheet) for this survey
			Table<String, String, String> dataDictionary = ddMap.get(surveyName);

			// get the names of export names for each row
			// the returned data will be rowId=exportName for the export column
			Map<String, String> columnData = dataDictionary.column(msgSrc.getMessage("data.dict.column.var.name", null, null));

			// traverse through each exportName, and try to find the run time response for the exportName. In case the
			// user has not responded, leave 999
			for (String exportName : columnData.values()) {
				if (!exportName.isEmpty()) {
					DataExportCell oneCell = srh.create(usrRespMap, aggregatesMap, exportName, show);
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("adding data for data dictionary column %s->%s=%s", surveyName, exportName, oneCell));
					}
					exportDataRowCells.add(oneCell);
				}
			}
		}
		return exportDataRowCells;
	}

	@Override
	@Transactional
	public AssessmentDataExport downloadExportData(Integer userId,
			int exportLogId, String comment) {

		ExportLog exportLog = exportLogRepository.findOne(exportLogId);

		AssessmentDataExport ade = AssessmentDataExport.createFromExportLog(exportLog);

		ade.getFilterOptions().setComment(comment);
		ade.getFilterOptions().setCreatedByUserId(userId);

		ExportLog newExportLog = logDataExport(ade);

		ade.setExportLogId(newExportLog.getExportLogId());

		return ade;
	}

	@Override
	public void setMessageSource(MessageSource msgSrc) {
		this.msgSrc = msgSrc;
	}

	@Override
	public List<DataExportCell> createDataExportForOneAssessment(
			VeteranAssessment va, int identifiedExportType) {

		Map<Integer, Map<String, String>> aggregatesMap = deriveAggregates(Arrays.asList(va));
		Map<String, Table<String, String, String>> dataDictionary = dds.createDataDictionary();
		List<SurveyMeasureResponse> smrList = va.getSurveyMeasureResponseList();
		List<DataExportCell> row = buildExportDataForOneAssessment(va, identifiedExportType, smrList, aggregatesMap.get(va.getVeteranAssessmentId()), dataDictionary);
		return row;

	}

	public List<DataExportCell> buildMandatoryColumns(
			VeteranAssessment assessment, boolean show) {

		List<DataExportCell> mandatoryData = new ArrayList<DataExportCell>();

		mandatoryData.addAll(collectPpi(assessment, show));

		mandatoryData.add(new DataExportCell("assessment_id", srh.getOrMiss(srh.getStrFromInt(assessment.getVeteranAssessmentId()))));
		mandatoryData.add(new DataExportCell("created_by", srh.getOrMiss(assessment.getCreatedByUser() != null ? assessment.getCreatedByUser().getUserFullName() : null)));
		mandatoryData.add(new DataExportCell("battery_name", srh.getOrMiss(assessment.getBattery() != null ? assessment.getBattery().getName() : null)));
		mandatoryData.add(new DataExportCell("program_name", srh.getOrMiss(assessment.getProgram() != null ? assessment.getProgram().getName() : null)));
		mandatoryData.add(new DataExportCell("vista_clinic", srh.getOrMiss(assessment.getClinic() != null ? assessment.getClinic().getName() : null)));
		mandatoryData.add(new DataExportCell("note_title", srh.getOrMiss(assessment.getNoteTitle() != null ? assessment.getNoteTitle().getName() : null)));
		mandatoryData.add(new DataExportCell("clinician_name", srh.getOrMiss(assessment.getClinician() != null ? assessment.getClinician().getUserFullName() : null)));
		mandatoryData.add(new DataExportCell("date_created", srh.getOrMiss(srh.getDtAsStr(assessment.getDateCreated()))));
		mandatoryData.add(new DataExportCell("time_created", srh.getOrMiss(srh.getTmAsStr(assessment.getDateCreated()))));
		mandatoryData.add(new DataExportCell("date_completed", srh.getOrMiss(srh.getDtAsStr(assessment.getDateCompleted()))));
		mandatoryData.add(new DataExportCell("time_completed", srh.getOrMiss(srh.getTmAsStr(assessment.getDateCompleted()))));
		mandatoryData.add(new DataExportCell("duration", srh.getOrMiss(srh.getStrFromInt(assessment.getDuration()))));

		mandatoryData.add(new DataExportCell("vista_DOB", show ? srh.getOrMiss(srh.getDtAsStr(assessment.getVeteran().getBirthDate())) : srh.miss()));
		return mandatoryData;
	}

	private List<DataExportCell> collectPpi(VeteranAssessment assessment,
			boolean show) {
		Veteran v = assessment.getVeteran();

		List<DataExportCell> mandatoryIdendifiedData = new ArrayList<DataExportCell>();

		// if veteran has taken the 'Identification' survey then skip this as veteran survey response
		// will take precedence over the clinician entered data

		mandatoryIdendifiedData.addAll(Arrays.asList(new DataExportCell("vista_lastname", show ? srh.getOrMiss(v.getLastName()) : srh.miss()),//
				new DataExportCell("vista_firstname", show ? srh.getOrMiss(v.getFirstName()) : srh.miss()),//
				new DataExportCell("vista_midname", show ? srh.getOrMiss(v.getMiddleName()) : srh.miss()),//
				new DataExportCell("vista_SSN", show ? srh.getOrMiss(v.getSsnLastFour()) : srh.miss()), new DataExportCell("vista_ien", v.getVeteranIen())));
		return mandatoryIdendifiedData;
	}

}