package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meOefOifReminder")
public class ModuleExporterOefOifReminder extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "serv_oef_oif"),//
				create(usrRespMap, "serv_oef_loc"),//
				create(usrRespMap, "serv_oef_otherspec"),//
				create(usrRespMap, "serv_oif_loc"),//
				create(usrRespMap, "serv_oif_otherspec"));
	}

}
