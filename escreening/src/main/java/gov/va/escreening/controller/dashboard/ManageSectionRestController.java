package gov.va.escreening.controller.dashboard;

import java.util.List;

import gov.va.escreening.controller.RestController;
import gov.va.escreening.delegate.EditorsDelegate;
import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.dto.editors.*;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.webservice.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/dashboard")
public class ManageSectionRestController extends RestController{

	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(type=EditorsDelegate.class)
	private EditorsDelegate editorsViewDelegate;

    @Resource(type=MeasureRepository.class)
    private MeasureRepository measureRepo;

    @RequestMapping(value = "/services/sections", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<List<SurveySectionInfo>> getSections(HttpServletRequest request) {        
        logRequest(logger, request);
        return successResponse(editorsViewDelegate.getSections());
    }

    @RequestMapping(value = "/services/sections/{sectionId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<SurveySectionInfo> getSection(
            @PathVariable("sectionId") Integer sectionId,
            HttpServletRequest request) {
        
        logRequest(logger, request);
        return successResponse(editorsViewDelegate.getSection(sectionId));
    }

    @RequestMapping(value = "/services/sections", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response<SurveySectionInfo> addSection(
            @RequestBody SurveySectionInfo surveySectionInfo,
            HttpServletRequest request) {

        logRequest(logger, request);
        
        ErrorResponse errorResponse = new ErrorResponse();

        if (surveySectionInfo != null) {
            logger.trace(surveySectionInfo.toString());

            // Data validation.
            if (StringUtils.isBlank(surveySectionInfo.getName())) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Section Name", "Section Name is required.");
            } else if (surveySectionInfo.getName().length() > 50) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Section Name", "Section Name should be less than 50 characters.");
            }

            if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
                throw new AssessmentEngineDataValidationException(errorResponse);
            }

            // Call service class here.
            Integer surveySectionId = editorsViewDelegate.createSection(surveySectionInfo);
            surveySectionInfo.setSurveySectionId(surveySectionId);
            logger.trace("surveySectionId: " + surveySectionId);
        } else {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Section Object", "Cannot be null.");
        }

        return successResponse(surveySectionInfo);
    }

    @RequestMapping(value = "/services/sections/{sectionId}", method = {RequestMethod.PUT}, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response<SurveySectionInfo> updateSection(
			@PathVariable("sectionId") Integer sectionId,
			@RequestBody SurveySectionInfo surveySectionInfo,
			HttpServletRequest request) {
        
        logRequest(logger, request);
		
        ErrorResponse errorResponse = new ErrorResponse();
        SurveySectionInfo updatedSurveySectionInfo = null;
        if(sectionId != null) {
            // Data validation.
            if (StringUtils.isBlank(surveySectionInfo.getName())) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Survey Section Name", "Section Name is required.");
            } else if (surveySectionInfo.getName().length() > 50) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Survey Section Name", "Section Name should be less than 50 characters.");
            }

            if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
                throw new AssessmentEngineDataValidationException(errorResponse);
            }

            // Call service class here.
            surveySectionInfo.setSurveySectionId(sectionId);
            updatedSurveySectionInfo = editorsViewDelegate.updateSection(surveySectionInfo);
        }

        return successResponse(updatedSurveySectionInfo);
	}

	@RequestMapping(value = "/services/sections/{sectionId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Response<Integer> deleteSection(
			@PathVariable("sectionId") Integer sectionId,
			HttpServletRequest request) {
	    logRequest(logger, request);
		editorsViewDelegate.deleteSection(sectionId);
        return successResponse(sectionId);
	}
}
