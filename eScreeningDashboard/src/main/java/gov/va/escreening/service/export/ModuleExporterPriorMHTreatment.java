package gov.va.escreening.service.export;

import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePriorMentalHealthTreatment")
public class ModuleExporterPriorMHTreatment extends ModuleExporterAbstract implements ModuleDataExporter {

	public class CveDxSum implements CellValueExtractor {
		/**
		 * sum prior_dx_dep, prior_dx_ptsd, prior_dx_oth
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "prior_dx_dep", "prior_dx_ptsd", "prior_dx_oth" }, colName, usrRespMap);
		}
	}

	public class CveTxSum implements CellValueExtractor {
		/**
		 * sum prior_tx_inpt, prior_tx_thpy, prior_tx_med, prior_tx_ect, prior_tx_oth
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "prior_tx_inpt", "prior_tx_thpy", "prior_tx_med", "prior_tx_ect", "prior_tx_oth" }, colName, usrRespMap);
		}
	}

	public class CveHxTxSum implements CellValueExtractor {
		/**
		 * prior_dx_score + prior_tx_score + demo_info_ment+ demo_va_menthealth
		 */
		@Override
		public String apply(String colName, Map<String, String> usrRespMap) {
			return sumColumns(new String[] { "prior_dx_score", "prior_tx_score", "demo_info_ment", "demo_va_menthealth" }, colName, usrRespMap);
		}
	}

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {

		copySurveyData(ModuleEnum.ME_PresentingProblems.getModuleName(), new String[] { "demo_info_ment", "demo_va_menthealth" }, usrRespMap, assessment);

		return Arrays.asList(create(usrRespMap, "prior_dx_dep"),//
				create(usrRespMap, "prior_dx_ptsd"),//
				create(usrRespMap, "Prior_dx_none"),//
				create(usrRespMap, "prior_dx_oth"),//
				create(usrRespMap, "prior_dx_oth_spec"),//
				create(usrRespMap, "prior_dx_score", new CveDxSum()),//
				create(usrRespMap, "prior_tx_inpt"),//
				create(usrRespMap, "prior_tx_thpy"),//
				create(usrRespMap, "prior_tx_med"),//
				create(usrRespMap, "prior_tx_ect"),//
				create(usrRespMap, "Prior_tx_none"),//
				create(usrRespMap, "prior_tx_oth"),//
				create(usrRespMap, "prior_tx_oth_spec"),//
				create(usrRespMap, "prior_tx_score", new CveTxSum()),//
				create(usrRespMap, "prior_hx_tx_req_appt", new CveHxTxSum()));
	}

	/**
	 * method to copy columns requested from the Survey (Module) SurveyResponseMeasures to the passed in Map
	 * 
	 * @param moduleName
	 * @param columns
	 * @param usrRespMap
	 * @param assessment
	 */
	private void copySurveyData(String moduleName, String[] columns,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {

		if (usrRespMap == null) {
			return;
		}

		Map<String, String> d = surveyResponsesHelper.prepareSurveyResponsesMap(moduleName, assessment, ExportTypeEnum.IDENTIFIED.getExportTypeId());
		if (d != null) {
			usrRespMap.putAll(d);
		}
	}
}
