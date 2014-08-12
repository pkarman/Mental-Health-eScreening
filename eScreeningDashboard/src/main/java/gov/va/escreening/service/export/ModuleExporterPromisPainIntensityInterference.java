package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePromisPainIntensityInterference")
public class ModuleExporterPromisPainIntensityInterference extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScore1 implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "pain_intensity", "pain_average" }, colName, usrRespMap);
		}
	}

	public class CveScore2 implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "pain_interfere_life", "pain_interfere_conc", "pain_interfere_day", "pain_interfere_rec", "pain_interfere_task", "pain_interfere_social" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "pain_intensity"),//
				create(usrRespMap, "pain_average"),//
				create(usrRespMap, "pain_score_intensity", new CveScore1()),//
				create(usrRespMap, "pain_interfere_life"),//
				create(usrRespMap, "pain_interfere_conc"),//
				create(usrRespMap, "pain_interfere_day"),//
				create(usrRespMap, "pain_interfere_rec"),//
				create(usrRespMap, "pain_interfere_task"),//
				create(usrRespMap, "pain_interfere_social"),//
				create(usrRespMap, "pain_score_interference", new CveScore2()));
	}

}
