package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meDemographics")
public class ModuleExporterDemographics extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveBMI implements CellValueExtractor {
		/**
		 * [demo_weight/(demo_totalheightin*demo_totalheightin)]*703 =
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			//[demo_weight/(demo_totalheightin*demo_totalheightin)]*703 =
			float demo_weight = getFloatFromStr(usrRespMap.get("demo_weight"));
			float demo_totalheightin = getFloatFromStr(usrRespMap.get("demo_totalheightin"));
			demo_totalheightin *= demo_totalheightin;
			float demo_BMI = (demo_weight == 0 || demo_totalheightin == 0) ? 0 : (demo_weight / demo_totalheightin) * 703;

			String result = String.valueOf(demo_BMI);
			usrRespMap.put(colName, result);
			return result;
		}
	}

	public class CveTHI implements CellValueExtractor {
		/**
		 * (demo_heightft*12)+demo_heightinch
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			int totalHeightInInches = getIntFromStr(usrRespMap.get("demo_heightft")) * 12 + getIntFromStr(usrRespMap.get("demo_heightinch"));
			String strTotalHtInInches = String.valueOf(totalHeightInInches);
			usrRespMap.put(colName, strTotalHtInInches);
			return strTotalHtInInches;
		}
	}

	@Override
	public List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "demo_gender"),//
				create(usrRespMap, "demo_DOB"),//
				create(usrRespMap, "demo_heightft"),//
				create(usrRespMap, "demo_heightinch"),//
				create(usrRespMap, "demo_weight"),//
				create(usrRespMap, "demo_totalheightin", new CveTHI()),//
				create(usrRespMap, "demo_BMI", new CveBMI()),//
				create(usrRespMap, "demo_ethnic"),//
				create(usrRespMap, "demo_racewhite"),//
				create(usrRespMap, "demo_race_amind"),//
				create(usrRespMap, "demo_race_pacisl"),//
				create(usrRespMap, "demo_race_black"),//
				create(usrRespMap, "demo_race_asian"),//
				create(usrRespMap, "demo_race_oth"),//
				create(usrRespMap, "demo_race_othspec"),//
				create(usrRespMap, "demo_race_decline"));
	}

}
