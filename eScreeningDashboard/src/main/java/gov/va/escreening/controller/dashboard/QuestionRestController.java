package gov.va.escreening.controller.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import gov.va.escreening.controller.RestController;
import gov.va.escreening.delegate.EditorsViewDelegate;
import gov.va.escreening.dto.MeasureAnswerDTO;
import gov.va.escreening.dto.MeasureValidationSimpleDTO;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.editors.QuestionInfo;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.MeasureService;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.transformer.EditorsQuestionViewTransformer;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.webservice.Response;
import gov.va.escreening.webservice.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.google.common.base.Preconditions.*;

@Controller
@RequestMapping(value = "/dashboard/services")
public class QuestionRestController extends RestController{

	private static final Logger logger = LoggerFactory.getLogger(QuestionRestController.class);

	private final EditorsViewDelegate editorsViewDelegate;
	private final MeasureService measureService;
	private final MeasureRepository measureRepo;
	private final AssessmentVariableService assessmentVariableService;

	@Autowired
	QuestionRestController(MeasureRepository measureRepo, 
			MeasureService measureService,
			EditorsViewDelegate editorsViewDelegate,
			AssessmentVariableService assessmentVariableService){
		this.measureRepo = checkNotNull(measureRepo);
		this.measureService = checkNotNull(measureService);
		this.editorsViewDelegate = checkNotNull(editorsViewDelegate);
		this.assessmentVariableService = checkNotNull(assessmentVariableService);
	}


	@RequestMapping(value = "/questions/{questionId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response<Map<String, QuestionInfo>> updateQuestion(
			@PathVariable("questionId") Integer questionId,
			@RequestBody QuestionInfo question,
			HttpServletRequest request) {
		logRequest(logger, request);
		
		QuestionInfo updatedQuestionInfo = EditorsQuestionViewTransformer.transformQuestion(measureRepo.updateMeasure(EditorsQuestionViewTransformer.transformQuestionInfo(question)));

		Map<String, QuestionInfo> questionMap = new HashMap<>();
		questionMap.put("question", updatedQuestionInfo);

		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), questionMap);
	}

	@RequestMapping(value = "/questions/{questionId}", method = RequestMethod.GET, produces = "application/json")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<QuestionInfo>  getQuestion(@PathVariable("questionId") Integer questionId,
			HttpServletRequest request) {
		logRequest(logger, request);
		
		// Call service class here instead of hard coding it.
		Measure measure = editorsViewDelegate.findMeasure(questionId);
		QuestionInfo question = EditorsQuestionViewTransformer.transformQuestion(measure);
		return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), question);
	}

	@RequestMapping(value="/questions/{measureId}/answers", method = RequestMethod.GET, produces="application/json")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<MeasureAnswerDTO> getMeasureAnswerValues(@PathVariable Integer measureId,
			HttpServletRequest request){

		logRequest(logger, request);
		return measureService.getMeasureAnswerValues(measureId);
	}

	@RequestMapping(value="/questions/{measureId}/answers/{answerId}", method = RequestMethod.GET,  produces="application/json")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public MeasureAnswerDTO getMeasureAnswer(@PathVariable Integer measureId, @PathVariable Integer answerId,  
			HttpServletRequest request){

		logRequest(logger, request);
		return measureService.getMeasureAnswer(measureId);
	}

	@RequestMapping(value="/questions/{measureId}/answers/{answerId}/assessment-variables", method = RequestMethod.GET,  produces="application/json")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AssessmentVariableDto getMeasureAnswerAssessmentVariable(@PathVariable Integer measureId, @PathVariable Integer answerId,  
			HttpServletRequest request){

		logRequest(logger, request);
		
		return new AssessmentVariableDto(assessmentVariableService.getAssessmentVarsForAnswer(answerId));
	}
	
	@RequestMapping(value="/questions/{measureId}/validations", method = RequestMethod.GET,  produces="application/json")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<MeasureValidationSimpleDTO> getMeasureValidations(@PathVariable Integer measureId, 
			HttpServletRequest request){

		logRequest(logger, request);
		return measureService.getMeasureValidations(measureId);
	}
}
