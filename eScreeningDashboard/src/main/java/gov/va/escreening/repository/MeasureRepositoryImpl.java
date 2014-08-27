package gov.va.escreening.repository;

import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.Validation;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureType;
import gov.va.escreening.entity.MeasureValidation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MeasureRepositoryImpl extends AbstractHibernateRepository<Measure>
		implements MeasureRepository {

	@Autowired
	ValidationRepository validationRepo;

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
			gov.va.escreening.dto.ae.Measure dto = new gov.va.escreening.dto.ae.Measure(
					m, null, null);
			result.add(dto);
		}
		return result;
	}

	@Override
	@Transactional
	public gov.va.escreening.dto.ae.Measure updateMeasure(
			gov.va.escreening.dto.ae.Measure measureDto) {
		Measure m = updateMeasureEntity(measureDto);
		commit();

		gov.va.escreening.dto.ae.Measure dto = new gov.va.escreening.dto.ae.Measure(
				m, null, null);

		return dto;
	}

	private Measure updateMeasureEntity(
			gov.va.escreening.dto.ae.Measure measureDto) {
		Measure m = findOne(measureDto.getMeasureId());

		m.setIsRequired(measureDto.getIsRequired());
		m.setIsPatientProtectedInfo(measureDto.getIsPPI());
		m.setIsMha(measureDto.getIsMha());
		m.setMeasureText(measureDto.getMeasureText());
		m.setVistaText(measureDto.getVistaText());

		List<Answer> answerList = measureDto.getAnswers();
		Map<Integer, Answer> answerMap = new HashedMap<Integer, Answer>();
		for (Answer a : answerList) {
			answerMap.put(a.getAnswerId(), a);
		}

		for (MeasureAnswer ma : m.getMeasureAnswerList()) {
			Answer answerDto = answerMap.get(ma.getMeasureAnswerId());
			if (answerDto != null) {
				ma.setAnswerText(answerDto.getAnswerText());
				ma.setExportName(answerDto.getExportName());
				ma.setVistaText(answerDto.getVistaText());
			}
		}

		m.setMeasureValidationList(new ArrayList<MeasureValidation>());
		if (measureDto.getValidations() != null) {
			for (Validation mvdto : measureDto.getValidations()) {
				gov.va.escreening.entity.Validation v = validationRepo
						.findValidationByCode(mvdto.getName());
				MeasureValidation mv = new MeasureValidation();
				if ("boolean".equalsIgnoreCase(v.getDataType())) {
					mv.setBooleanValue(Integer.getInteger(mvdto.getValue()));
				} else if ("number".equalsIgnoreCase(v.getDataType())) {
					mv.setNumberValue(Integer.getInteger(mvdto.getValue()));
				} else {
					mv.setTextValue(mvdto.getValue());
				}

				mv.setDateCreated(Calendar.getInstance().getTime());
				mv.setMeasure(m);

				mv.setValidation(v);
				m.getMeasureValidationList().add(mv);
			}
		}

		update(m);

		if (measureDto.getChildMeasures() != null) {
			for (gov.va.escreening.dto.ae.Measure child : measureDto
					.getChildMeasures()) {
				updateMeasure(child);
			}
		}
		return m;
	}
}