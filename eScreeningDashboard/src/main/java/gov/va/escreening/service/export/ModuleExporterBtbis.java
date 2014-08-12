package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.util.SurveyResponsesHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("meBtbis")
public class ModuleExporterBtbis extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveScore1 implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			int q1 = getIntFromStr(usrRespMap.get("tbi_q1_score")) > 0 ? 1 : 0;
			int q2 = getIntFromStr(usrRespMap.get("tbi_q2_score")) > 0 ? 1 : 0;
			int q3 = getIntFromStr(usrRespMap.get("tbi_q3_score")) > 0 ? 1 : 0;
			int q4 = getIntFromStr(usrRespMap.get("tbi_q4_score")) > 0 ? 1 : 0;

			String tbi_score1 = String.valueOf(q1 + q2 + q3 + q4);

			usrRespMap.put(colName, tbi_score1);
			return tbi_score1;
		}
	}

	public class CveScore2 implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			int q1 = getIntFromStr(usrRespMap.get("tbi_q1_score")) > 0 ? 1 : 0;
			int q2 = getIntFromStr(usrRespMap.get("tbi_q2_score")) > 0 ? 1 : 0;
			int q3 = getIntFromStr(usrRespMap.get("tbi_q3_score")) > 0 ? 1 : 0;
			int q4 = getIntFromStr(usrRespMap.get("tbi_q4_score")) > 0 ? 1 : 0;
			int q5 = getIntFromStr(usrRespMap.get("tbi_consult")) > 0 ? 1 : 0;

			String tbi_score2 = String.valueOf(q1 + q2 + q3 + q4 + q5);

			usrRespMap.put(colName, tbi_score2);
			return tbi_score2;
		}
	}

	class CveTbiQ1Score implements CellValueExtractor {
		/**
		 * Sum tbi_blast, tbi_vehicle, tbi_fragment, tbi_fall, tbi_blow, tbi_otherinj
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "tbi_blast", "tbi_vehicle", "tbi_fragment", "tbi_fall", "tbi_blow", "tbi_otherinj" }, colName, usrRespMap);
		}

	}

	class CveTbiQ2Score implements CellValueExtractor {
		/**
		 * Sum tbi_immed_loss, tbi_immed_dazed, tbi_immed_memory, tbi_immed_concussion, tbi_immed_headinj
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "tbi_immed_loss", "tbi_immed_dazed", "tbi_immed_memory", "tbi_immed_concussion", "tbi_immed_headinj" }, colName, usrRespMap);
		}

	}

	class CveTbiQ3Score implements CellValueExtractor {
		/**
		 * Sum tbi_worse_memory, tbi_worse_balance, tbi_worse_light, tbi_worse_irritable, tbi_worse_headache ,
		 * tbi_worse_sleep
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "tbi_worse_memory", "tbi_worse_balance", "tbi_worse_light", "tbi_worse_irritable", "tbi_worse_headache", "tbi_worse_sleep" }, colName, usrRespMap);
		}

	}

	class CveTbiQ4Score implements CellValueExtractor {
		/**
		 * Sum tbi_week_memory, tbi_week_balance, tbi_week_light, tbi_week_irritable, tbi_week_headache, tbi_week_sleep
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "tbi_week_memory", "tbi_week_balance", "tbi_week_light", "tbi_week_irritable", "tbi_week_headache", "tbi_week_sleep" }, colName, usrRespMap);
		}

	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "tbi_blast"),//
				create(usrRespMap, "tbi_vehicle"),//
				create(usrRespMap, "tbi_fragment"),//
				create(usrRespMap, "tbi_fall"),//
				create(usrRespMap, "tbi_blow"),//
				create(usrRespMap, "tbi_otherinj"),//
				create(usrRespMap, "tbi_none"),//
				create(usrRespMap, "tbi_q1_score", new CveTbiQ1Score()),//
				create(usrRespMap, "tbi_immed_loss"),//
				create(usrRespMap, "tbi_immed_dazed"),//
				create(usrRespMap, "tbi_immed_memory"),//
				create(usrRespMap, "tbi_immed_concussion"),//
				create(usrRespMap, "tbi_immed_headinj"),//
				create(usrRespMap, "tbI_immed_none"),//
				create(usrRespMap, "tbi_q2_score", new CveTbiQ2Score()),//
				create(usrRespMap, "tbi_worse_memory"),//
				create(usrRespMap, "tbi_worse_balance"),//
				create(usrRespMap, "tbi_worse_light"),//
				create(usrRespMap, "tbi_worse_irritable"),//
				create(usrRespMap, "tbi_worse_headache"),//
				create(usrRespMap, "tbi_worse_sleep"),//
				create(usrRespMap, "tbi_worse_none"),//
				create(usrRespMap, "tbi_q3_score", new CveTbiQ3Score()),//
				create(usrRespMap, "tbi_week_memory"),//
				create(usrRespMap, "tbi_week_balance"),//
				create(usrRespMap, "tbi_week_light"),//
				create(usrRespMap, "tbi_week_irritable"),//
				create(usrRespMap, "tbi_week_headache"),//
				create(usrRespMap, "tbi_week_sleep"),//
				create(usrRespMap, "tbi_week_none"),//
				create(usrRespMap, "tbi_q4_score", new CveTbiQ4Score()),//
				create(usrRespMap, "tbi_consult"),//
				create(usrRespMap, "tbi_score1", new CveScore1()),//
				create(usrRespMap, "tbi_score2", new CveScore2()),//
				create(usrRespMap, "TBI_consult_where"),//
				create(usrRespMap, "TBI_consult_when"),//
				create(usrRespMap, "TBI_consult_how"));
	}

}
