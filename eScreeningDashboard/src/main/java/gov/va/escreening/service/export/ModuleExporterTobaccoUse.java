package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meTobaccoUse")
public class ModuleExporterTobaccoUse extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "tob_use"),//
				create(usrRespMap, "tob_quit_when"),//
				create(usrRespMap, "tob_quit_month"),//
				create(usrRespMap, "tob_quit_date"),//
				create(usrRespMap, "tob_ciggs"),//
				create(usrRespMap, "tob_cigar"),//
				create(usrRespMap, "tob_smokeless"),//
				create(usrRespMap, "tob_cigg_quant_length"),//
				create(usrRespMap, "tob_pipe_quant_length"),//
				create(usrRespMap, "tob_chew_quant_length"));
	}

}
