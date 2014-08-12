package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meMilitarySexualTrauma")
public class ModuleExporterMst extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "mst1_2"),//
				create(usrRespMap, "mst3a_childhood"),//
				create(usrRespMap, "mst3b_adulthood"),//
				create(usrRespMap, "Mst_consult"));
	}

}
