package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meHomelessnessReminder")
public class ModuleExporterHomelessnessReminder extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "HomelessCR_stable_house"),//
				create(usrRespMap, "HomelessCR_stable_worry"),//
				create(usrRespMap, "HomelessCR_live_2mos"),//
				create(usrRespMap, "HomelessCR_homenogov_spec"),//
				create(usrRespMap, "HomelessCR_homewgov_spec"),//
				create(usrRespMap, "HomelessCR_friendfam_spec"),//
				create(usrRespMap, "HomelessCR_hotel_spec"),//
				create(usrRespMap, "HomelessCR_shortins_spec"),//
				create(usrRespMap, "HomelessCR_shel_spec"),//
				create(usrRespMap, "HomelessCR_out_spec"),//
				create(usrRespMap, "HomelessCR_oth_spec"),//
				create(usrRespMap, "HomelessCR_house_ref"),//
				create(usrRespMap, "HomelessCR_howreach"));
	}

}
