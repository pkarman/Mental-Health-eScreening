package gov.va.escreening.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.domain.MeasureTypeEnum;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.Validation;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.AssessmentVariableType;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureType;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MeasureRepositoryImpl extends AbstractHibernateRepository<Measure>
        implements MeasureRepository {

    @Autowired
    ValidationRepository validationRepo;

    @Autowired
    MeasureTypeRepository measureTypeRepo;
    
    @Autowired
    AssessmentVariableRepository assessmentVariableRepository;

    public MeasureRepositoryImpl() {
        super();

        setClazz(Measure.class);
    }

    @Override
    public List<Measure> getMeasureForTypeSurvey(Integer surveyId,
                                                 MeasureType measureType) {

        String sql = "SELECT m FROM Measure m JOIN m.surveyMeasureResponseList smrl JOIN smrl.survey s "
                + "WHERE m.measureType = :measureTypeId AND "
                + "s.surveyId = :surveyId";

        TypedQuery<Measure> query = entityManager.createQuery(sql,
                Measure.class);
        query.setParameter("measureTypeId", measureType);
        query.setParameter("surveyId", surveyId);

        List<Measure> measures = query.getResultList();

        return measures;
    }

    @Override
    public List<Integer> getChildMeasureIds(Measure parentMeasure) {

        String sql = "SELECT m FROM Measure m  "
                + "WHERE m.parent = :parentMeasure";

        TypedQuery<Measure> query = entityManager.createQuery(sql,
                Measure.class);
        query.setParameter("parentMeasure", parentMeasure);

        List<Measure> measures = query.getResultList();

        List<Integer> measureIds = new ArrayList<Integer>();
        for (Measure measure : measures)
            measureIds.add(measure.getMeasureId());

        return measureIds;
    }

    @Override
    public List<Measure> getChildMeasures(Measure parentMeasure) {
        String sql = "SELECT m FROM Measure m  "
                + "WHERE m.parent = :parentMeasure";

        TypedQuery<Measure> query = entityManager.createQuery(sql,
                Measure.class);
        query.setParameter("parentMeasure", parentMeasure);

        List<Measure> measures = query.getResultList();
        return measures;
    }

    // TODO this needs to be modified to take into account an identified vs
    // de-identified export
    @Override
    public List<Measure> getMeasuresBySurvey(Integer surveyId) {

        String sql = "SELECT m FROM Measure m JOIN m.surveyMeasureResponseList smrl JOIN smrl.survey s "
                + "WHERE s.surveyId = :surveyId ";

        TypedQuery<Measure> query = entityManager.createQuery(sql,
                Measure.class);
        query.setParameter("surveyId", surveyId);

        List<Measure> measures = query.getResultList();

        return measures;
    }

    @Override
    @Transactional
    public List<gov.va.escreening.dto.ae.Measure> getMeasureDtoBySurveyID(
            int surveyID) {
        String sql = "SELECT m.* "
                + " FROM survey_page sp "
                + " INNER JOIN survey_page_measure spm ON sp.survey_page_id = spm.survey_page_id "
                + " INNER JOIN measure m ON spm.measure_id = m.measure_id "
                + " WHERE sp.survey_id = :surveyID";

        Query q = entityManager.createNativeQuery(sql, Measure.class);
        q.setParameter("surveyID", surveyID);

        List<Measure> measures = q.getResultList();

        List<gov.va.escreening.dto.ae.Measure> result = new ArrayList<gov.va.escreening.dto.ae.Measure>();

        for (Measure m : measures) {
            result.add(new gov.va.escreening.dto.ae.Measure(m));
        }
        return result;
    }

    @Override
    @Transactional
    public gov.va.escreening.dto.ae.Measure updateMeasure(
            gov.va.escreening.dto.ae.Measure measureDto) {

        updateMeasureEntity(measureDto);
        Measure m = findOne(measureDto.getMeasureId());

        return new gov.va.escreening.dto.ae.Measure(m);
    }

    @Override
    public gov.va.escreening.dto.ae.Measure createMeasure(
            gov.va.escreening.dto.ae.Measure measureDto) {

        createMeasureEntity(measureDto);
        return measureDto;
    }

    private Measure updateMeasureEntity(
            gov.va.escreening.dto.ae.Measure measureDto) {

        Measure m = findOne(measureDto.getMeasureId());

        Map<Integer,MeasureAnswer> removedAnswers = copyFromDTO(m, measureDto);
        validateMeasure(m);
        update(m);
        updatedAssessmentVar(m, removedAnswers);
        assignParent(m, measureDto.getChildMeasures());

        return m;
    }
    
    private void validateMeasure(Measure m){
    	if(m.getMeasureType() == null){
        	ErrorBuilder.throwing(AssessmentEngineDataValidationException.class)
        	.toAdmin("This error normally represents an issue in the Module editor. Measure: " + m)
        	.toUser("Question type is required. Please call support.")
        	.throwIt();
        }
        if(m.getDisplayOrder() == null){
        	ErrorBuilder.throwing(AssessmentEngineDataValidationException.class)
        	.toAdmin("This error normally represents an issue in the Module editor. Measure: " + m)
        	.toUser("Question display order is required. Please call support.")
        	.throwIt();
        }
    }


    private Measure createMeasureEntity(
            gov.va.escreening.dto.ae.Measure measureDto) {
        
        Measure m = new Measure();
        Map<Integer,MeasureAnswer> removedAnswers = copyFromDTO(m, measureDto);
        validateMeasure(m);
        addDefaultAnswers(m);
        create(m);
        updatedAssessmentVar(m, removedAnswers);
        
        measureDto.setMeasureId(m.getMeasureId());
        assignParent(m, measureDto.getChildMeasures());

        return m;
    }

    private void assignParent(Measure parent, List<gov.va.escreening.dto.ae.Measure> childMeasures) {
        if (childMeasures != null) {
            for (gov.va.escreening.dto.ae.Measure cm : childMeasures) {
                Measure dbMeasure = findOne(cm.getMeasureId());
                dbMeasure.setParent(parent);
                update(dbMeasure);
            }
        }
    }
    
    private void addDefaultAnswers(Measure m){
    	//if this is freetext and no answer was given, add it
        if(m.getMeasureType().getMeasureTypeId() == MeasureTypeEnum.FREETEXT.getMeasureTypeId()
        		&& (m.getMeasureAnswerList() == null || m.getMeasureAnswerList().isEmpty())){
        	
        	MeasureAnswer textAnswer = new MeasureAnswer();
        	textAnswer.setDisplayOrder(1);
        	textAnswer.setMeasure(m);
        	m.setMeasureAnswerList(Lists.newArrayList(textAnswer));
        }
    }
    
    /**
     * Updates or initializes the assessment variable list for the given measure. A call to update the measure is needed after calling this.
     * @param measure
     */
    private void updatedAssessmentVar(Measure measure, Map<Integer,MeasureAnswer> removedAnswers) {
    	List<AssessmentVariable> avList = measure.getAssessmentVariableList();
    	if(avList == null){
    		avList = Lists.newArrayList();
    		measure.setAssessmentVariableList(avList);
    	}
    	if(avList.isEmpty()){
    		avList.add(new AssessmentVariable());
    	}
    	
    	//TODO: there really should be a one to one relationship between measure and AV which would simplify this
        for(AssessmentVariable av : avList){
        	av.setMeasure(measure);
        	av.setAssessmentVariableTypeId(new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE));
        	av.setDisplayName(measure.getVariableName() != null ? measure.getVariableName() : "");
        	av.setDescription(measure.getMeasureText());
        	if(av.getAssessmentVariableId() == null || av.getAssessmentVariableId() < 0){
        		assessmentVariableRepository.create(av);
        	}
        }
        
        updateAnswerAssessmentVar(measure, removedAnswers.values());
    }
    
    private void updateAnswerAssessmentVar(Measure measure, Collection<MeasureAnswer> removedAnswers){
    	
    	//TODO: Decide if we are going to be saving reports which means we can remove AV if the associated object is remove, or since now we are keeping measure_answers around as orphans, we keep the associated AV
    	//delete AV for removed answers
//    	if(removedAnswers != null){
//	    	for(MeasureAnswer ma : removedAnswers){
//	        	List<AssessmentVariable> vars = ma.getAssessmentVariableList();
//	        	if(vars != null){
//	        		for(AssessmentVariable av : vars){
//	        			assessmentVariableRepository.delete(av);
//	        		}
//	        	}
//	        }
//    	}
    	
    	//update or create for answers that are in measure
    	if(measure.getMeasureAnswerList() != null){
    	for(MeasureAnswer ma : measure.getMeasureAnswerList()){
	        	List<AssessmentVariable> avList = ma.getAssessmentVariableList();
	        	if(avList == null){
	        		avList = Lists.newArrayList();
	        		ma.setAssessmentVariableList(avList);
	        	}
	        	if(avList.isEmpty()){
	        		avList.add(new AssessmentVariable());
	        	}
	        	
	        	for(AssessmentVariable av : avList){
	            	av.setMeasureAnswer(ma);
	            	av.setAssessmentVariableTypeId(new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER));
	            	av.setDisplayName(ma.getExportName() != null ? ma.getExportName() : "");
	            	av.setDescription(ma.getAnswerText());
	            	if(av.getAssessmentVariableId() == null || av.getAssessmentVariableId() < 0){
	            		assessmentVariableRepository.create(av);
	            	}
	            }
	    	}
    	}
    }

    /**
     * 
     * @param m
     * @param measureDto
     * @return the answers which were deleted from the measure 
     */
    private Map<Integer,MeasureAnswer> copyFromDTO(Measure m, gov.va.escreening.dto.ae.Measure measureDto) {
        m.setIsRequired(measureDto.getIsRequired());
        m.setIsPatientProtectedInfo(measureDto.getIsPPI());
        m.setIsMha(measureDto.getIsMha());
        m.setMeasureText(measureDto.getMeasureText());
        m.setVistaText(measureDto.getVistaText());
        m.setVariableName(measureDto.getVariableName());
        m.setDisplayOrder(measureDto.getDisplayOrder());
        m.setIsPatientProtectedInfo(measureDto.getIsPPI());
        m.setMeasureType(measureTypeRepo.findMeasureTypeByName(measureDto.getMeasureType().trim()));


        List<Answer> answerList = measureDto.getAnswers();
        Map<Integer, Answer> modifiedAnswerMap = Maps.newHashMap();
        List<Answer> newAnswerList = Lists.newArrayList();
        for (Answer a : answerList) {
            if (a.getAnswerId() != null) {
                modifiedAnswerMap.put(a.getAnswerId(), a);
            } else {
                newAnswerList.add(a);
            }
        }

        Map<Integer,MeasureAnswer> removedAnswers = Maps.newHashMap();
        List<MeasureAnswer> maList = m.getMeasureAnswerList();
        if(maList == null){
        	maList = Lists.newArrayList();
        	m.setMeasureAnswerList(maList);
        }

        for (MeasureAnswer ma : maList) {
            Answer answerDto = modifiedAnswerMap.get(ma.getMeasureAnswerId());
            if(answerDto != null){
            	updateMeasureAnswer(m, ma, answerDto);
            }
            else{
            	removedAnswers.put(ma.getMeasureAnswerId(), ma);
            	//orphan the measure answer (this is required)
            	ma.setMeasure(null);
            }
        }
        //remove measures that were deleted
        maList.removeAll(removedAnswers.values());
        
        for (Answer newAnswer : newAnswerList) {
            maList.add(updateMeasureAnswer(m, new MeasureAnswer(), newAnswer));
        }

        if (m.getMeasureValidationList() == null) {
            m.setMeasureValidationList(new ArrayList<MeasureValidation>());
        } else
            m.getMeasureValidationList().clear();

        if (measureDto.getValidations() != null) {
            for (Validation mvdto : measureDto.getValidations()) {
                gov.va.escreening.entity.Validation v = validationRepo
                        .findValidationByCode(mvdto.getName());
                MeasureValidation mv = new MeasureValidation();
                if ("boolean".equalsIgnoreCase(v.getDataType())) {
                    mv.setBooleanValue(Integer.valueOf(mvdto.getValue()));
                } else if ("number".equalsIgnoreCase(v.getDataType())) {
                    mv.setNumberValue(Integer.valueOf(mvdto.getValue()));
                } else {
                    mv.setTextValue(mvdto.getValue());
                }

                mv.setDateCreated(Calendar.getInstance().getTime());
                mv.setMeasure(m);

                mv.setValidation(v);
                m.getMeasureValidationList().add(mv);
            }

        }

        if (measureDto.getChildMeasures() != null) {

            for (gov.va.escreening.dto.ae.Measure child : measureDto
                    .getChildMeasures()) {

                if (child.getMeasureId() == null) {
                    createMeasure(child);
                    m.addChild(findOne(child.getMeasureId()));
                } else {
                    updateMeasure(child);
                }
            }
        }
        return removedAnswers;
    }

    private MeasureAnswer updateMeasureAnswer(Measure m, MeasureAnswer ma, Answer answerDto) {
        if (answerDto != null) {
            ma.setAnswerText(answerDto.getAnswerText());
            ma.setExportName(deriveExportName(m, answerDto));
            ma.setVistaText(answerDto.getVistaText());
            ma.setAnswerType(answerDto.getAnswerType());
            ma.setCalculationValue(answerDto.getCalculationValue());
            ma.setDisplayOrder(answerDto.getDisplayOrder());
            ma.setMeasure(m);
        }
        return ma;
    }

    private String deriveExportName(Measure m, Answer answerDto) {
        String xn=answerDto.getExportName();
        return xn==null||xn.isEmpty()?m.getVariableName():xn;
    }
}