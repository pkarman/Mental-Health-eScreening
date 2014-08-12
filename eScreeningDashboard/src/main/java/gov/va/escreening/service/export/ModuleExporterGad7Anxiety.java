package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meAnxiety")
public class ModuleExporterGad7Anxiety extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveSum implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "gad1_nervous", "gad2_notable", "gad3_worry", "gad4_trouble", "gad5_restless", "gad6_annoyed", "gad7_afraid" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "gad1_nervous"),//
				create(usrRespMap, "gad2_notable"),//
				create(usrRespMap, "gad3_worry"),//
				create(usrRespMap, "gad4_trouble"),//
				create(usrRespMap, "gad5_restless"),//
				create(usrRespMap, "gad6_annoyed"),//
				create(usrRespMap, "gad7_afraid"),//
				create(usrRespMap, "gad7_score", new CveSum()),//
				create(usrRespMap, "gad8_difficult"));
	}

}
