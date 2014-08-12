package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meSocialEnvironment")
public class ModuleExporterSocialEnvironment extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	public List<DataExportCell> applyNow(String moduleName,	Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "demo_children"),//
				create(usrRespMap, "child_agegroup1"),//
				create(usrRespMap, "child_agegroup2"),//
				create(usrRespMap, "child_agegroup3"),//
				create(usrRespMap, "child_agegroup4"),//
				create(usrRespMap, "child_agegroup5"),//
				create(usrRespMap, "child_agegroup6"),//
				create(usrRespMap, "child_agegroup7"),//
				create(usrRespMap, "child_agegroup8"),//
				create(usrRespMap, "child_agegroup9"),//
				create(usrRespMap, "child_agegroup10"),//
				create(usrRespMap, "demo_livewith_parent"),//
				create(usrRespMap, "demo_livewith_alone"),//
				create(usrRespMap, "demo_livewith_friend"),//
				create(usrRespMap, "demo_livewith_spouse"),//
				create(usrRespMap, "demo_livewith_child"),//
				create(usrRespMap, "demo_livewith_other"),//
				create(usrRespMap, "demo_livewith_otherspec"),//
				create(usrRespMap, "demo_emo_parents"),//
				create(usrRespMap, "demo_emo_friends"),//
				create(usrRespMap, "demo_emo_spouse"),//
				create(usrRespMap, "demo_emo_therapist"),//
				create(usrRespMap, "demo_emo_spiritual"),//
				create(usrRespMap, "demo_emo_children"),//
				create(usrRespMap, "demo_emo_other"),//
				create(usrRespMap, "demo_emo_other_spec"),//
				create(usrRespMap, "demo_emo_none"),//
				create(usrRespMap, "demo_rel_hurt"));
	}

}
