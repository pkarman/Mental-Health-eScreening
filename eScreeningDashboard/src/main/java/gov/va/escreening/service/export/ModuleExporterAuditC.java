package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meAlcoholConsumption")
public class ModuleExporterAuditC extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScore implements CellValueExtractor {

		/**
		 * Sum alc_drinkyr, alc_drinkday, alc_drink6
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "alc_drinkyr", "alc_drinkday", "alc_drink6" }, colName, usrRespMap);
		}

	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "alc_drinkyr"),//
				create(usrRespMap, "alc_drinkday"),//
				create(usrRespMap, "alc_drink6"),//
				create(usrRespMap, "alc_score_audit", new CveScore()));
	}

}
