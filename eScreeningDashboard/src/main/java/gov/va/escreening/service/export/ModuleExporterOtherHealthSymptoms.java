package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meOtherHealthSymptoms")
public class ModuleExporterOtherHealthSymptoms extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveWeightScore implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "health21_wghtgain", "health22_wghtloss" }, colName, usrRespMap);
		}
	}

	public class CveHearingScore implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "health16_hearing", "health17_tinnitus" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "health16_hearing"),//
				create(usrRespMap, "health17_tinnitus"),//
				create(usrRespMap, "health_score_hearing", new CveHearingScore()),//
				create(usrRespMap, "health20_vision"),//
				create(usrRespMap, "health21_wghtgain"),//
				create(usrRespMap, "health22_wghtloss"),//
				create(usrRespMap, "health_score_weight", new CveWeightScore()),//
				create(usrRespMap, "health23_forgetfulness"));
	}
}
