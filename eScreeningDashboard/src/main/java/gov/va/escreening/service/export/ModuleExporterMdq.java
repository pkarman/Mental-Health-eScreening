package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meMoodDisorder")
public class ModuleExporterMdq extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveSum1a21m implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "hyp1a_good", "hyp1b_irritable", "hyp1c_confident", "hyp1d_sleep", "hyp1e_talkative", "hyp1f_thoughts", "hyp1g_distracted", "hyp1h_energy", "hyp1i_active", "hyp1j_social", "hyp1k_sex", "hyp1l_unusual", "hyp1m_spend" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "hyp1a_good"),//
				create(usrRespMap, "hyp1b_irritable"),//
				create(usrRespMap, "hyp1c_confident"),//
				create(usrRespMap, "hyp1d_sleep"),//
				create(usrRespMap, "hyp1e_talkative"),//
				create(usrRespMap, "hyp1f_thoughts"),//
				create(usrRespMap, "hyp1g_distracted"),//
				create(usrRespMap, "hyp1h_energy"),//
				create(usrRespMap, "hyp1i_active"),//
				create(usrRespMap, "hyp1j_social"),//
				create(usrRespMap, "hyp1k_sex"),//
				create(usrRespMap, "hyp1l_unusual"),//
				create(usrRespMap, "hyp1m_spend"),//
				create(usrRespMap, "hyp2_time"),//
				create(usrRespMap, "hyp3_problem"),//
				create(usrRespMap, "hyp4_relative"),//
				create(usrRespMap, "hyp5_disorder"),//
				create(usrRespMap, "hyp_score", new CveSum1a21m()),//
				create(usrRespMap, "hyp_criteria"));
	}

}
