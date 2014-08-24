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
	}
}
