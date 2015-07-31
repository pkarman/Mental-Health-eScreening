package gov.va.escreening.variableresolver;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

@Transactional(noRollbackFor = {CouldNotResolveVariableException.class,
        AssessmentVariableInvalidValueException.class,
        UnsupportedOperationException.class,
        CouldNotResolveVariableValueException.class,
        UnsupportedOperationException.class, Exception.class})
public class MeasureAssessmentVariableResolverImpl implements
        MeasureAssessmentVariableResolver {

	//Please add to the constructor and do not use field based @Autowired	
	private final MeasureAnswerAssessmentVariableResolver measureAnswerVariableResolver;
    private final ChildResolverFunction selectOneChildResolverFunction;
    private final ChildResolverFunction selectMultiChildResolverFunction;

    private static final Logger logger = LoggerFactory
            .getLogger(MeasureAssessmentVariableResolverImpl.class);

	@Autowired
	public MeasureAssessmentVariableResolverImpl(
			MeasureAnswerAssessmentVariableResolver mavr){
		measureAnswerVariableResolver = checkNotNull(mavr);
		selectOneChildResolverFunction = new SelectOneChildResolverFunction();
		selectMultiChildResolverFunction = new SelectMultiChildResolverFunction();
	}

    @Override
    public AssessmentVariableDto resolveAssessmentVariable(
            AssessmentVariable assessmentVariable, 
            ResolverParameters params) {
        //logger.debug("Resolving measure variable with AV ID: {}", assessmentVariable.getAssessmentVariableId());
        
        Integer avId = assessmentVariable.getAssessmentVariableId();
        params.checkUnresolvable(avId);
        AssessmentVariableDto variableDto = params.getResolvedVariable(avId);
        if(variableDto == null){
            try{
                Integer measureId = assessmentVariable.getMeasure().getMeasureId();
                if (measureId == null)
                    throw new AssessmentVariableInvalidValueException(
                            String.format(
                                    "AssessmentVariable of type Measure did not conatain the required measureid"
                                    + " VeteranAssessment id was: %s, AssessmentVariable id was: %s",
                                    params.getAssessmentId(),
                                    assessmentVariable.getAssessmentVariableId()));
        
                variableDto = processMeasureType(assessmentVariable,
                        assessmentVariable.getMeasure(), params);
                
                if (variableDto == null)
                    throw new CouldNotResolveVariableException(
                            String.format(
                                    "Was unable to resolve the assessment variable for measureid: %s, assessmentId: %s",
                                    measureId, params.getAssessmentId()));
        
                //add resolved Dto to the cache
                params.addResolvedVariable(variableDto);
            }
            catch(Exception e){
                params.addUnresolvableVariable(assessmentVariable.getAssessmentVariableId());
                throw e;
            }
        }
        
        //logger.debug("Resolved answer variable with AV ID: {} to:\n{}", assessmentVariable.getAssessmentVariableId(), variableDto);
        return variableDto;
    }

    private AssessmentVariableDto processMeasureType(
            AssessmentVariable assessmentVariable,
            Measure measure, 
            ResolverParameters params){
        return processMeasureType(assessmentVariable, measure, params, null);
    }
    
    private AssessmentVariableDto processMeasureType(
            AssessmentVariable assessmentVariable,
            Measure measure, 
            ResolverParameters params,
            @Nullable
            List<Answer> responses) {

        Integer measureId = measure.getMeasureId();
        int measureTypeId = measure.getMeasureType().getMeasureTypeId();
        
        if(responses == null){
            responses = params.getMeasureResponses(measureId);
        }
        
        if(responses.isEmpty()
                && measureTypeId != AssessmentConstants.MEASURE_TYPE_TABLE
                && measureTypeId != AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX
                && measureTypeId != AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX)
            throw new CouldNotResolveVariableException(
                    String.format(
                            "There are no reponses for measure with Id: %s, measure's variable ID is %s, assessmentId: %s",
                            measureId, measure.getAssessmentVariable().getAssessmentVariableId(), params.getAssessmentId()));
        
        AssessmentVariableDto variableDto = null;

        switch (measureTypeId) {
		case AssessmentConstants.MEASURE_TYPE_FREE_TEXT:
		case AssessmentConstants.MEASURE_TYPE_READ_ONLY:
                variableDto = resolveFreeTextAssessmentVariableQuestion(
                        assessmentVariable, 
                        responses, params);
                break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE:
                variableDto = resolveSelectOneAssessmentVariableQuestion(
                        assessmentVariable, 
                        responses, params, false);
                break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI:
                variableDto = resolveSelectMultiAssessmentVariableQuestion(
                        assessmentVariable, 
                        responses, params);
                break;
		case AssessmentConstants.MEASURE_TYPE_TABLE:
                variableDto = resolveTableAssessmentVariableQuestion(
                        assessmentVariable, params);
                break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX:
			variableDto = resolveParentQuestion(assessmentVariable, 
			        params, selectMultiChildResolverFunction);
                break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX:
			variableDto = resolveParentQuestion(assessmentVariable, 
			        params, selectOneChildResolverFunction);
                break;
		case AssessmentConstants.MEASURE_TYPE_INSTRUCTION:
            default:
                throw new UnsupportedOperationException(
                        String.format(
                                "Resolve assessment variable for measure type of: %s is not supported.",
                                measureTypeId));
        }

        return variableDto;
    }

    private AssessmentVariableDto resolveFreeTextAssessmentVariableQuestion(
            AssessmentVariable assessmentVariable, 
            List<Answer> responses,
            ResolverParameters params) {
		
		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);
		addResolvedAnswerTo(questionVariableDto, responses.get(0), params);
		
        return questionVariableDto;
    }

    private AssessmentVariableDto resolveSelectOneAssessmentVariableQuestion(
            AssessmentVariable assessmentVariable, 
            List<Answer> responses,
            ResolverParameters params, 
            boolean getAll) {

		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

        // loop to find the first true value then process the answer
        for (Answer response : responses) {
            if(response.isTrue() || !Strings.isNullOrEmpty(response.getOtherAnswerResponse())){
				
				addResolvedAnswerTo(questionVariableDto, response, params);
				
				if(!getAll){
				    break; // found what we were looking for
				}
            }
        }

        return questionVariableDto;
    }	
	
    private AssessmentVariableDto resolveSelectMultiAssessmentVariableQuestion(
            AssessmentVariable assessmentVariable, 
            List<Answer> responses,
            ResolverParameters params) {

		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

        // loop to find all of the true values then add them to the collection
        for (Answer response : responses) {
            if (response.isTrue() || !Strings.isNullOrEmpty(response.getOtherAnswerResponse())) {
				
                // call the answer level to resolve the value
				addResolvedAnswerTo(questionVariableDto, response, params);
            }
        }

        return questionVariableDto;
    }

    private AssessmentVariableDto resolveTableAssessmentVariableQuestion(
            AssessmentVariable assessmentVariable, 
            ResolverParameters params) {

		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

        Measure parentMeasure = assessmentVariable.getMeasure();
        List<Answer> parentResponses = params.getMeasureResponses(parentMeasure.getMeasureId());
		Answer parentResponse = parentResponses == null || parentResponses.isEmpty() ? null
                : parentResponses.get(0); // there should only be one response
        
		// for the parent question for table type
        if (parentResponse != null && parentResponse.isTrue()) {
			addResolvedAnswerTo(questionVariableDto, parentResponse, params);
        } else {
            Set<Measure> childMeasures = parentMeasure.getChildren();
            if (childMeasures.size() == 0)
                throw new CouldNotResolveVariableException(
                        String.format(
								"Could not resolve Measure of type: %s, the measure was expected to have child measures but did not have any.  "
                                        + "MeasureId: %s, AssessmentVariableId: %s, assessmentId: %s",
								AssessmentConstants.MEASURE_TYPE_TABLE,
                                parentMeasure.getMeasureId(),
                                assessmentVariable.getAssessmentVariableId(),
                                params.getAssessmentId()));
            
            for (Measure childMeasure : childMeasures) {   
                Integer childMeasureId = childMeasure.getMeasureId();
                
                int rowCount = params.getMeasureRowCount(childMeasureId);
                
                for(int tabularRow = 0; tabularRow < rowCount; tabularRow++){
                    List<Answer> responses = params.getMeasureResponses(childMeasureId, tabularRow);
                    
					if (responses.isEmpty())
                        break;

                    try {
                        // otherwise we have a response to process
						AssessmentVariable childAV = childMeasure.getAssessmentVariable();
                        AssessmentVariableDto childQuestionVariableDto = processMeasureType(
                                childAV, childMeasure, params, responses);
						
						questionVariableDto.getChildren().add(childQuestionVariableDto);
						
					} catch ( AssessmentVariableInvalidValueException avive) {
						logger.warn(avive.getMessage());
						//this is so that when a child measure is missing, the parent should still 
						//be resolved.
					} catch (CouldNotResolveVariableException cnrve){
						logger.warn(cnrve.getMessage());
                        //this is so that when a child measure is missing, the parent should still
                        //be resolved.
                    }
                }
            }
        }
        return questionVariableDto;
    }

	/**
	 * Resolves response to an assessment variable for the measure answer represented 
	 * by the response, and appends this AV dto to the children of the passed in question AV.
	 * @param questionVariableDto the AV we are adding children to 
	 * @param response the veteran's response to the given question. This is important
	 * because there can be more than one response for the same given answer AV (due to table rows)
	 * @param params parameters for this resolve task
	 */
	private void addResolvedAnswerTo(AssessmentVariableDto questionVariableDto, 
	        Answer response, 
	        ResolverParameters params){
	    
		AssessmentVariable answerAv = params.getAnswerAv(response.getAnswerId());
		
		if(answerAv != null){
			// call the answer level to resolve the value
			AssessmentVariableDto answerVariableDto = measureAnswerVariableResolver
					.resolveAssessmentVariable(answerAv, params, response);
			
			questionVariableDto.getChildren().add(answerVariableDto);
		}
//		else{
//		    //We could call something here to get the AV for the given measure answer but that would be expensive
//		    //and if we are setting up our editors correctly, we should not have to this here
//			//logger.warn("The measure answer with ID {} was not in the measureAnswerHash.  This normally indicates that there is a mistake in the assessment variable bookkeeping for the service that is resolving (e.g. template, rules, formulas). All needed assessment variables must be associated to each of these system objects.", response.getAnswerId());
//		}
    }
	
	private AssessmentVariableDto resolveParentQuestion(
			AssessmentVariable assessmentVariable, 
			ResolverParameters params,
			ChildResolverFunction resolverFunction){
		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

		Measure parentMeasure = assessmentVariable.getMeasure();
		Set<Measure> childMeasures = parentMeasure.getChildren();
		if (childMeasures.size() == 0)
			throw new CouldNotResolveVariableException(
					String.format(
							"Could not resolve Measure of type: %s, the measure was expected to have child measures but did not have any.  "
									+ "MeasureId: %s, AssessmentVariableId: %s, assessmentId: %s",
							AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX,
							parentMeasure.getMeasureId(),
							assessmentVariable.getAssessmentVariableId(),
							params.getAssessmentId()));
		
		//resolve children
		for (Measure childMeasure : childMeasures) {
			resolverFunction.addChild(questionVariableDto, childMeasure, params);
		}
		
		return questionVariableDto;
    }
	
	//if only we had lamdas
	private interface ChildResolverFunction {
		void addChild(AssessmentVariableDto parentAvDto, 
		        Measure childMeasure, ResolverParameters params);
	}
	
	private class SelectOneChildResolverFunction implements ChildResolverFunction {
		@Override
		public void addChild(AssessmentVariableDto parentAvDto, 
                Measure childMeasure, ResolverParameters params){
			
		    List<Answer> childResponses = params.getMeasureResponses(childMeasure.getMeasureId());
		    
			parentAvDto.getChildren().add( 
					resolveSelectOneAssessmentVariableQuestion(
					        childMeasure.getAssessmentVariable(),
					        childResponses, params, true));
		}
	}
	
	private class SelectMultiChildResolverFunction implements ChildResolverFunction {
		@Override
		public void addChild(AssessmentVariableDto parentAvDto, 
                Measure childMeasure, ResolverParameters params){
			
		    List<Answer> childResponses = params.getMeasureResponses(childMeasure.getMeasureId());
		    
			parentAvDto.getChildren().add( 
					resolveSelectMultiAssessmentVariableQuestion(
					        childMeasure.getAssessmentVariable(),
					        childResponses, params));
		}
	}
}