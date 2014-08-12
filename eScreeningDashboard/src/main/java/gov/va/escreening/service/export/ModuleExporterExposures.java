package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meExposures")
public class ModuleExporterExposures extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "serv_exposed"),//
				create(usrRespMap, "serv_exp_chemical"),//
				create(usrRespMap, "serv_exp_bio"),//
				create(usrRespMap, "serv_exp_jp8"),//
				create(usrRespMap, "serv_exp_asbestos"),//
				create(usrRespMap, "serv_exp_nerve"),//
				create(usrRespMap, "serv_exp_radio"),//
				create(usrRespMap, "serv_exp_sand"),//
				create(usrRespMap, "serv_exp_uranium"),//
				create(usrRespMap, "serv_exp_industrial"),//
				create(usrRespMap, "serv_exp_fumes"),//
				create(usrRespMap, "serv_exp_paint"),//
				create(usrRespMap, "serv_exp_bite"),//
				create(usrRespMap, "serv_exp_burn"),//
				create(usrRespMap, "serv_exp_pest"),//
				create(usrRespMap, "serv_exp_other"),//
				create(usrRespMap, "serv_exp_oth1spec"),//
				create(usrRespMap, "serv_exp_oth2spec"),//
				create(usrRespMap, "serv_animal_bite"),//
				create(usrRespMap, "serv_animal_blood"),//
				create(usrRespMap, "serv_animal_bat"),//
				create(usrRespMap, "serv_combat"),//
				create(usrRespMap, "serv_comb_none"),//
				create(usrRespMap, "serv_comb_attack"),//
				create(usrRespMap, "serv_comb_fire"),//
				create(usrRespMap, "serv_comb_hand"),//
				create(usrRespMap, "serv_comb_wounded"),//
				create(usrRespMap, "serv_comb_interro"),//
				create(usrRespMap, "serv_comb_rocket"),//
				create(usrRespMap, "serv_comb_seebody"),//
				create(usrRespMap, "serv_comb_clear"),//
				create(usrRespMap, "serv_comb_ship"),//
				create(usrRespMap, "serv_comb_detain"),//
				create(usrRespMap, "serv_comb_recdfire"),//
				create(usrRespMap, "serv_comb_handbody"),//
				create(usrRespMap, "serv_comb_killed"),//
				create(usrRespMap, "serv_comb_enemy"));
	}

}
