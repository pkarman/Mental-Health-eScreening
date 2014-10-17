package gov.va.escreening.controller.dashboard;

import gov.va.escreening.service.AssessmentVariableService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

@Controller
@RequestMapping("/dashboard")
public class AssessmentVariableController {
	@Resource(name = "assessmentVariableService")
	AssessmentVariableService avs;

	@RequestMapping(value = "/av/get/{surveyId}", method = RequestMethod.GET)
	@ResponseBody
	public List getAssessmentVarsForSurvey(
			@PathVariable("surveyId") Integer surveyId) {
		Table<String, String, Object> t = avs.getAssessmentVarsFor(surveyId);

		List<Map<String, Object>> avs = Lists.newArrayList();

		for (String rowKey : t.rowKeySet()) {
			avs.add(t.row(rowKey));
		}

		return avs;
	}

}
