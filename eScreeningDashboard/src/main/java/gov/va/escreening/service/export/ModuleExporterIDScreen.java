package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meIDScreen")
public class ModuleExporterIDScreen extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	public List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		// Veteran v = assessment.getVeteran();
		return Arrays.asList(create(usrRespMap, "demo_lastname"),//
				create(usrRespMap, "demo_firstname"),//
				create(usrRespMap, "demo_midname"),//
				create(usrRespMap, "demo_SSN"),//
				create(usrRespMap, "demo_date"),//
				create(usrRespMap, "demo_contact"),//
				create(usrRespMap, "demo_call"),//
				create(usrRespMap, "demo_call_spec"),//
				create(usrRespMap, "demo_email"),//
				create(usrRespMap, "duration"));
		// new DataExportCell("demo_lastname", getOrMiss(v.getLastName())),//
		// new DataExportCell("demo_firstname", getOrMiss(v.getFirstName())),//
		// new DataExportCell("demo_midname", getOrMiss(v.getMiddleName())),//
		// new DataExportCell("demo_SSN", getOrMiss(v.getSsnLastFour())),//
		// new DataExportCell("demo_date", df.format(v.getDateCreated())),//
		// new DataExportCell("demo_contact", getOrMiss(v.getVeteranIen())),//
		// new DataExportCell("demo_call", getOrMiss(v.getPhone())),//
		// new DataExportCell("demo_call_spec", miss()),//
		// new DataExportCell("demo_email", getOrMiss(v.getEmail())),//
		// new DataExportCell("duration", getOrMiss(getStrFromInt(assessment.getDuration()))));
	}
}
