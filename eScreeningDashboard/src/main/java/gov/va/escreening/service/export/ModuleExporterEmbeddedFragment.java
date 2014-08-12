package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meEmbeddedFragment")
public class ModuleExporterEmbeddedFragment extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveInfectScore implements CellValueExtractor {

		/**
		 * if Sum infect1a_gastro,infect2a_fever,infect3a_rash, infect4a_fragment >0
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "infect1a_gastro", "infect2a_fever", "infect3a_rash", "infect4a_fragment" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "infect1a_gastro"),//
				create(usrRespMap, "infect1b_gastrosympt"),//
				create(usrRespMap, "infect2a_fever"),//
				create(usrRespMap, "infect2b_feversympt"),//
				create(usrRespMap, "infect3a_rash"),//
				create(usrRespMap, "infect3b_rashsympt"),//
				create(usrRespMap, "infect4a_fragment"),//
				create(usrRespMap, "infect4b_fragmentsympt"),//
				create(usrRespMap, "infect_score", new CveInfectScore()));
	}
}
