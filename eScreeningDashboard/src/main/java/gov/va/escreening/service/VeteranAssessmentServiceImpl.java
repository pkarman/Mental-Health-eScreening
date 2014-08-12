package gov.va.escreening.service;

import static com.google.common.base.Preconditions.checkArgument;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.constants.RuleConstants;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.domain.MentalHealthAssessment;
import gov.va.escreening.domain.VeteranAssessmentDto;
import gov.va.escreening.dto.AlertDto;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.VeteranAssessmentInfo;
import gov.va.escreening.dto.dashboard.AssessmentSearchResult;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.ClinicalNote;
import gov.va.escreening.entity.Consult;
import gov.va.escreening.entity.DashboardAlert;
import gov.va.escreening.entity.Event;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.SurveyPage;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentAuditLog;
import gov.va.escreening.entity.VeteranAssessmentAuditLogHelper;
import gov.va.escreening.entity.VeteranAssessmentMeasureVisibility;
import gov.va.escreening.entity.VeteranAssessmentNote;
import gov.va.escreening.entity.VeteranAssessmentSurvey;
import gov.va.escreening.form.AssessmentReportFormBean;
import gov.va.escreening.form.ExportDataFormBean;
import gov.va.escreening.repository.AssessmentStatusRepository;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.ClinicalNoteRepository;
import gov.va.escreening.repository.EventRepository;
import gov.va.escreening.repository.NoteTitleRepository;
import gov.va.escreening.repository.ProgramRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.repository.VeteranAssessmentAuditLogRepository;
import gov.va.escreening.repository.VeteranAssessmentDashboardAlertRepository;
import gov.va.escreening.repository.VeteranAssessmentMeasureVisibilityRepository;
import gov.va.escreening.repository.VeteranAssessmentNoteRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.repository.VeteranAssessmentSurveyRepository;
import gov.va.escreening.repository.VeteranRepository;
import gov.va.escreening.util.VeteranUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Transactional
@Service("veteranAssessmentService")
public class VeteranAssessmentServiceImpl implements VeteranAssessmentService {

	private static final Logger logger = LoggerFactory.getLogger(VeteranAssessmentServiceImpl.class);

	@Autowired
	private AssessmentStatusRepository assessmentStatusRepository;
	@Autowired
	private BatteryRepository batteryRepository;
	@Autowired
	private ClinicalNoteRepository clinicalNoteRepository;
	@Autowired
	private ClinicRepository clinicRepository;
	@Autowired
	private NoteTitleRepository noteTitleRepository;
	@Autowired
	private ProgramRepository programRepository;
	@Autowired
	private SurveyRepository surveyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VeteranAssessmentRepository veteranAssessmentRepository;
	@Autowired
	private VeteranRepository veteranRepository;
	@Autowired
	private VeteranAssessmentAuditLogRepository veteranAssessmentAuditLogRepository;
	@Autowired
	private VeteranAssessmentNoteRepository veteranAssessmentNoteRepository;
	@Autowired
	private VeteranAssessmentSurveyRepository veteranAssessmentSurveyRepository;
	@Autowired
	private VeteranAssessmentMeasureVisibilityRepository veteranAssessmentMeasureVisibilityRepository;
	@Autowired
	private EventRepository eventRepository;

	@Resource(name = "esVeteranAssessmentDashboardAlertRepository")
	VeteranAssessmentDashboardAlertRepository vadar;

	@Override
	public List<VeteranAssessment> getAvailableAssessmentsForVeteran(
			Integer veteranId) {

		// These are the valid assessment states that the engine can run
		// against.
		List<Integer> assessmentStatusIdList = new ArrayList<Integer>();
		assessmentStatusIdList.add(AssessmentStatusEnum.CLEAN.getAssessmentStatusId());
		assessmentStatusIdList.add(AssessmentStatusEnum.INCOMPLETE.getAssessmentStatusId());

		return veteranAssessmentRepository.findByVeteranIdAndAssessmentStatusIdList(veteranId, assessmentStatusIdList);
	}

	@Override
	public List<VeteranAssessment> getAssessmentsForProgramIdList(
			List<Integer> programIdList) {

		return veteranAssessmentRepository.findByProgramIdList(programIdList);
	}

	@Transactional(readOnly = true)
	@Override
	public List<VeteranAssessmentDto> getAssessmentListForProgramIdList(
			List<Integer> programIdList) {

		List<VeteranAssessmentDto> veteranAssessmentDtoList = new ArrayList<VeteranAssessmentDto>();

		List<VeteranAssessment> veteranAssessmentList = getAssessmentsForProgramIdList(programIdList);

		for (VeteranAssessment veteranAssessment : veteranAssessmentList) {
			VeteranAssessmentDto veteranAssessmentDto = new VeteranAssessmentDto();

			veteranAssessmentDto.setAssessmentStatus(veteranAssessment.getAssessmentStatus().getName());

			if (veteranAssessment.getClinician() != null) {
				veteranAssessmentDto.setClinicianFirstName(veteranAssessment.getClinician().getFirstName());
				veteranAssessmentDto.setClinicianLastName(veteranAssessment.getClinician().getLastName());
			}

			if (veteranAssessment.getClinic() != null) {
				veteranAssessmentDto.setClinicName(veteranAssessment.getClinic().getName());
				veteranAssessmentDto.setProgramName(veteranAssessment.getClinic().getProgram().getName());
			}

			veteranAssessmentDto.setDuration(veteranAssessment.getDuration());
			veteranAssessmentDto.setPercentComplete(veteranAssessment.getPercentComplete());
			veteranAssessmentDto.setVeteranFirstName(veteranAssessment.getVeteran().getFirstName());
			veteranAssessmentDto.setVeteranLastName(veteranAssessment.getVeteran().getLastName());
			veteranAssessmentDto.setVeteranSsnLastFour(veteranAssessment.getVeteran().getSsnLastFour());

			veteranAssessmentDto.setDateCreated(veteranAssessment.getDateCreated());

			veteranAssessmentDtoList.add(veteranAssessmentDto);
		}

		return veteranAssessmentDtoList;
	}

