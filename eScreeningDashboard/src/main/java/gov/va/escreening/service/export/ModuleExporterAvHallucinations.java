package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meAvHallucinations")
public class ModuleExporterAvHallucinations extends ModuleExporterAbstract implements ModuleDataExporter {
	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		
		return Arrays.asList(create(usrRespMap, "health18_voices"),//
				create(usrRespMap, "health19_seeing")//
		);
	}
}
