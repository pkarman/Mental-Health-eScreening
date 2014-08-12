package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePHQ15")
public class ModuleExporterPHQ15 extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScorePhq15 implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "health1_stomach", "health2_back", "health3_arm", "health4_cramp", "health5_headache", "health6_chest", "health7_dizzy", "health8_faint", "health9_heart", "health10_breath", "health11_sex", "health12_constipation", "health13_nausea", "health14_tired", "health15_sleeping" }, colName, usrRespMap);
		}

	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "health1_stomach"),//
				create(usrRespMap, "health2_back"),//
				create(usrRespMap, "health3_arm"),//
				create(usrRespMap, "health4_cramp"),//
				create(usrRespMap, "health5_headache"),//
				create(usrRespMap, "health6_chest"),//
				create(usrRespMap, "health7_dizzy"),//
				create(usrRespMap, "health8_faint"),//
				create(usrRespMap, "health9_heart"),//
				create(usrRespMap, "health10_breath"),//
				create(usrRespMap, "health11_sex"),//
				create(usrRespMap, "health12_constipation"),//
				create(usrRespMap, "health13_nausea"),//
				create(usrRespMap, "health14_tired"),//
				create(usrRespMap, "health15_sleeping"),//
				create(usrRespMap, "health_score_phq15", new CveScorePhq15()));
	}

}
