package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePragmaticConcerns")
public class ModuleExporterPragmaticConcerns extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "demo_legal_civil"),//
				create(usrRespMap, "demo_legal_child"),//
				create(usrRespMap, "demo_legal_tax"),//
				create(usrRespMap, "demo_legal_bank"),//
				create(usrRespMap, "demo_legal_ticket"),//
				create(usrRespMap, "demo_legal_arrest"),//
				create(usrRespMap, "demo_legal_restrain"),//
				create(usrRespMap, "demo_legal_dui"),//
				create(usrRespMap, "demo_legal_prob"),//
				create(usrRespMap, "demo_legal_parole"),//
				create(usrRespMap, "demo_legal_jag"),//
				create(usrRespMap, "demo_legal_cps"),//
				create(usrRespMap, "demo_legal_none"),//
				create(usrRespMap, "demo_legal_other"),//
				create(usrRespMap, "demo_legal_otherspec"));
	}

}
