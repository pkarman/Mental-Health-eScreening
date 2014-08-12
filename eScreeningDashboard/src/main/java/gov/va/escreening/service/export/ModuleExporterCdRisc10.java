package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meResilience")
public class ModuleExporterCdRisc10 extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScore implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "res1_adapt", "res2_deal", "res3_humor", "res4_cope", "res5_bounce", "res6_goals", "res7_pressure", "res8_discouraged", "res9_strong", "res10_unpleasant" }, colName, usrRespMap);
		}

	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "res1_adapt"),//
				create(usrRespMap, "res2_deal"),//
				create(usrRespMap, "res3_humor"),//
				create(usrRespMap, "res4_cope"),//
				create(usrRespMap, "res5_bounce"),//
				create(usrRespMap, "res6_goals"),//
				create(usrRespMap, "res7_pressure"),//
				create(usrRespMap, "res8_discouraged"),//
				create(usrRespMap, "res9_strong"),//
				create(usrRespMap, "res10_unpleasant"),//
				create(usrRespMap, "res_score", new CveScore()));
	}
}