	@Override
	public void updateVeteranAssessmentSurveyIdList(int veteranAssessmentId,
			List<Integer> surveyIdList) {

		logger.trace("updateVeteranAssessmentSurveyIdList called");

		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		// union the survey ID list given with the required ones
		Set<Integer> mergedList = new HashSet<Integer>(surveyIdList);
		List<Survey> requiredSurveys = surveyRepository.getRequiredSurveys();
		for (Survey required : requiredSurveys) {
			mergedList.add(required.getSurveyId());
		}

		// add all surveys from the given list to the assessment
		updateAssessmentSurveys(veteranAssessment, mergedList);

		// Update the database.
		veteranAssessmentRepository.update(veteranAssessment);

		// log the assessment creation event to the audit log
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_SURVEY_ASSIGNED, AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);
	}

	@Override
	public VeteranAssessment create(int veteranId, int clinicianId,
			int clinicId, int createdByUserId) {

		VeteranAssessment veteranAssessment = new VeteranAssessment();

		veteranAssessment.setVeteran(veteranRepository.findOne(veteranId));
		veteranAssessment.setClinician(userRepository.findOne(clinicianId));
		veteranAssessment.setClinic(clinicRepository.findOne(clinicId));
		veteranAssessment.setCreatedByUser(userRepository.findOne(createdByUserId));
		veteranAssessment.setAssessmentStatus(assessmentStatusRepository.findOne(1));

		// Save to database.
		veteranAssessmentRepository.create(veteranAssessment);

		// log the assessment creation event to the audit log
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_CREATED, AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);

		return veteranAssessment;
	}

	@Override
	public VeteranAssessment create(int veteranId, int clinicianId,
			int clinicId, int createdByUserId, int accessMode) {

		VeteranAssessment veteranAssessment = new VeteranAssessment();

		veteranAssessment.setVeteran(veteranRepository.findOne(veteranId));
		veteranAssessment.setClinician(userRepository.findOne(clinicianId));
		veteranAssessment.setClinic(clinicRepository.findOne(clinicId));
		veteranAssessment.setCreatedByUser(userRepository.findOne(createdByUserId));
		veteranAssessment.setAssessmentStatus(assessmentStatusRepository.findOne(1));
		veteranAssessment.setAccessMode(accessMode);

		// Save to database.
		veteranAssessmentRepository.create(veteranAssessment);

		// log the assessment creation event to the audit log
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_CREATED, AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);

		return veteranAssessment;
	}

	@Override
	public Integer create(int veteranId, int createdByUserId) {

		// 1. Create a new assessment for the veteran.
		VeteranAssessment veteranAssessment = new VeteranAssessment();

		veteranAssessment.setVeteran(veteranRepository.findOne(veteranId));
		veteranAssessment.setCreatedByUser(userRepository.findOne(createdByUserId));
		veteranAssessment.setAssessmentStatus(assessmentStatusRepository.findOne(AssessmentStatusEnum.CLEAN.getAssessmentStatusId()));

		// Save to database.
		veteranAssessmentRepository.create(veteranAssessment);

		// log the assessment creation event to the audit log
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_CREATED, AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);

		return veteranAssessment.getVeteranAssessmentId();
	}

	@Transactional(readOnly = true)
	@Override
	public List<VeteranAssessment> searchVeteranAssessmentForExport(
			ExportDataFormBean exportDataFormBean) {

		// Get the data from the repository.
		List<VeteranAssessment> veteranAssessmentSearchResult = veteranAssessmentRepository.searchVeteranAssessment(exportDataFormBean.getClinicianId(), exportDataFormBean.getCreatedByUserId(), exportDataFormBean.getProgramId(), exportDataFormBean.getFromAssessmentDate(), exportDataFormBean.getToAssessmentDate(), exportDataFormBean.getVeteranId(), exportDataFormBean.getProgramIdList());

		return veteranAssessmentSearchResult;
	}

	@Transactional(readOnly = true)
	@Override
	public SearchResult<AssessmentSearchResult> searchVeteranAssessment(
			AssessmentReportFormBean assessmentReportFormBean,
			SearchAttributes searchAttributes) {

		// Get the data from the repository.
		SearchResult<VeteranAssessment> veteranAssessmentSearchResult = veteranAssessmentRepository.searchVeteranAssessment(
				assessmentReportFormBean.getVeteranAssessmentId(), 
				assessmentReportFormBean.getVeteranId(), 
				assessmentReportFormBean.getProgramId(), 
				assessmentReportFormBean.getClinicianId(), 
				assessmentReportFormBean.getCreatedByUserId(), 
				assessmentReportFormBean.getFromAssessmentDate(), 
				assessmentReportFormBean.getToAssessmentDate(), 
				assessmentReportFormBean.getProgramIdList(), 
				searchAttributes);

		return prepareAssessmentSearchResult(veteranAssessmentSearchResult);

	}

	private SearchResult<AssessmentSearchResult> prepareAssessmentSearchResult(
			SearchResult<VeteranAssessment> veteranAssessmentSearchResult) {

		List<AssessmentSearchResult> assessmentSearchResults = new ArrayList<AssessmentSearchResult>();

		SearchResult<AssessmentSearchResult> searchResult = new SearchResult<AssessmentSearchResult>();
		searchResult.setTotalNumRowsFound(veteranAssessmentSearchResult.getTotalNumRowsFound());

		// Convert the entity to dto.
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

		if (veteranAssessmentSearchResult.getResultList() != null) {
			for (VeteranAssessment veteranAssessment : veteranAssessmentSearchResult.getResultList()) {

				AssessmentSearchResult assessmentSearchResult = new AssessmentSearchResult();

				// set assessment date string
				if (veteranAssessment.getDateUpdated() == null)
					assessmentSearchResult.setAssessmentDate("");
				else
					assessmentSearchResult.setAssessmentDate(sdf.format(veteranAssessment.getDateUpdated()));

				// set assessment status text
				if (veteranAssessment.getAssessmentStatus().getName() == null)
					assessmentSearchResult.setAssessmentStatusName("");
				else
					assessmentSearchResult.setAssessmentStatusName(veteranAssessment.getAssessmentStatus().getName());

				// set Clinician name
				if (veteranAssessment.getClinician().getFirstName() != null && veteranAssessment.getClinician().getLastName() != null) {
					assessmentSearchResult.setClinicianName(veteranAssessment.getClinician().getLastName() + ", " + veteranAssessment.getClinician().getFirstName());
				} else {
					if (veteranAssessment.getClinician().getLastName() == null)
						assessmentSearchResult.setClinicianName("");
					else
						assessmentSearchResult.setClinicianName(veteranAssessment.getClinician().getLastName());
				}

				// set created by User name
				if (veteranAssessment.getCreatedByUser().getFirstName() != null && veteranAssessment.getCreatedByUser().getLastName() != null) {
					assessmentSearchResult.setCreatedBy(veteranAssessment.getCreatedByUser().getLastName() + ", " + veteranAssessment.getCreatedByUser().getFirstName());
				} else {
					if (veteranAssessment.getCreatedByUser().getLastName() == null)
						assessmentSearchResult.setCreatedBy("");
					else
						assessmentSearchResult.setCreatedBy(veteranAssessment.getCreatedByUser().getLastName());
				}

				assessmentSearchResult.setVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());
				assessmentSearchResult.setVeteranId(veteranAssessment.getVeteran().getVeteranId());

				// set Veteran name
				if (veteranAssessment.getVeteran().getFirstName() != null && veteranAssessment.getVeteran().getLastName() != null) {
					assessmentSearchResult.setVeteranName(veteranAssessment.getVeteran().getLastName() + ", " + veteranAssessment.getVeteran().getFirstName());
				} else {
					if (veteranAssessment.getVeteran().getLastName() == null)
						assessmentSearchResult.setVeteranName("");
					else
						assessmentSearchResult.setVeteranName(veteranAssessment.getVeteran().getLastName());
				}

				if (veteranAssessment.getClinic() != null) {
					assessmentSearchResult.setClinicName(veteranAssessment.getClinic().getName());

					if (veteranAssessment.getClinic().getProgram() != null) {
						assessmentSearchResult.setProgramName(veteranAssessment.getClinic().getProgram().getName());
					}
				}

				assessmentSearchResult.setSsnLastFour(veteranAssessment.getVeteran().getSsnLastFour());

				if (veteranAssessment.getDuration() == null) {
					assessmentSearchResult.setDuration(0);
				} else {
					assessmentSearchResult.setDuration(veteranAssessment.getDuration());
				}

				if (veteranAssessment.getPercentComplete() == null) {
					assessmentSearchResult.setPercentComplete(0);
				} else {
					assessmentSearchResult.setPercentComplete(veteranAssessment.getPercentComplete());
				}

				assessmentSearchResult.setAlerts(new ArrayList<AlertDto>());

				if (veteranAssessment.getDashboardAlerts() != null && veteranAssessment.getDashboardAlerts().size() > 0) {
					for (DashboardAlert dashboardAlert : veteranAssessment.getDashboardAlerts()) {
						AlertDto alertDto = new AlertDto();
						alertDto.setAlertId(dashboardAlert.getDashboardAlertId());
						alertDto.setAlertName(dashboardAlert.getName());

						assessmentSearchResult.getAlerts().add(alertDto);
					}
				}

				assessmentSearchResults.add(assessmentSearchResult);
			}
		}

		searchResult.setResultList(assessmentSearchResults);

		return searchResult;
	}

	@Override
	public VeteranAssessment findByVeteranAssessmentId(int veteranAssessmentId) {
		VeteranAssessment veteranAssessment = null;

		try {
			veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		} catch (Exception ex) {
			logger.debug("Veteran Assessment not found for ID: " + veteranAssessmentId);
		}

		return veteranAssessment;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VeteranAssessmentDto> getVeteranAssessmentsForVeteran(
			int veteranId) {

		List<VeteranAssessmentDto> veteranAssessmentList = new ArrayList<VeteranAssessmentDto>();

		List<VeteranAssessment> resultList = veteranAssessmentRepository.findByVeteranId(veteranId);

		for (VeteranAssessment veteranAssessment : resultList) {
			VeteranAssessmentDto veteranAssessmentDto = new VeteranAssessmentDto();
			veteranAssessmentDto.setVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());
			veteranAssessmentDto.setDateCreated(veteranAssessment.getDateCreated());
			veteranAssessmentDto.setAssessmentStatus(veteranAssessment.getAssessmentStatus().getName());
			if (veteranAssessment.getCreatedByUser() != null) {
				veteranAssessmentDto.setCreatedByUserFirstName(veteranAssessment.getCreatedByUser().getFirstName());
				veteranAssessmentDto.setCreatedByUserMiddleName(veteranAssessment.getCreatedByUser().getMiddleName());
				veteranAssessmentDto.setCreatedByUserLastName(veteranAssessment.getCreatedByUser().getLastName());
			}

			if (veteranAssessment.getBattery() != null) {
				veteranAssessmentDto.setBatteryName(veteranAssessment.getBattery().getName());
			}

			veteranAssessmentList.add(veteranAssessmentDto);
		}

		return veteranAssessmentList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VeteranAssessmentDto> getVeteranAssessmentsForVeteranAndProgramIdList(
			int veteranId, List<Integer> programIdList) {

		List<VeteranAssessmentDto> veteranAssessmentList = new ArrayList<VeteranAssessmentDto>();

		List<VeteranAssessment> resultList = veteranAssessmentRepository.findByVeteranIdAndProgramIdList(veteranId, programIdList);

		for (VeteranAssessment veteranAssessment : resultList) {
			VeteranAssessmentDto veteranAssessmentDto = new VeteranAssessmentDto();
			veteranAssessmentDto.setVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());
			veteranAssessmentDto.setDateCreated(veteranAssessment.getDateCreated());
			veteranAssessmentDto.setAssessmentStatus(veteranAssessment.getAssessmentStatus().getName());
			if (veteranAssessment.getCreatedByUser() != null) {
				veteranAssessmentDto.setCreatedByUserFirstName(veteranAssessment.getCreatedByUser().getFirstName());
				veteranAssessmentDto.setCreatedByUserMiddleName(veteranAssessment.getCreatedByUser().getMiddleName());
				veteranAssessmentDto.setCreatedByUserLastName(veteranAssessment.getCreatedByUser().getLastName());
			}

			if (veteranAssessment.getBattery() != null) {
				veteranAssessmentDto.setBatteryName(veteranAssessment.getBattery().getName());
			}

			veteranAssessmentList.add(veteranAssessmentDto);
		}

		return veteranAssessmentList;
	}

	@Override
	public void updateVeteranAssessmentClinicalNoteIdList(
			int veteranAssessmentId, List<Integer> clinicalNoteIdList) {

		logger.trace("updateVeteranAssessmentClinicalNoteIdList called");

		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		List<Integer> existingClinicalNoteIdList = new ArrayList<Integer>();

		// First remove everything that is not in the new list or not in one of
		// the mandatory survey list.
		for (int i = 0; i < veteranAssessment.getVeteranAssessmentNoteList().size(); ++i) {

			Integer clinicalNoteId = veteranAssessment.getVeteranAssessmentNoteList().get(i).getClinicalNote().getClinicalNoteId();

			if (!clinicalNoteIdList.contains(clinicalNoteId)) {
				veteranAssessment.getVeteranAssessmentNoteList().remove(i);
				--i;
			} else {
				existingClinicalNoteIdList.add(clinicalNoteId);
			}
		}

		// For each new surveys we need to add, add only if we don't already
		// have it in the list.
		for (Integer clinicalNoteId : clinicalNoteIdList) {
			if (!existingClinicalNoteIdList.contains(clinicalNoteId)) {

				ClinicalNote clinicalNote = clinicalNoteRepository.findOne(clinicalNoteId);

				VeteranAssessmentNote veteranAssessmentNote = new VeteranAssessmentNote();
				veteranAssessmentNote.setClinicalNote(clinicalNote);
				veteranAssessmentNote.setVeteranAssessment(veteranAssessment);

				veteranAssessment.getVeteranAssessmentNoteList().add(veteranAssessmentNote);
			}
		}

		// Update the database.
		veteranAssessmentRepository.update(veteranAssessment);

		// log the assessment creation event to the audit log
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_SURVEY_ASSIGNED, AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);
	}

	@Override
	public Integer create(Integer veteranId, Integer programId,
			Integer clinicId, Integer clinicianId, Integer createdByUserId,
			Integer selectedNoteTitleId, Integer selectedBatteryId,
			List<Integer> surveyIdList) {

		// 1. create a new veteran assessment.
		VeteranAssessment veteranAssessment = new VeteranAssessment();

		veteranAssessment.setVeteran(veteranRepository.findOne(veteranId));

		if (programId != null) {
			veteranAssessment.setProgram(programRepository.findOne(programId));
		}

		if (clinicId != null) {
			veteranAssessment.setClinic(clinicRepository.findOne(clinicId));
		}

		if (clinicianId != null) {
			veteranAssessment.setClinician(userRepository.findOne(clinicianId));
		}

		if (createdByUserId != null) {
			veteranAssessment.setCreatedByUser(userRepository.findOne(createdByUserId));
		}

		veteranAssessment.setAssessmentStatus(assessmentStatusRepository.findOne(AssessmentStatusEnum.CLEAN.getAssessmentStatusId()));

		if (selectedNoteTitleId != null) {
			veteranAssessment.setNoteTitle(noteTitleRepository.findOne(selectedNoteTitleId));
		}

		if (selectedBatteryId != null) {
			veteranAssessment.setBattery(batteryRepository.findOne(selectedBatteryId));
		}

		if (surveyIdList != null && !surveyIdList.isEmpty()) {

			Set<Survey> newSurveys = Sets.newHashSetWithExpectedSize(surveyIdList.size());
			for (Integer surveyId : surveyIdList) {
				newSurveys.add(surveyRepository.findOne(surveyId));
			}
			veteranAssessment.setSurveys(newSurveys);
		}

		// Save to database.
		veteranAssessmentRepository.create(veteranAssessment);

		// log the assessment creation event to the audit log
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_CREATED, AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);

		return veteranAssessment.getVeteranAssessmentId();
	}

	@Override
	public void update(Integer veteranAssessmentId, Integer programId,
			Integer clinicId, Integer clinicianId, Integer updatedByUserId,
			Integer selectedNoteTitleId, Integer selectedBatteryId,
			List<Integer> surveyIdList) {

		// Get a veteran.
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		// 1. Update program
		if (programId != null) {
			if (veteranAssessment.getProgram() == null || veteranAssessment.getProgram().getProgramId().intValue() != programId.intValue()) {
				veteranAssessment.setProgram(programRepository.findOne(programId));
			}
		} else {
			if (veteranAssessment.getProgram() != null) {
				veteranAssessment.setProgram(null);
			}
		}

		// 2. Update clinic
		if (clinicId != null) {
			if (veteranAssessment.getClinic() == null || veteranAssessment.getClinic().getClinicId().intValue() != clinicId.intValue()) {
				veteranAssessment.setClinic(clinicRepository.findOne(clinicId));
			}
		} else {
			if (veteranAssessment.getClinic() != null) {
				veteranAssessment.setClinic(null);
			}
		}

		// 3. Update clinician
		if (clinicianId != null) {
			if (veteranAssessment.getClinician() == null || veteranAssessment.getClinician().getUserId().intValue() != clinicianId.intValue()) {
				veteranAssessment.setClinician(userRepository.findOne(clinicianId));
			}
		} else {
			if (veteranAssessment.getClinician() != null) {
				veteranAssessment.setClinician(null);
			}
		}

		// 4. Update the selected note title.
		if (selectedNoteTitleId != null) {
			if (veteranAssessment.getNoteTitle() == null || veteranAssessment.getNoteTitle().getNoteTitleId().intValue() != selectedNoteTitleId.intValue()) {
				veteranAssessment.setNoteTitle(noteTitleRepository.findOne(selectedNoteTitleId));
			}
		} else {
			if (veteranAssessment.getNoteTitle() != null) {
				veteranAssessment.setNoteTitle(null);
			}
		}

		// 5. Update the selected battery.
		if (selectedBatteryId != null) {
			if (veteranAssessment.getBattery() == null || veteranAssessment.getBattery().getBatteryId().intValue() != selectedBatteryId.intValue()) {
				veteranAssessment.setBattery(batteryRepository.findOne(selectedBatteryId));
			}
		} else {
			if (veteranAssessment.getBattery() != null) {
				veteranAssessment.setBattery(null);
			}
		}

		// 6. Update the survey set in place.
		if (surveyIdList == null) {
			surveyIdList = new ArrayList<Integer>();
		}

		updateAssessmentSurveys(veteranAssessment, surveyIdList);

		// Update the database.
		veteranAssessmentRepository.update(veteranAssessment);

		// log the assessment creation event to the audit log
		VeteranAssessmentAuditLog auditLogEntry = VeteranAssessmentAuditLogHelper.createAuditLogEntry(veteranAssessment, AssessmentConstants.ASSESSMENT_EVENT_SURVEY_ASSIGNED, AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentConstants.PERSON_TYPE_USER);
		veteranAssessmentAuditLogRepository.update(auditLogEntry);
	}

	/**
	 * Updates the surveys found in the give veteran assessment to contain only the surveys with the given IDS. This
	 * only updates the veteranAssessment Object passed in (i.e. it doesn't update the database)
	 * 
	 * @param veteranAssessment
	 * @param surveyIds
	 */
	private void updateAssessmentSurveys(VeteranAssessment veteranAssessment,
			Collection<Integer> surveyIds) {

		// get the surveys that we want to keep and add any new ones
		Map<Integer, Survey> currentSurveyMap = veteranAssessment.getSurveyMap();
		Set<Survey> newSurveys = Sets.newHashSetWithExpectedSize(surveyIds.size());
		for (Integer surveyId : surveyIds) {
			Survey currentSurvey = currentSurveyMap.get(surveyId);
			if (currentSurvey != null)
				newSurveys.add(currentSurvey);
			else
				// otherwise look it up
				newSurveys.add(surveyRepository.findOne(surveyId));
		}

		// update the set of surveys
		veteranAssessment.setSurveys(newSurveys);
	}

	@Override
	public void addConsult(Integer veteranAssessmentId, Consult consult) {
		VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		if (assessment == null)
			throw new IllegalArgumentException("Veteran assessment not found (ID: " + veteranAssessmentId + ")");

		assessment.getConsults().add(consult);
		veteranAssessmentRepository.update(assessment);

	}

	@Override
	public void addHealthFactor(Integer veteranAssessmentId,
			HealthFactor healthFactor) {
		VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		if (assessment == null)
			throw new IllegalArgumentException("Veteran assessment not found (ID: " + veteranAssessmentId + ")");

		assessment.getHealthFactors().add(healthFactor);
		veteranAssessmentRepository.update(assessment);
	}

	@Override
	public void addDashboardAlert(Integer veteranAssessmentId,
			DashboardAlert dashboardAlert) {
		VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		if (assessment == null)
			throw new IllegalArgumentException("Veteran assessment not found (ID: " + veteranAssessmentId + ")");

		assessment.getDashboardAlerts().add(dashboardAlert);
		veteranAssessmentRepository.update(assessment);
	}

	@Transactional(readOnly = false)
	@Override
	public boolean isReadOnly(Integer veteranAssessmentId) {

		if (veteranAssessmentId == null) {
			return false;
		}

		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		if (veteranAssessment == null) {
			return false;
		}

		if (veteranAssessment.getAssessmentStatus() == null) {
			return false;
		}

		int assessmentStatusId = veteranAssessment.getAssessmentStatus().getAssessmentStatusId();

		if (assessmentStatusId != AssessmentStatusEnum.CLEAN.getAssessmentStatusId()) {
			return true;
		}

		return false;
	}

	@Override
	public void initializeVisibilityFor(VeteranAssessment veteranAssessment) {
		logger.debug("Initializing visibility settings for optional questions in veteran assessment {}", veteranAssessment);

		Integer veteranAssessmentId = veteranAssessment.getVeteranAssessmentId();

		VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		checkArgument(assessment != null, "No Veteran Assessment with ID %s found.", veteranAssessmentId);

		// remove any visibility settings for this assessment -- This is not right. Should not clear out the visibility
		// here.
		// veteranAssessmentMeasureVisibilityRepository.clearVisibilityFor(veteranAssessmentId);

		// Get all measures for an the veteranAssessmentId and extract IDs
		List<Measure> assessmentMeasures = veteranAssessmentRepository.getMeasuresFor(veteranAssessmentId);
		Map<Integer, Measure> assessmentMeasureMap = Maps.newHashMapWithExpectedSize(assessmentMeasures.size());
		for (Measure m : assessmentMeasures) {
			assessmentMeasureMap.put(m.getMeasureId(), m);
		}

		// Get rule events (of type ShowQuestion) that are relevant to the
		// veteran assessment
		List<Event> events = eventRepository.getEventByTypeFilteredByObjectIds(RuleConstants.EVENT_TYPE_SHOW_QUESTION, assessmentMeasureMap.keySet());
		Map<Integer, Boolean> visibilityMap = veteranAssessmentMeasureVisibilityRepository.getMeasureVisibilityMapFor(veteranAssessmentId);
		// Add an entry for each measure
		for (Event event : events) {
			Measure eventMeasure = assessmentMeasureMap.get(event.getRelatedObjectId());
			if (!visibilityMap.containsKey(eventMeasure.getMeasureId())) {
				veteranAssessmentMeasureVisibilityRepository.create(new VeteranAssessmentMeasureVisibility(assessment, eventMeasure));
			}
		}

	}

	@Transactional(readOnly = false)
	@Override
	public VeteranAssessment findOne(int veteranAssessmentId) {
		return veteranAssessmentRepository.findOne(veteranAssessmentId);
	}

	@Override
	public void saveVeteranAssessmentToVista(int veteranAssessmentId, int userId) {

		if (veteranAssessmentId < 1) {
			throw new IllegalArgumentException("A valid Veteran Assessment ID is required");
		}

		if (userId < 1) {
			throw new IllegalArgumentException("A valid User ID is required");
		}

		// 1. Get Veteran's assessment.
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		// 2. Make sure Veteran has been mapped to a VistA record. Else, this
		// will not work.
		if (StringUtils.isEmpty(veteranAssessment.getVeteran().getVeteranIen())) {
			throw new IllegalArgumentException("Veteran has not been mapped to a VistA record.");
		}

		// 3. Generate CPRS Note based on the responses to the survey questions.
		// TODO Plug in the code that generates the CPRS Note here.

		// 4. Get the Health Factors based on the responses to the survey
		// questions.
		// Actually, this is already determined and to a child table. No need
		// for any work then.
		if (veteranAssessment.getHealthFactors() != null) {
			for (HealthFactor healthFactor : veteranAssessment.getHealthFactors()) {
				logger.debug("Found HF: " + healthFactor.getName());
				// healthFactor.getVistaIen();
				// healthFactor.getName();
				// healthFactor.getIsHistorical();
			}
		}

		// Veteran IEN value: veteranAssessment.getVeteran().getVeteranIen();

		// 5. Save to VistA

		// 6. Changed status to 'Finalized'
		AssessmentStatus assessmentStatus = assessmentStatusRepository.findOne(AssessmentStatusEnum.FINALIZED.getAssessmentStatusId());

		veteranAssessment.setAssessmentStatus(assessmentStatus);

		// 7. Save to DB.
		veteranAssessmentRepository.update(veteranAssessment);
	}

	@Override
	public void updateStatus(int veteranAssessmentId,
			AssessmentStatusEnum assessmentStatusEnum) {
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		veteranAssessment.setAssessmentStatus(assessmentStatusRepository.findOne(assessmentStatusEnum.getAssessmentStatusId()));
		veteranAssessmentRepository.update(veteranAssessment);
	}

	@Transactional(readOnly = true)
	@Override
	public VeteranAssessmentInfo getVeteranAssessmentInfo(
			int veteranAssessmentId) {

		// Get the assessment.
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		if (veteranAssessment == null) {
			return null;
		}

		VeteranAssessmentInfo veteranAssessmentInfo = new VeteranAssessmentInfo();

		veteranAssessmentInfo.setVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());

		if (veteranAssessment.getVeteran() != null) {
			veteranAssessmentInfo.setVeteranId(veteranAssessment.getVeteran().getVeteranId());
			veteranAssessmentInfo.setVeteranIen(veteranAssessment.getVeteran().getVeteranIen());
			veteranAssessmentInfo.setFirstName(veteranAssessment.getVeteran().getFirstName());
			veteranAssessmentInfo.setMiddleName(veteranAssessment.getVeteran().getMiddleName());
			veteranAssessmentInfo.setLastName(veteranAssessment.getVeteran().getLastName());
			veteranAssessmentInfo.setVeteranFullName(VeteranUtil.getFullName(veteranAssessment.getVeteran().getFirstName(), veteranAssessment.getVeteran().getMiddleName(), veteranAssessment.getVeteran().getLastName(), veteranAssessment.getVeteran().getSuffix(), null));
			veteranAssessmentInfo.setBirthDate(veteranAssessment.getVeteran().getBirthDate());
			veteranAssessmentInfo.setSsnLastFour(veteranAssessment.getVeteran().getSsnLastFour());
			veteranAssessmentInfo.setPhone(veteranAssessment.getVeteran().getPhone());
			veteranAssessmentInfo.setOfficePhone(veteranAssessment.getVeteran().getOfficePhone());
			veteranAssessmentInfo.setCellPhone(veteranAssessment.getVeteran().getCellPhone());
			veteranAssessmentInfo.setEmail(veteranAssessment.getVeteran().getEmail());
		}

		if (veteranAssessment.getBattery() != null) {
			veteranAssessmentInfo.setBatteryId(veteranAssessment.getBattery().getBatteryId());
			veteranAssessmentInfo.setBatteryName(veteranAssessment.getBattery().getName());
		}

		if (veteranAssessment.getCreatedByUser() != null) {
			veteranAssessmentInfo.setCreatedByUserId(veteranAssessment.getCreatedByUser().getUserId());
			veteranAssessmentInfo.setCreatedByUserFullName(VeteranUtil.getFullName(veteranAssessment.getCreatedByUser().getFirstName(), veteranAssessment.getCreatedByUser().getMiddleName(), veteranAssessment.getCreatedByUser().getLastName(), null, null));
		}

		veteranAssessmentInfo.setDateCreated(veteranAssessment.getDateCreated());
		veteranAssessmentInfo.setDateCompleted(veteranAssessment.getDateCompleted());

		if (veteranAssessment.getProgram() != null) {
			veteranAssessmentInfo.setProgramId(veteranAssessment.getProgram().getProgramId());
			veteranAssessmentInfo.setProgramName(veteranAssessment.getProgram().getName());
		}

		if (veteranAssessment.getClinic() != null) {
			veteranAssessmentInfo.setClinicId(veteranAssessment.getClinic().getClinicId());
			veteranAssessmentInfo.setClinicName(veteranAssessment.getClinic().getName());
		}

		if (veteranAssessment.getNoteTitle() != null) {
			veteranAssessmentInfo.setNoteTitleId(veteranAssessment.getNoteTitle().getNoteTitleId());
			veteranAssessmentInfo.setNoteTitleName(veteranAssessment.getNoteTitle().getName());
		}

		if (veteranAssessment.getClinician() != null) {
			veteranAssessmentInfo.setClinicianId(veteranAssessment.getClinician().getUserId());
			veteranAssessmentInfo.setClinicianFullName(VeteranUtil.getFullName(veteranAssessment.getClinician().getFirstName(), veteranAssessment.getClinician().getMiddleName(), veteranAssessment.getClinician().getLastName(), null, null));
		}

		if (veteranAssessment.getAssessmentStatus() != null) {
			veteranAssessmentInfo.setAssessmentStatusId(veteranAssessment.getAssessmentStatus().getAssessmentStatusId());
			veteranAssessmentInfo.setAssessmentStatusName(veteranAssessment.getAssessmentStatus().getName());
		}

		return veteranAssessmentInfo;
	}

	@Transactional(readOnly = true)
	@Override
	public List<MentalHealthAssessment> getMentalHealthAssessments(
			int veteranAssessmentId) {

		List<MentalHealthAssessment> mentalHealthAssessmentList = new ArrayList<MentalHealthAssessment>();

		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		for (Survey survey : veteranAssessment.getSurveys()) {

			if (survey.isMha() != null && survey.isMha()) {

				String mentalHealthTestName = survey.getMhaTestName();
				String reminderDialogIEN = survey.getMhaResultGroupIen();
				String mentalHealthTestAnswers = "";
				Long surveyId = survey.getSurveyId().longValue();

				List<Integer> measureIdList = new ArrayList<Integer>();

				for (SurveyPage surveyPage : survey.getSurveyPageList()) {

					for (Measure measure : surveyPage.getMeasures()) {

						if (measure.getIsMha()) {
							measureIdList.add(measure.getMeasureId());
						}

						//
						if (measure.getChildren() != null && measure.getChildren().size() > 0) {

							// Need to order the Hash Set here.
							TreeSet<Measure> orderedMeasures = new TreeSet<Measure>(new Comparator<Measure>() {
								public int compare(Measure a, Measure b) {
									if (a.getDisplayOrder() == null && b.getDisplayOrder() == null) {
										return 0;
									} else if (a.getDisplayOrder() == null) {
										return -1;
									} else if (b.getDisplayOrder() == null) {
										return 1;
									} else {
										return a.getDisplayOrder() - b.getDisplayOrder();
									}
								}
							});

							orderedMeasures.addAll(measure.getChildren());

							for (Measure childMeasure : orderedMeasures) {
								if (childMeasure.getIsMha()) {
									measureIdList.add(childMeasure.getMeasureId());
								}
							}
						}
					}
				}

				// Get all the answers and lookup the MHA values we need to
				// send.
				// Should always have a question that is marked as MHA, but....
				if (measureIdList != null && measureIdList.size() > 0) {

					// we now have a list of measure id
					// Get all the answers the veteran submitted for the measure
					// id
					for (Integer measureId : measureIdList) {

						// Responses missed will be marked as X
						String mhaValue = "X";
						boolean respondHasMeasureId = false;

						for (SurveyMeasureResponse response : veteranAssessment.getSurveyMeasureResponseList()) {

							if (response.getMeasure().getMeasureId().equals(measureId)) {
								respondHasMeasureId = true;
								if (response.getBooleanValue()) { // responded = true
									mhaValue = response.getMeasureAnswer().getMhaValue();
									break;
								}
							}
						}

						if (respondHasMeasureId) {
							// Append it.
							mentalHealthTestAnswers += mhaValue;
						}
					}
				}

				MentalHealthAssessment mentalHealthAssessment = new MentalHealthAssessment();
				mentalHealthAssessment.setSurveyId(surveyId);
				mentalHealthAssessment.setMentalHealthTestName(mentalHealthTestName);
				mentalHealthAssessment.setReminderDialogIEN(reminderDialogIEN == null ? 0L : Long.parseLong(reminderDialogIEN.trim()));
				mentalHealthAssessment.setMentalHealthTestAnswers(mentalHealthTestAnswers);

				mentalHealthAssessmentList.add(mentalHealthAssessment);
			}
		}

		return mentalHealthAssessmentList;
	}

	@Override
	public void saveMentalHealthTestResult(int veteranAssessmentId,
			int surveyId, String mhaResult) {

		VeteranAssessmentSurvey vas = veteranAssessmentSurveyRepository.getByVeteranAssessmentIdAndSurveyId(veteranAssessmentId, surveyId);

		if (vas != null) {
			vas.setMhaResult(mhaResult);
			veteranAssessmentSurveyRepository.update(vas);
		} else {
			throw new IllegalArgumentException(String.format("Survey %s not found for veteran assessment %s", surveyId, veteranAssessmentId));
		}
	}

	@Transactional(readOnly = true)
	@Override
	public String getCprsNote(int veteranAssessmentId) {

		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		StringBuilder sb = new StringBuilder();

		sb.append("CPRS Note for Assessment\n");
		sb.append("veteranAssessmentId: " + veteranAssessment.getVeteranAssessmentId());

		return sb.toString();
	}

	@Override
	public void update(int veteranAssessmentId, Integer clinicId,
			Integer noteTitleId, Integer clinicianId, Integer assessmentStatusId) {

		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		if (clinicId != null && clinicId > 0) {
			if (veteranAssessment.getClinic() == null || !veteranAssessment.getClinic().getClinicId().equals(clinicId)) {
				veteranAssessment.setClinic(clinicRepository.findOne(clinicId));
			}
		} else {
			veteranAssessment.setClinic(null);
		}

		if (noteTitleId != null && noteTitleId > 0) {
			if (veteranAssessment.getNoteTitle() == null || !veteranAssessment.getNoteTitle().getNoteTitleId().equals(noteTitleId)) {
				veteranAssessment.setNoteTitle(noteTitleRepository.findOne(noteTitleId));
			}
		} else {
			veteranAssessment.setNoteTitle(null);
		}

		if (clinicianId != null && clinicianId > 0) {
			if (veteranAssessment.getClinician() == null || !veteranAssessment.getClinician().getUserId().equals(clinicianId)) {
				veteranAssessment.setClinician(userRepository.findOne(clinicianId));
			}
		} else {
			veteranAssessment.setClinician(null);
		}

		if (assessmentStatusId != null && assessmentStatusId > 0) {
			if (veteranAssessment.getAssessmentStatus() == null || !veteranAssessment.getAssessmentStatus().getAssessmentStatusId().equals(assessmentStatusId)) {
				veteranAssessment.setAssessmentStatus(assessmentStatusRepository.findOne(assessmentStatusId));
			}
		} else {
			veteranAssessment.setAssessmentStatus(null);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public List<String> getHealthFactorReport(int veteranAssessmentId) {
		VeteranAssessment veteranAssessment = veteranAssessmentRepository.findOne(veteranAssessmentId);

		List<String> titleList = new ArrayList<String>();

		if (veteranAssessment != null && veteranAssessment.getHealthFactors() != null) {
			for (HealthFactor healthFactor : veteranAssessment.getHealthFactors()) {
				titleList.add(String.format("%s %s", healthFactor.getVistaIen(), healthFactor.getName()));
			}
		}

		return titleList;
	}

	@Override
	public SearchResult<AssessmentSearchResult> searchVeteranAssessment(
			String programId, SearchAttributes searchAttributes) {
		SearchResult<VeteranAssessment> veteranAssessmentSearchResult = vadar.searchVeteranAssessment(programId.isEmpty()?null:Integer.parseInt(programId), searchAttributes);
		return prepareAssessmentSearchResult(veteranAssessmentSearchResult);
	}
}
