package gov.va.escreening.service;

import gov.va.escreening.domain.MeasureTypeEnum;
import gov.va.escreening.dto.MeasureAnswerDTO;
import gov.va.escreening.dto.MeasureValidationSimpleDTO;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureType;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.MeasureTypeRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MeasureServiceImpl implements MeasureService {

    @Autowired
    private MeasureTypeRepository measureTypeRepository;
    @Autowired
    private MeasureRepository measureRepository;
	@Autowired
	private MeasureAnswerRepository measureAnswerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Measure> getMeasureIdsForTableTypeQuestions(Integer surveyId) {
        MeasureType type = measureTypeRepository.findOne(MeasureTypeEnum.TABLEQUESTION.getMeasureTypeId());
        List<Measure> parentMeasures = measureRepository.getMeasureForTypeSurvey(surveyId, type);
        return parentMeasures;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Measure> getMeasuresBySurvey(Integer surveyId) {
        List<Measure> measures = measureRepository.getMeasuresBySurvey(surveyId);
        return measures;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasureType> loadAllMeasureTypes() {
        List<MeasureType> l = measureTypeRepository.findAll();
        return l;
    }

    @Override
    @Transactional(readOnly = true)
    public gov.va.escreening.dto.ae.Measure findMeasure(Integer measureId) {
        Measure dbMeasure=measureRepository.findOne(measureId);
        if(dbMeasure == null){
			ErrorBuilder.throwing(EntityNotFoundException.class)
			.toAdmin("No measure with ID: " + measureId)
			.toUser("An invlid question ID has been sent. Please contact support")
			.throwIt();
		}
        return new gov.va.escreening.dto.ae.Measure(dbMeasure);
    }
    
    @Override
    @Transactional(readOnly = true)
	public List<MeasureAnswerDTO> getMeasureAnswerValues(Integer measureId) {
		List<MeasureAnswer> answers = measureAnswerRepository.getAnswersForMeasure(measureId);
		
		List<MeasureAnswerDTO> answerDTOs = new ArrayList<MeasureAnswerDTO> ();
		if (answers!=null && answers.size() >0){
			for (MeasureAnswer a : answers )
			{
				answerDTOs.add(new MeasureAnswerDTO(a));
			}
		}
		
		return answerDTOs;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MeasureValidationSimpleDTO> getMeasureValidations(
			Integer measureId) {
		gov.va.escreening.entity.Measure measure = measureRepository.findOne(measureId);
		
		List<MeasureValidationSimpleDTO> results = new ArrayList<MeasureValidationSimpleDTO>();
		
		if (measure!=null && measure.getMeasureValidationList()!=null && measure.getMeasureValidationList().size()>0){
			for(MeasureValidation mv : measure.getMeasureValidationList()){
				MeasureValidationSimpleDTO sDTO = new MeasureValidationSimpleDTO();
				
				sDTO.setValidateId(mv.getValidation().getValidationId());
				if (mv.getBooleanValue()!=null){
					sDTO.setValue(mv.getBooleanValue()+"");
				}else if (mv.getNumberValue()!=null){
					sDTO.setValue(mv.getNumberValue()+"");
				}
				else{
					sDTO.setValue(mv.getTextValue());
				}
				
				results.add(sDTO);
			}
		}
		return results;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MeasureAnswerDTO getMeasureAnswer(Integer measureAnswerId){
		MeasureAnswer answer = null;
		
		if(measureAnswerId != null){
			answer = measureAnswerRepository.findOne(measureAnswerId);
		}
		
		if(answer == null){
			ErrorBuilder.throwing(EntityNotFoundException.class)
			.toAdmin("Question answer Id (measureAnswer) of " + measureAnswerId + " does not exist.")
			.toUser("Invalid answer ID. Please report this to support.")
			.throwIt();
		}
		
		return new MeasureAnswerDTO(answer);
	}
}