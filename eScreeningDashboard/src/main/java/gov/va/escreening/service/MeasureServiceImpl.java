package gov.va.escreening.service;

import gov.va.escreening.domain.MeasureTypeEnum;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureType;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.MeasureTypeRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MeasureServiceImpl implements MeasureService {

    @Autowired
    private MeasureTypeRepository measureTypeRepository;
    @Autowired
    private MeasureRepository measureRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Measure> getMeasureIdsForTableTypeQuestions(Integer surveyId) {
    	MeasureType type = measureTypeRepository.findOne(MeasureTypeEnum.TABLEQUESTION.getMeasureTypeId());
    	List<Measure> parentMeasures = measureRepository.getMeasureForTypeSurvey(surveyId, type);
    	return parentMeasures;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Measure> getMeasuresBySurvey(Integer surveyId) {
    	List<Measure> measures = measureRepository.getMeasuresBySurvey(surveyId);
    	return measures;
    }
}