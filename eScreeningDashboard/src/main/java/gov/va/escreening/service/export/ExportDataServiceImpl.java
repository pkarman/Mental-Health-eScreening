package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.dto.dashboard.DataExportFilterOptions;
import gov.va.escreening.entity.ExportLog;
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

		List<VeteranAssessment> matchingAssessments = null;

		// 1) Use the passed in filter criteria to pull in the matching assessments
		if (exportDataFormBean.getHasParameter()) {
			matchingAssessments = veteranAssessmentService.searchVeteranAssessmentForExport(exportDataFormBean);
		} else {
			return assessmentDataExport;
		}

		// 2) prepare exportData from matching assessments
		assessmentDataExport.setReport(createDataExportReport(matchingAssessments, exportDataFormBean.getExportTypeId()));
		DataExportFilterOptions filterOptions = createFilterOptions(exportDataFormBean);
		assessmentDataExport.setFilterOptions(filterOptions);

		// 3) log this activity
		logDataExport(assessmentDataExport);

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
	public void logDataExport(AssessmentDataExport dataExport) {

		// Add an entry to the exportLog table
		ExportLog exportLog = createExportLogFromOptions(dataExport.getFilterOptions());
		exportLogRepository.create(exportLog);
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
}