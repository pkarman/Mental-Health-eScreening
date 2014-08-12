package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePclc")
public class ModuleExporterPclc extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScore implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "pcl1_memories", "pcl2_dreams", "pcl3_acting", "pcl4_remind", "pcl5_reaction", "pcl6_think", "pcl7_activity", "pcl8_trouble", "pcl9_interest", "pcl10_distant", "pcl11_emotion", "pcl12_future", "pcl13_asleep", "pcl14_irritable", "pcl15_concentrate", "pcl16_alert", "pcl17_jumpy" }, colName, usrRespMap);
		}

	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "pcl1_memories"),//
				create(usrRespMap, "pcl2_dreams"),//
				create(usrRespMap, "pcl3_acting"),//
				create(usrRespMap, "pcl4_remind"),//
				create(usrRespMap, "pcl5_reaction"),//
				create(usrRespMap, "pcl6_think"),//
				create(usrRespMap, "pcl7_activity"),//
				create(usrRespMap, "pcl8_trouble"),//
				create(usrRespMap, "pcl9_interest"),//
				create(usrRespMap, "pcl10_distant"),//
				create(usrRespMap, "pcl11_emotion"),//
				create(usrRespMap, "pcl12_future"),//
				create(usrRespMap, "pcl13_asleep"),//
				create(usrRespMap, "pcl14_irritable"),//
				create(usrRespMap, "pcl15_concentrate"),//
				create(usrRespMap, "pcl16_alert"),//
				create(usrRespMap, "pcl17_jumpy"),//
				create(usrRespMap, "pcl_score", new CveScore()));
	}

}
