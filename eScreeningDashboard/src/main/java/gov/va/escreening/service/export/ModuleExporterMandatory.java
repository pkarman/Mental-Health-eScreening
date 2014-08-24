package gov.va.escreening.service.export;

import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.SurveyRepository;

import java.util.ArrayList;
import java.util.Arrays;
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

	@PostConstruct
	void initSurveys() {
		for (Survey s : surveyrepository.findAll()) {
			if ("Identification".equals(s.getName())) {
				identificationSurveyId = s.getSurveyId();
				break;
			}
		}
		Preconditions.checkNotNull(identificationSurveyId, "There is no 'Identification' Survey found in the system");
	}

	@Override
	public List<DataExportCell> apply(ModuleEnum modEnum,
			VeteranAssessment assessment, Integer identifiedExportType) {

		List<DataExportCell> mandatoryData = new ArrayList<DataExportCell>();

		mandatoryData.addAll(collectPpi(assessment, identifiedExportType));

		mandatoryData.add(new DataExportCell("assessment_id", getOrMiss(getStrFromInt(assessment.getVeteranAssessmentId()))));
		mandatoryData.add(new DataExportCell("created_by", getOrMiss(assessment.getCreatedByUser().getUserFullName())));
		mandatoryData.add(new DataExportCell("battery_name", getOrMiss(assessment.getBattery().getName())));
		mandatoryData.add(new DataExportCell("program_name", getOrMiss(assessment.getProgram().getName())));
		mandatoryData.add(new DataExportCell("vista_clinic", getOrMiss(assessment.getClinic().getName())));
		mandatoryData.add(new DataExportCell("note_title", getOrMiss(assessment.getNoteTitle().getName())));
		mandatoryData.add(new DataExportCell("clinician_name", getOrMiss(assessment.getClinician().getUserFullName())));
		mandatoryData.add(new DataExportCell("date_created", df.format(assessment.getDateCreated())));
		mandatoryData.add(new DataExportCell("time_created", tf.format(assessment.getDateCreated())));

		if (assessment.getDateCompleted() != null) {
			mandatoryData.add(new DataExportCell("date_completed", df.format(assessment.getDateCompleted())));
			mandatoryData.add(new DataExportCell("time_completed", tf.format(assessment.getDateCompleted())));
		}

		mandatoryData.add(new DataExportCell("duration", getStrFromInt(assessment.getDuration())));

		return mandatoryData;
	}

	private List<DataExportCell> collectPpi(VeteranAssessment assessment,
			Integer identifiedExportType) {
		Veteran v = assessment.getVeteran();

		List<DataExportCell> mandatoryIdendifiedData = new ArrayList<DataExportCell>();

		if (ExportTypeEnum.DEIDENTIFIED.getExportTypeId() != identifiedExportType) {
			// if veteran has taken the 'Identification' survey then skip this as veteran survey response
			// will take precedence over the clinician entered data
			if (assessment.getSurveyMap().get(identificationSurveyId) == null) {
				mandatoryIdendifiedData.addAll(Arrays.asList(new DataExportCell("demo_lastname", getOrMiss(v.getLastName())),//
						new DataExportCell("demo_firstname", getOrMiss(v.getFirstName())),//
						new DataExportCell("demo_midname", getOrMiss(v.getMiddleName())),//
						new DataExportCell("demo_SSN", getOrMiss(v.getSsnLastFour())),//
						new DataExportCell("demo_DOB", df.format(v.getBirthDate()))));
			}
			mandatoryIdendifiedData.add(new DataExportCell("veteran_ien", v.getVeteranIen()));
		}
		return mandatoryIdendifiedData;
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		throw new IllegalStateException(String.format("%s->applyNow is not allowed to be called", getClass().getName()));
	}

}
