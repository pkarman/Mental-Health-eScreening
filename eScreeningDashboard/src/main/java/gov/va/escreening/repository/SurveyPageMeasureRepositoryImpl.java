package gov.va.escreening.repository;

import gov.va.escreening.entity.SurveyPageMeasure;

import org.springframework.stereotype.Repository;

@Repository
public class SurveyPageMeasureRepositoryImpl extends AbstractHibernateRepository<SurveyPageMeasure> implements SurveyPageMeasureRepository {

	public SurveyPageMeasureRepositoryImpl() {
		setClazz(SurveyPageMeasure.class);
	}

}
