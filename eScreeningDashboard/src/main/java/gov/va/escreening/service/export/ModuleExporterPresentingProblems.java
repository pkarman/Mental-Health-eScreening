package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("mePresentingProblems")
public class ModuleExporterPresentingProblems extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	public List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "demo_va_enroll"),//
				create(usrRespMap, "demo_va_physhealth"),//
				create(usrRespMap, "demo_va_menthealth"),//
				create(usrRespMap, "demo_va_primcare"),//
				create(usrRespMap, "demo_va_other"),//
				create(usrRespMap, "demo_va_otherspec"),//
				create(usrRespMap, "demo_info_prost"),//
				create(usrRespMap, "demo_info_sex"),//
				create(usrRespMap, "demo_info_ment"),//
				create(usrRespMap, "demo_info_subst"),//
				create(usrRespMap, "demo_info_visual"),//
				create(usrRespMap, "demo_info_health_none"),//
				create(usrRespMap, "demo_info_comp"),//
				create(usrRespMap, "demo_info_gi"),//
				create(usrRespMap, "demo_info_loan"),//
				create(usrRespMap, "demo_info_ben_none"),//
				create(usrRespMap, "demo_info_rehab"),//
				create(usrRespMap, "demo_info_unemp"),//
				create(usrRespMap, "demo_info_work"),//
				create(usrRespMap, "demo_info_emp_none"),//
				create(usrRespMap, "demo_info_comm"),//
				create(usrRespMap, "demo_info_fin_none"),//
				create(usrRespMap, "demo_info_adj"),//
				create(usrRespMap, "demo_info_relat"),//
				create(usrRespMap, "demo_info_support"),//
				create(usrRespMap, "demo_info_soc_none"),//
				create(usrRespMap, "demo_info_parole"),//
				create(usrRespMap, "demo_info_probat"),//
				create(usrRespMap, "demo_info_warrant"),//
				create(usrRespMap, "demo_info_bank"),//
				create(usrRespMap, "demo_info_legal_none"),//
				create(usrRespMap, "demo_info_home"),//
				create(usrRespMap, "demo_info_forcl"),//
				create(usrRespMap, "demo_info_house_none"),//
				create(usrRespMap, "demo_info_other"),//
				create(usrRespMap, "demo_info_otherspec"));
	}
}
