package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meMedications")
public class ModuleExporterMedications extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,
			Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "med1"),//
				create(usrRespMap, "med1_dose"),//
				create(usrRespMap, "med1_freq"),//
				create(usrRespMap, "med1_dur"),//
				create(usrRespMap, "med1_doc"),//
				create(usrRespMap, "med2"),//
				create(usrRespMap, "med2_dose"),//
				create(usrRespMap, "med2_freq"),//
				create(usrRespMap, "med2_dur"),//
				create(usrRespMap, "med2_doc"),//
				create(usrRespMap, "med3"),//
				create(usrRespMap, "med3_dose"),//
				create(usrRespMap, "med3_freq"),//
				create(usrRespMap, "med3_dur"),//
				create(usrRespMap, "med3_doc"),//
				create(usrRespMap, "med4"),//
				create(usrRespMap, "med4_dose"),//
				create(usrRespMap, "med4_freq"),//
				create(usrRespMap, "med4_dur"),//
				create(usrRespMap, "med4_doc"),//
				create(usrRespMap, "med5"),//
				create(usrRespMap, "med5_dose"),//
				create(usrRespMap, "med5_freq"),//
				create(usrRespMap, "med5_dur"),//
				create(usrRespMap, "med5_doc"));
	}

}
