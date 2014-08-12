package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meSpiritualHealth")
public class ModuleExporterSpiritualHealth extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,	Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "spirit_rel_important"),//
				create(usrRespMap, "spirit_combat_change"),//
				create(usrRespMap, "spirit_faith_comm"),//
				create(usrRespMap, "spirit_chap_referral"));
	}

}
