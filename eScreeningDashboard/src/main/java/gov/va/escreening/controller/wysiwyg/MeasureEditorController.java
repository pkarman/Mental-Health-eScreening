package gov.va.escreening.controller.wysiwyg;

import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.service.AssessmentVariableService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

@Controller
@RequestMapping("/wysiwyg")
public class MeasureEditorController {
	@Autowired
	MeasureRepository measureRepo;

	@Resource(name = "assessmentVariableService")
	AssessmentVariableService avs;

	@RequestMapping(value = "/measure/get", method = RequestMethod.GET)
	@ResponseBody
	public List<Measure> getMeasuresForSurvey(@RequestParam int surveyID) {
		List<Measure> measureList = measureRepo.getMeasureDtoBySurveyID(surveyID);
		return measureList;
	}

	@RequestMapping(value = "/av/get/{surveyId}", method = RequestMethod.GET)
	@ResponseBody
	public List getAssessmentVarsForSurvey(
			@PathVariable("surveyId") Integer surveyId) {
		Table<String, String, String> t = avs.getAssessmentVarsFor(surveyId);

		List<Map<String, String>> avs = Lists.newArrayList();

		for (String rowKey : t.rowKeySet()) {
			avs.add(t.row(rowKey));
		}

		return avs;
	}
}
