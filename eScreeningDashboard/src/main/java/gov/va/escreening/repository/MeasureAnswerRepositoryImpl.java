package gov.va.escreening.repository;

import gov.va.escreening.entity.MeasureAnswer;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class MeasureAnswerRepositoryImpl extends AbstractHibernateRepository<MeasureAnswer> implements MeasureAnswerRepository {

    public MeasureAnswerRepositoryImpl(){
        super();

        setClazz(MeasureAnswer.class);
    }
    
    @Override
    public List<MeasureAnswer> getAnswersForMeasure(Integer measureId) {
    	
    	String sql = "SELECT ma FROM MeasureAnswer ma JOIN ma.measure m "
    			+ "WHERE m.measureId = :measureId "
    			+ "ORDER BY ma.displayOrder";

        TypedQuery<MeasureAnswer> query = entityManager.createQuery(sql, MeasureAnswer.class);
        query.setParameter("measureId", measureId);
        
        List<MeasureAnswer> measureAnswers = query.getResultList();
        return measureAnswers;
    }
}