package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meServiceInjuries")
public class ModuleExporterServiceInjuries extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "serv_blast_none"),//
				create(usrRespMap, "serv_spine_none"),//
				create(usrRespMap, "serv_burn_none"),//
				create(usrRespMap, "serv_nerve_none"),//
				create(usrRespMap, "serv_vision_none"),//
				create(usrRespMap, "serv_hearing_none"),//
				create(usrRespMap, "serv_amput_none"),//
				create(usrRespMap, "serv_bone_none"),//
				create(usrRespMap, "serv_joint_none"),//
				create(usrRespMap, "serv_internal_none"),//
				create(usrRespMap, "serv_other1_none"),//
				create(usrRespMap, "serv_other2_none"),//
				create(usrRespMap, "serv_blast_comb"),//
				create(usrRespMap, "serv_spine_comb"),//
				create(usrRespMap, "serv_burn_comb"),//
				create(usrRespMap, "serv_nerve_comb"),//
				create(usrRespMap, "serv_vision_comb"),//
				create(usrRespMap, "serv_hearing_comb"),//
				create(usrRespMap, "serv_amput_comb"),//
				create(usrRespMap, "serv_bone_comb"),//
				create(usrRespMap, "serv_joint_comb"),//
				create(usrRespMap, "serv_internal_comb"),//
				create(usrRespMap, "serv_other1_comb"),//
				create(usrRespMap, "serv_other2_comb"),//
				create(usrRespMap, "serv_blast_other"),//
				create(usrRespMap, "serv_spine_other"),//
				create(usrRespMap, "serv_burn_other"),//
				create(usrRespMap, "serv_nerve_other"),//
				create(usrRespMap, "serv_vision_other"),//
				create(usrRespMap, "serv_hearing_other"),//
				create(usrRespMap, "serv_amput_other"),//
				create(usrRespMap, "serv_bone_other"),//
				create(usrRespMap, "serv_joint_other"),//
				create(usrRespMap, "serv_internal_other"),//
				create(usrRespMap, "serv_other1_other"),//
				create(usrRespMap, "serv_other2_other"),//
				create(usrRespMap, "serv_other1_spec"),//
				create(usrRespMap, "serv_other2_spec"),//
				create(usrRespMap, "serv_inj_comp"));
	}

}
