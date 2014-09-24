package gov.va.escreening.service.export;

import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.SurveyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component("meMandatory")
public class ModuleExporterMandatory extends ModuleExporterAbstract implements ModuleDataExporter {

	@Resource(type = SurveyRepository.class)
	SurveyRepository surveyrepository;

	Integer identificationSurveyId;
	Integer basicDemographicsSurveyId;

	@PostConstruct
	void initSurveys() {
		for (Survey s : surveyrepository.findAll()) {
			if ("Identification".equals(s.getName())) {
				identificationSurveyId = s.getSurveyId();
			} else if ("Basic Demographics".equals(s.getName())) {
				basicDemographicsSurveyId = s.getSurveyId();
			}
		}
		Preconditions.checkNotNull(identificationSurveyId, "There is no 'Identification' Survey found in the system");
		Preconditions.checkNotNull(basicDemographicsSurveyId, "There is no 'Basic Demographics' Survey found in the system");
	}

	@Override
	public List<DataExportCell> apply(ModuleEnum modEnum,
			VeteranAssessment assessment, Integer identifiedExportType) {

		List<DataExportCell> mandatoryData = new ArrayList<DataExportCell>();

		mandatoryData.addAll(collectPpi(assessment, identifiedExportType));

		mandatoryData.add(new DataExportCell("assessment_id", getOrMiss(getStrFromInt(assessment.getVeteranAssessmentId()))));
		mandatoryData.add(new DataExportCell("created_by", getOrMiss(assessment.getCreatedByUser() != null ? assessment.getCreatedByUser().getUserFullName() : null)));
		mandatoryData.add(new DataExportCell("battery_name", getOrMiss(assessment.getBattery() != null ? assessment.getBattery().getName() : null)));
		mandatoryData.add(new DataExportCell("program_name", getOrMiss(assessment.getProgram() != null ? assessment.getProgram().getName() : null)));
		mandatoryData.add(new DataExportCell("vista_clinic", getOrMiss(assessment.getClinic() != null ? assessment.getClinic().getName() : null)));
		mandatoryData.add(new DataExportCell("note_title", getOrMiss(assessment.getNoteTitle() != null ? assessment.getNoteTitle().getName() : null)));
		mandatoryData.add(new DataExportCell("clinician_name", getOrMiss(assessment.getClinician() != null ? assessment.getClinician().getUserFullName() : null)));
		mandatoryData.add(new DataExportCell("date_created", getOrMiss(getDtAsStr(assessment.getDateCreated()))));
		mandatoryData.add(new DataExportCell("time_created", getOrMiss(getTmAsStr(assessment.getDateCreated()))));
		mandatoryData.add(new DataExportCell("date_completed", getOrMiss(getDtAsStr(assessment.getDateCompleted()))));
		mandatoryData.add(new DataExportCell("time_completed", getOrMiss(getTmAsStr(assessment.getDateCompleted()))));
		mandatoryData.add(new DataExportCell("duration", getOrMiss(getStrFromInt(assessment.getDuration()))));

		boolean identified=ExportTypeEnum.DEIDENTIFIED.getExportTypeId() != identifiedExportType;
		mandatoryData.add(new DataExportCell("vista_DOB", identified?getOrMiss(getDtAsStr(assessment.getVeteran().getBirthDate())):"999"));
		return mandatoryData;
	}

	private List<DataExportCell> collectPpi(VeteranAssessment assessment,
			Integer identifiedExportType) {
		Veteran v = assessment.getVeteran();

		List<DataExportCell> mandatoryIdendifiedData = new ArrayList<DataExportCell>();

		boolean identified=ExportTypeEnum.DEIDENTIFIED.getExportTypeId() != identifiedExportType;
		
		// if veteran has taken the 'Identification' survey then skip this as veteran survey response
		// will take precedence over the clinician entered data
			
		mandatoryIdendifiedData.addAll(
				Arrays.asList(
						new DataExportCell("vista_lastname", identified?getOrMiss(v.getLastName()):"999"),//
						new DataExportCell("vista_firstname", identified?getOrMiss(v.getFirstName()):"999"),//
						new DataExportCell("vista_midname", identified?getOrMiss(v.getMiddleName()):"999"),//
						new DataExportCell("vista_SSN", identified?getOrMiss(v.getSsnLastFour()):"999"),
						new DataExportCell("vista_ien", v.getVeteranIen())));
		return mandatoryIdendifiedData;
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		throw new IllegalStateException(String.format("%s->applyNow is not allowed to be called", getClass().getName()));
	}

}
