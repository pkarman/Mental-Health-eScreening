package gov.va.escreening.controller.dashboard;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import gov.va.escreening.controller.RestController;
import gov.va.escreening.delegate.EditorsDelegate;
import gov.va.escreening.dto.MeasureAnswerDTO;
import gov.va.escreening.dto.MeasureValidationSimpleDTO;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.editors.QuestionInfo;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.MeasureService;
import gov.va.escreening.transformer.EditorsQuestionViewTransformer;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.webservice.Response;

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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.*;

@Controller
@RequestMapping(value = "/dashboard/services")
public class QuestionRestController extends RestController{

	private static final Logger logger = LoggerFactory.getLogger(QuestionRestController.class);

	private final EditorsDelegate editorsViewDelegate;
	private final MeasureService measureService;
	private final MeasureRepository measureRepo;
	private final AssessmentVariableService assessmentVariableService;

	@Autowired
	QuestionRestController(MeasureRepository measureRepo, 
			MeasureService measureService,
			EditorsDelegate editorsViewDelegate,
			AssessmentVariableService assessmentVariableService){
		this.measureRepo = checkNotNull(measureRepo);
		this.measureService = checkNotNull(measureService);
		this.editorsViewDelegate = checkNotNull(editorsViewDelegate);
		this.assessmentVariableService = checkNotNull(assessmentVariableService);
	}

	@RequestMapping(value = "/questions/{questionId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<QuestionInfo> updateQuestion(
			@PathVariable("questionId") Integer questionId,
			@RequestBody QuestionInfo question,
			HttpServletRequest request) {
		logRequest(logger, request);
		
		Measure updatedMeasure = measureRepo.updateMeasure(EditorsQuestionViewTransformer.transformQuestionInfo(question));
		QuestionInfo updatedQuestionInfo = EditorsQuestionViewTransformer.transformQuestion(updatedMeasure);
		
		return successResponse(updatedQuestionInfo);
	}

	@RequestMapping(value = "/questions/{questionId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<QuestionInfo>  getQuestion(@PathVariable("questionId") Integer questionId,
			HttpServletRequest request) {
		logRequest(logger, request);
		
		// Call service class here instead of hard coding it.
		Measure measure = editorsViewDelegate.findMeasure(questionId);
		return successResponse(EditorsQuestionViewTransformer.transformQuestion(measure));
	}

	@RequestMapping(value="/questions/{measureId}/answers", method = RequestMethod.GET, produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<List<MeasureAnswerDTO>> getMeasureAnswerValues(@PathVariable Integer measureId,
			HttpServletRequest request){

		logRequest(logger, request);
		return successResponse(measureService.getMeasureAnswerValues(measureId));
	}

	@RequestMapping(value="/questions/{measureId}/answers/{answerId}", method = RequestMethod.GET,  produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<MeasureAnswerDTO> getMeasureAnswer(@PathVariable Integer measureId, @PathVariable Integer answerId,  
			HttpServletRequest request){

		logRequest(logger, request);
		return successResponse(measureService.getMeasureAnswer(measureId));
	}

	@RequestMapping(value="/questions/{measureId}/answers/{answerId}/assessment-variables", method = RequestMethod.GET,  produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<List<AssessmentVariableDto>> getMeasureAnswerAssessmentVariable(@PathVariable Integer measureId, @PathVariable Integer answerId,  
			HttpServletRequest request){

		logRequest(logger, request);
		return successResponse((List<AssessmentVariableDto>) ImmutableList.of(new AssessmentVariableDto(assessmentVariableService.getAssessmentVarsForAnswer(answerId))));
	}
	
	@RequestMapping(value="/questions/{measureId}/validations", method = RequestMethod.GET,  produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<List<MeasureValidationSimpleDTO>> getMeasureValidations(@PathVariable Integer measureId, 
			HttpServletRequest request){

		logRequest(logger, request);
		return successResponse(measureService.getMeasureValidations(measureId));
	}
}
