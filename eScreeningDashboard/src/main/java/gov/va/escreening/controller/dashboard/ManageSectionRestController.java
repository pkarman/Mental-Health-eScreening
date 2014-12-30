package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.EditorsViewDelegate;
import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.*;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.transformer.EditorsQuestionViewTransformer;
import gov.va.escreening.webservice.Response;
import gov.va.escreening.webservice.ResponseStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dashboard")
public class ManageSectionRestController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(type=EditorsViewDelegate.class)
	private EditorsViewDelegate editorsViewDelegate;

    @Resource(type=MeasureRepository.class)
    private MeasureRepository measureRepo;

    @RequestMapping(value = "/services/sections", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List getSections(@CurrentUser EscreenUser escreenUser) {
        logger.debug("getSections");

        return editorsViewDelegate.getSections();
    }

    @RequestMapping(value = "/services/sections/{sectionId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SurveySectionInfo getSection(
            @PathVariable("sectionId") Integer sectionId,
            @CurrentUser EscreenUser escreenUser) {

        return editorsViewDelegate.getSection(sectionId);
    }

    @RequestMapping(value = "/services/sections", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public SurveySectionInfo addSection(
            @RequestBody SurveySectionInfo surveySectionInfo,
            @CurrentUser EscreenUser escreenUser) {

        ErrorResponse errorResponse = new ErrorResponse();

        if (surveySectionInfo != null) {
            logger.debug(surveySectionInfo.toString());

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
            logger.debug("surveySectionId: " + surveySectionId);
        } else {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Section Object", "Cannot be null.");
        }

        return surveySectionInfo;
    }

    @RequestMapping(value = "/services/sections/{sectionId}", method = {RequestMethod.PUT}, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public SurveySectionInfo updateSection(
			@PathVariable("sectionId") Integer sectionId,
			@RequestBody SurveySectionInfo surveySectionInfo,
			@CurrentUser EscreenUser escreenUser) {
		logger.debug("updateSection");
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

        return updatedSurveySectionInfo;
	}

	@RequestMapping(value = "/services/sections/{sectionId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public int deleteSection(
			@PathVariable("sectionId") Integer sectionId,
			@CurrentUser EscreenUser escreenUser) {
		editorsViewDelegate.deleteSection(sectionId);
        return sectionId;
	}
}
