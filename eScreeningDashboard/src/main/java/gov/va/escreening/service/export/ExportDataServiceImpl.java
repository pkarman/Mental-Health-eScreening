package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.dto.dashboard.DataExportFilterOptions;
import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.ExportLogData;
import gov.va.escreening.entity.ExportType;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Transactional
@Service("exportDataService")
public class ExportDataServiceImpl implements ExportDataService, BeanFactoryAware {
	private static final Logger logger = LoggerFactory.getLogger(ExportDataServiceImpl.class);
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

	@Override
	public AssessmentDataExport getAssessmentDataExport(
			ExportDataFormBean exportDataFormBean) {

		AssessmentDataExport assessmentDataExport = new AssessmentDataExport();

		if (exportDataFormBean.getHasParameter()) {
			// 1) Use the passed in filter criteria to pull in the matching assessments
			List<VeteranAssessment> matchingAssessments = veteranAssessmentService.searchVeteranAssessmentForExport(exportDataFormBean);

			// 2) prepare exportData from matching assessments
			assessmentDataExport.setTableContents(createDataExportReport(matchingAssessments, exportDataFormBean.getExportTypeId()));
			DataExportFilterOptions filterOptions = createFilterOptions(exportDataFormBean);
			assessmentDataExport.setFilterOptions(filterOptions);

			// 3) log this activity
			ExportLog exportLog = logDataExport(assessmentDataExport);

			assessmentDataExport.setExportLogId(exportLog.getExportLogId());
		}

		return assessmentDataExport;
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
	public ExportLog logDataExport(AssessmentDataExport dataExport) {

		// Add an entry to the exportLog table
		ExportLog exportLog = createExportLogFromOptions(dataExport.getFilterOptions());

		addExportLogDataToExportLog(exportLog, dataExport);

		exportLogRepository.create(exportLog);
		return exportLog;
	}

	private void addExportLogDataToExportLog(ExportLog exportLog,
			AssessmentDataExport dataExport) {

		if (!dataExport.hasData()) {
			return;
		}

		String header = createHeaderFromDataExport(dataExport);
		if (header != null) {
			exportLog.addExportLogData(new ExportLogData(header));
			List<String> data = createDataFromDataExport(dataExport);
			for (String eldRow : data) {
				exportLog.addExportLogData(new ExportLogData(eldRow));
			}
			dataExport.setHeaderAndData(header, data);
		}

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
			Integer identifiedExportType) {

		List<List<DataExportCell>> tableContent = new ArrayList<List<DataExportCell>>();

		// build an export row for each assessment
		for (VeteranAssessment assessment : matchingAssessments) {
			List<DataExportCell> row = createDataExportForOneAssessment(assessment, identifiedExportType);
			tableContent.add(row);
		}

		return tableContent;
	}

	@Override
	public List<DataExportCell> createDataExportForOneAssessment(
			VeteranAssessment assessment, Integer identifiedExportType) {

		// Iterate the list of modules and add them to the export row
		// each row consists of cells, and each cell is represented by a DataExportCell
		// every survey (also called interchangeably as 'module' here [database survey = java module]) has its own set
		// of data export cells
		List<DataExportCell> exportRow = new ArrayList<DataExportCell>();

		for (ModuleEnum module : ModuleEnum.values()) {
			List<DataExportCell> cells = module.apply(this.bf, assessment, identifiedExportType);
			if (cells != null && !cells.isEmpty()) {
				exportRow.addAll(cells);
			}
		}

		return exportRow;
	}

	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.bf = arg0;
	}

	@Override
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
}