package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meDrugAbuse")
public class ModuleExporterDast10 extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveDeriveScore implements CellValueExtractor {
		/**
		 * "DAST1_other","DAST2_abuse","DAST3_ablestop","DAST4_blackout","DAST5_guilty","DAST6_complain","DAST7_neglect"
		 * ,"DAST8_illegal ,"DAST9_withdraw","DAST10_medical"
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {

			// take the value of DAST3_ablestop and invert it (if 0 then 1, else if 1 then 0)
			String DAST3_ablestop = usrRespMap.get("DAST3_ablestop");
			DAST3_ablestop = (DAST3_ablestop != null) ? ("0".equals(DAST3_ablestop) ? "1" : "0") : null;
			int ablestop = DAST3_ablestop==null?0:Integer.parseInt(DAST3_ablestop);
			
			String strSum=sumColumns(new String[] { "DAST1_other", "DAST2_abuse", "DAST4_blackout", "DAST5_guilty", "DAST6_complain", "DAST7_neglect", "DAST8_illegal", "DAST9_withdraw", "DAST10_medical" }, colName, usrRespMap);
			
			return String.valueOf(ablestop+Integer.parseInt(strSum));
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "DAST1_other"),//
				create(usrRespMap, "DAST2_abuse"),//
				create(usrRespMap, "DAST3_ablestop"),//
				create(usrRespMap, "DAST4_blackout"),//
				create(usrRespMap, "DAST5_guilty"),//
				create(usrRespMap, "DAST6_complain"),//
				create(usrRespMap, "DAST7_neglect"),//
				create(usrRespMap, "DAST8_illegal"),//
				create(usrRespMap, "DAST9_withdraw"),//
				create(usrRespMap, "DAST10_medical"),//
				create(usrRespMap, "DAST_score", new CveDeriveScore()));
	}

}
