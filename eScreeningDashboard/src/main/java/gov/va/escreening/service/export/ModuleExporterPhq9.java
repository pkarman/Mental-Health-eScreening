package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meDepression")
public class ModuleExporterPhq9 extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveSum1Through9 implements CellValueExtractor {
		/**
		 * "dep1_interest","dep2_down","dep3_sleep","dep4_tired","dep5_appetite","dep6_feelbad","dep7_concentrate",
		 * "dep8_moveslow","dep9_dead"
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "dep1_interest", "dep2_down", "dep3_sleep", "dep4_tired", "dep5_appetite", "dep6_feelbad", "dep7_concentrate", "dep8_moveslow", "dep9_dead" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "dep1_interest"),//
				create(usrRespMap, "dep2_down"),//
				create(usrRespMap, "dep3_sleep"),//
				create(usrRespMap, "dep4_tired"),//
				create(usrRespMap, "dep5_appetite"),//
				create(usrRespMap, "dep6_feelbad"),//
				create(usrRespMap, "dep7_concentrate"),//
				create(usrRespMap, "dep8_moveslow"),//
				create(usrRespMap, "dep9_dead"),//
				create(usrRespMap, "dep10_difficult"),//
				create(usrRespMap, "dep_score_phq9", new CveSum1Through9()));
	}

}
