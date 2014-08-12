package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meWHODAS20")
public class ModuleExporterWhoDas20 extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveWhoDasSocietyScore implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "whodas6_1_community", "whodas6_2_barriers", "whodas6_3_dignity", "whodas6_4_time", "whodas6_5_emotion", "whodas6_6_finance", "whodas6_7_family", "whodas6_8_relax" }, colName, usrRespMap);
		}
	}

	public class CveWhoDasSocietyMean implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return String.valueOf(getIntFromStr(usrRespMap.get("whodas_society_score")) / 8);
		}
	}

	public class CveWhoDasWrkScore implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "whodas5_5_daily", "whodas5_6_workwell", "whodas5_7_workdone", "whodas5_8_workquickly" }, colName, usrRespMap);
		}
	}

	public class CveWhoDasWrkMean implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return String.valueOf(getIntFromStr(usrRespMap.get("whodas_work_score")) / 4);
		}
	}

	public class CveWhoDasHouseholdScore implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "whodas5_1_housecare", "whoda5_2_housetask", "whodas5_3_housedone", "whodas5_4_housequickly" }, colName, usrRespMap);
		}
	}

	public class CveWhoDasHouseholdMean implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return String.valueOf(getIntFromStr(usrRespMap.get("whodas_household_score")) / 4);
		}
	}

	public class CveWhoDasPplScore implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "whodas4_1_deal", "whodas4_2_friend", "whodas4_3_getalong", "whodas4_4_newfriend", "whodas4_5_sexual" }, colName, usrRespMap);
		}

	}

	public class CveWhoDasPplMean implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return String.valueOf(getIntFromStr(usrRespMap.get("whodas_people_score")) / 5);
		}
	}

	public class CveWhoDasSelfcareScore implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "whodas3_1_wash", "whodas3_2_dressed", "whodas3_3_eat", "whodas3_4_stay" }, colName, usrRespMap);
		}
	}

	public class CveWhoDasSelfcareMean implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return String.valueOf(getIntFromStr(usrRespMap.get("whodas_selfcare_score")) / 4);
		}
	}

	public class CveWhoDasMobilityMean implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return String.valueOf(getIntFromStr(usrRespMap.get("whodas_mobility_score")) / 5);
		}
	}

	public class CveWhoDasMobilityScore implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "whodas2_1_stand", "whodas2_2_standup", "whodas2_3_move", "whodas2_4_getout", "whodas2_5_walk" }, colName, usrRespMap);
		}

	}

	class CveWhoDasUnderstandMean implements CellValueExtractor {
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return String.valueOf(getIntFromStr(usrRespMap.get("whodas_understand_score")) / 6);
		}
	}

	class CveWhoDasUnderstandScore implements CellValueExtractor {
		/**
		 * sum whodas1_1_concentrate to whodas1_6_coversation
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "whodas1_1_concentrate", "whodas1_2_remember", "whodas1_3_solution", "whodas1_4_new", "whodas1_5_understand", "whodas1_6_conversation" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "whodas1_1_concentrate"),//
				create(usrRespMap, "whodas1_2_remember"),//
				create(usrRespMap, "whodas1_3_solution"),//
				create(usrRespMap, "whodas1_4_new"),//
				create(usrRespMap, "whodas1_5_understand"),//
				create(usrRespMap, "whodas1_6_conversation"),//
				create(usrRespMap, "whodas_understand_score", new CveWhoDasUnderstandScore()),//
				create(usrRespMap, "whodas_understand_mean", new CveWhoDasUnderstandMean()),//
				create(usrRespMap, "whodas2_1_stand"),//
				create(usrRespMap, "whodas2_2_standup"),//
				create(usrRespMap, "whodas2_3_move"),//
				create(usrRespMap, "whodas2_4_getout"),//
				create(usrRespMap, "whodas2_5_walk"),//
				create(usrRespMap, "whodas_mobility_score", new CveWhoDasMobilityScore()),//
				create(usrRespMap, "whodas_mobility_mean", new CveWhoDasMobilityMean()),//
				create(usrRespMap, "whodas3_1_wash"),//
				create(usrRespMap, "whodas3_2_dressed"),//
				create(usrRespMap, "whodas3_3_eat"),//
				create(usrRespMap, "whodas3_4_stay"),//
				create(usrRespMap, "whodas_selfcare_score", new CveWhoDasSelfcareScore()),//
				create(usrRespMap, "whodas_selfcare_mean", new CveWhoDasSelfcareMean()),//
				create(usrRespMap, "whodas4_1_deal"),//
				create(usrRespMap, "whodas4_2_friend"),//
				create(usrRespMap, "whodas4_3_getalong"),//
				create(usrRespMap, "whodas4_4_newfriend"),//
				create(usrRespMap, "whodas4_5_sexual"),//
				create(usrRespMap, "whodas_people_score", new CveWhoDasPplScore()),//
				create(usrRespMap, "whodas_people_mean", new CveWhoDasPplMean()),//
				create(usrRespMap, "whodas5_1_housecare"),//
				create(usrRespMap, "whoda5_2_housetask"),//
				create(usrRespMap, "whodas5_3_housedone"),//
				create(usrRespMap, "whodas5_4_housequickly"),//
				create(usrRespMap, "whodas_household_score", new CveWhoDasHouseholdScore()),//
				create(usrRespMap, "whodas_household_mean", new CveWhoDasHouseholdMean()),//
				create(usrRespMap, "whodas_work"),//
				create(usrRespMap, "whodas5_5_daily"),//
				create(usrRespMap, "whodas5_6_workwell"),//
				create(usrRespMap, "whodas5_7_workdone"),//
				create(usrRespMap, "whodas5_8_workquickly"),//
				create(usrRespMap, "whodas_work_score", new CveWhoDasWrkScore()),//
				create(usrRespMap, "whodas_work_mean", new CveWhoDasWrkMean()),//
				create(usrRespMap, "whodas6_1_community"),//
				create(usrRespMap, "whodas6_2_barriers"),//
				create(usrRespMap, "whodas6_3_dignity"),//
				create(usrRespMap, "whodas6_4_time"),//
				create(usrRespMap, "whodas6_5_emotion"),//
				create(usrRespMap, "whodas6_6_finance"),//
				create(usrRespMap, "whodas6_7_family"),//
				create(usrRespMap, "whodas6_8_relax"),//
				create(usrRespMap, "whodas_society_score", new CveWhoDasSocietyScore()),//
				create(usrRespMap, "whodas_society_mean", new CveWhoDasSocietyMean()),//
				create(usrRespMap, "whodas_h1_daysdiff"),//
				create(usrRespMap, "whodas_h2_daysunable"),//
				create(usrRespMap, "whodas_h3_daysreduce"));
	}

}
