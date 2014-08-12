package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meMilitaryHistory")
public class ModuleExporterMilitaryHistory extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "serv_discipline_none"),//
				create(usrRespMap, "serv_discipline_couns"),//
				create(usrRespMap, "serv_discipline_art15"),//
				create(usrRespMap, "serv_discipline_martial"),//
				create(usrRespMap, "serv_discipline_other"),//
				create(usrRespMap, "serv_discipline_otherspec"),//
				create(usrRespMap, "serv_award_none"),//
				create(usrRespMap, "serv_award_honor"),//
				create(usrRespMap, "serv_award_distservcross"),//
				create(usrRespMap, "serv_award_distservmedal"),//
				create(usrRespMap, "serv_award_meritservmedal"),//
				create(usrRespMap, "serv_award_legionmerit"),//
				create(usrRespMap, "serv_distflycross"),//
				create(usrRespMap, "serv_award_bronzstar"),//
				create(usrRespMap, "serv_award_purpheart"),//
				create(usrRespMap, "serv_award_airmedal"),//
				create(usrRespMap, "serv_award_silvstar"),//
				create(usrRespMap, "serv_award_soldiermedal"),//
				create(usrRespMap, "serv_award_achievmedal"),//
				create(usrRespMap, "serv_award_commedal"),//
				create(usrRespMap, "serv_award_other"),//
				create(usrRespMap, "serv_award_otherspec"),//
				create(usrRespMap, "serv_deployno"),//
				create(usrRespMap, "serv_deploy_loc_1"),//
				create(usrRespMap, "serv_deploy_1_from_mo"),//
				create(usrRespMap, "serv_deploy_1_from_yr"),//
				create(usrRespMap, "serv_deploy_1_to_mo"),//
				create(usrRespMap, "serv_deploy_1_to_yr"),//
				create(usrRespMap, "serv_deploy_loc_2"),//
				create(usrRespMap, "serv_deploy_2_from_mo"),//
				create(usrRespMap, "serv_deploy_2_from_yr"),//
				create(usrRespMap, "serv_deploy_2_to_mo"),//
				create(usrRespMap, "serv_deploy_2_to_yr"),//
				create(usrRespMap, "serv_deploy_loc_3"),//
				create(usrRespMap, "serv_deploy_3_from_mo"),//
				create(usrRespMap, "serv_deploy_3_from_mo"),//
				create(usrRespMap, "serv_deploy_3_to_mo"),//
				create(usrRespMap, "serv_deploy_3_to_yr"),//
				create(usrRespMap, "serv_deploy_loc_4"),//
				create(usrRespMap, "serv_deploy_4_from_mo"),//
				create(usrRespMap, "serv_deploy_4_from_yr"),//
				create(usrRespMap, "serv_deploy_4_to_mo"),//
				create(usrRespMap, "serv_deploy_4_to_yr"),//
				create(usrRespMap, "serv_deploy_loc_5"),//
				create(usrRespMap, "serv_deploy_5_from_mo"),//
				create(usrRespMap, "serv_deploy_5_from_yr"),//
				create(usrRespMap, "serv_deploy_5_to_mo"),//
				create(usrRespMap, "serv_deploy_5_to_yr"));
	}

}
