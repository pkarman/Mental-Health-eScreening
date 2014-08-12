package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meInsomnia")
public class ModuleExporterIsi extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScore implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "sleep1a_falling", "sleep1b_staying", "sleep1c_waking", "sleep2_satisfied", "sleep3_interfere", "sleep4_noticeable", "sleep5_worried" }, colName, usrRespMap);
		}

	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "sleep1a_falling"),//
				create(usrRespMap, "sleep1b_staying"),//
				create(usrRespMap, "sleep1c_waking"),//
				create(usrRespMap, "sleep2_satisfied"),//
				create(usrRespMap, "sleep3_interfere"),//
				create(usrRespMap, "sleep4_noticeable"),//
				create(usrRespMap, "sleep5_worried"),//
				create(usrRespMap, "sleep_score", new CveScore()));
	}

}
