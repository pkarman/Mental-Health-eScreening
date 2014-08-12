package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meAggression")
public class ModuleExporterRoas extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScore1 implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {

			applyMultipliersToAggressiveResponses(usrRespMap);

			return sumColumns(new String[] { "ang1_noise", "ang2_yell", "ang3_curse", "ang4_threat", "ang5_slamdoor", "ang6_throw", "ang7_break", "ang8_setfire", "ang9_gesture", "ang10_strike", "ang11_injury", "ang12_severeinjury", "ang13_pick", "ang14_banghead", "ang15_cut", "ang16_hurt" }, colName, usrRespMap);
		}

		private void applyMultipliersToAggressiveResponses(
				Map<String, String> usrRespMap) {

			// group these issues in different category groups. The key of the map is the weight
			Map<Integer, String[]> exportColumnsMultiplierCategories = new HashMap<Integer, String[]>();
			exportColumnsMultiplierCategories.put(1, new String[] { "ang1_noise" });
			exportColumnsMultiplierCategories.put(2, new String[] { "ang2_yell", "ang5_slamdoor" });
			exportColumnsMultiplierCategories.put(3, new String[] { "ang3_curse", "ang6_throw", "ang9_gesture", "ang13_pick" });
			exportColumnsMultiplierCategories.put(4, new String[] { "ang4_threat", "ang7_break", "ang10_strike", "ang14_banghead" });
			exportColumnsMultiplierCategories.put(5, new String[] { "ang8_setfire", "ang11_injury", "ang15_cut" });
			exportColumnsMultiplierCategories.put(6, new String[] { "ang12_severeinjury", "ang16_hurt" });

			// now apply the weight to every issue in its category
			for (Integer multiplier : exportColumnsMultiplierCategories.keySet()) {
				for (String exportColumn : exportColumnsMultiplierCategories.get(multiplier)) {
					usrRespMap.put(exportColumn, String.valueOf(getIntFromStr(usrRespMap.get(exportColumn)) * multiplier));
				}
			}
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {

		return Arrays.asList(create(usrRespMap, "ang1_noise"),//
				create(usrRespMap, "ang2_yell"),//
				create(usrRespMap, "ang3_curse"),//
				create(usrRespMap, "ang4_threat"),//
				create(usrRespMap, "ang5_slamdoor"),//
				create(usrRespMap, "ang6_throw"),//
				create(usrRespMap, "ang7_break"),//
				create(usrRespMap, "ang8_setfire"),//
				create(usrRespMap, "ang9_gesture"),//
				create(usrRespMap, "ang10_strike"),//
				create(usrRespMap, "ang11_injury"),//
				create(usrRespMap, "ang12_severeinjury"),//
				create(usrRespMap, "ang13_pick"),//
				create(usrRespMap, "ang14_banghead"),//
				create(usrRespMap, "ang15_cut"),//
				create(usrRespMap, "ang16_hurt"),//
				create(usrRespMap, "ROAS_weighted_score", new CveScore1()));
	}
}
