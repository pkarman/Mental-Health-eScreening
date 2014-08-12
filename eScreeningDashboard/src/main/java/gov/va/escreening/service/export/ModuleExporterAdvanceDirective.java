package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meAdvanceDirective")
public class ModuleExporterAdvanceDirective extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {

		return Arrays.asList(create(usrRespMap, "demo_language"),//
				create(usrRespMap, "demo_language_otherspec"),//
				create(usrRespMap, "demo_will"),//
				create(usrRespMap, "demo_will_info"));
	}

}
