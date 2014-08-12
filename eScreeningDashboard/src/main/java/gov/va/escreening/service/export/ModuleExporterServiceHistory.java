package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("meServiceHistory")
public class ModuleExporterServiceHistory extends ModuleExporterAbstract implements ModuleDataExporter {

	@Override
	protected List<DataExportCell> applyNow(String moduleName,	Map<String, String> usrRespMap, VeteranAssessment assessment) {
		return Arrays.asList(create(usrRespMap, "serv_type1"),//
				create(usrRespMap, "serv_branch1"),//
				create(usrRespMap, "serv_start1"),//
				create(usrRespMap, "serv_stop1"),//
				create(usrRespMap, "serv_discharge1"),//
				create(usrRespMap, "serv_rank1"),//
				create(usrRespMap, "serv_job1"),//
				create(usrRespMap, "serv_type2"),//
				create(usrRespMap, "serv_branch2"),//
				create(usrRespMap, "serv_start2"),//
				create(usrRespMap, "serv_stop2"),//
				create(usrRespMap, "serv_discharge2"),//
				create(usrRespMap, "serv_rank2"),//
				create(usrRespMap, "serv_job2"),//
				create(usrRespMap, "serv_type3"),//
				create(usrRespMap, "serv_branch3"),//
				create(usrRespMap, "serv_start3"),//
				create(usrRespMap, "serv_stop3"),//
				create(usrRespMap, "serv_discharge3"),//
				create(usrRespMap, "serv_rank3"),//
				create(usrRespMap, "serv_job3"),//
				create(usrRespMap, "serv_oper_none"),//
				create(usrRespMap, "serv_oper_OEF"),//
				create(usrRespMap, "serv_oper_OIF"),//
				create(usrRespMap, "serv_oper_gwot"),//
				create(usrRespMap, "serv_oper_ond"),//
				create(usrRespMap, "serv_oper_caribbean"),//
				create(usrRespMap, "serv_oper_gulf"),//
				create(usrRespMap, "serv_oper_somalia"),//
				create(usrRespMap, "serv_oper_bosnia"),//
				create(usrRespMap, "serv_oper_kosovo"),//
				create(usrRespMap, "serv_oper_djibouti"),//
				create(usrRespMap, "serv_oper_libya"),//
				create(usrRespMap, "Serv_oper_vietnam"),//
				create(usrRespMap, "serv_oper_korea"),//
				create(usrRespMap, "serv_oper_other"),//
				create(usrRespMap, "serv_oper_other1spec"),//
				create(usrRespMap, "serv_oper_other2spec"));
	}

}
