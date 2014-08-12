package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meBasicPain")
public class ModuleExporterBasicPain extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "pain_number"),//
				create(usrRespMap, "pain_area_1"),//
				create(usrRespMap, "pain_area_2"),//
				create(usrRespMap, "pain_area_3"),//
				create(usrRespMap, "pain_area_4"),//
				create(usrRespMap, "pain_area_5")
		);
	}
}
