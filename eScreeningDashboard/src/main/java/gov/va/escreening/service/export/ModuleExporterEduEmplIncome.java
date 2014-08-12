package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meEduEmplIncome")
public class ModuleExporterEduEmplIncome extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	public List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "demo_education"),//
				create(usrRespMap, "demo_workstatus"),//
				create(usrRespMap, "demo_hours"),//
				create(usrRespMap, "demo_occupation"),//
				create(usrRespMap, "demo_income_none"),//
				create(usrRespMap, "demo_income_wrk"),//
				create(usrRespMap, "demo_income_unemp"),//
				create(usrRespMap, "demo_income_dis"),//
				create(usrRespMap, "demo_income_gi"),//
				create(usrRespMap, "demo_income_retire"),//
				create(usrRespMap, "demo_income_other"),//
				create(usrRespMap, "demo_income_spec"),//
				create(usrRespMap, "demo_income_group"),//
				create(usrRespMap, "demo_relationship"));
	}

}
