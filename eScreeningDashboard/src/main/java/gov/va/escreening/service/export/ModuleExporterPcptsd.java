package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePcptsd")
public class ModuleExporterPcptsd extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveSum implements CellValueExtractor {
		/**
		 * "pcptsdA_nightmare", "pcptsdB_notthink", "pcptsdC_onguard", "pcptsdD_numb"
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "pcptsdA_nightmare", "pcptsdB_notthink", "pcptsdC_onguard", "pcptsdD_numb" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "pcptsdA_nightmare"),//
				create(usrRespMap, "pcptsdB_notthink"),//
				create(usrRespMap, "pcptsdC_onguard"),//
				create(usrRespMap, "pcptsdD_numb"),//
				create(usrRespMap, "pcptsd_score", new CveSum()));
	}

}
