package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePromisEmotionalSupport")
public class ModuleExporterPromisEmotionalSupport extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CvePromiseScore implements CellValueExtractor {

		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "es1_listen", "es2_talk", "es3_feel", "es4_bad" }, colName, usrRespMap);
		}

	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "es1_listen"),//
				create(usrRespMap, "es2_talk"),//
				create(usrRespMap, "es3_feel"),//
				create(usrRespMap, "es4_bad"),//
				create(usrRespMap, "es_score_promis", new CvePromiseScore()));
	}

}
