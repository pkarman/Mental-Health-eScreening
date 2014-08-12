package gov.va.escreening.repository;

import gov.va.escreening.entity.BatterySurvey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class BatterySurveyRepositoryImpl extends AbstractHibernateRepository<BatterySurvey> implements
        BatterySurveyRepository {

    public BatterySurveyRepositoryImpl() {
        super();

        setClazz(BatterySurvey.class);
    }

    @Override
    public List<BatterySurvey> findAllByBatteryId(int batteryId) {

        List<BatterySurvey> resultList = new ArrayList<BatterySurvey>();
        String sql = "SELECT bs FROM BatterySurvey bs JOIN bs.battery b WHERE b.batteryId = :batteryId ORDER BY bs.batterySurveyId";

        TypedQuery<BatterySurvey> query = entityManager.createQuery(sql, BatterySurvey.class);
        query.setParameter("batteryId", batteryId);

        resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<BatterySurvey> getBatterySurveyList() {

        List<BatterySurvey> resultList = new ArrayList<BatterySurvey>();
        String sql = "SELECT bs FROM BatterySurvey bs JOIN bs.battery b JOIN bs.survey s ORDER BY b.name, s.name, bs.batterySurveyId";

        TypedQuery<BatterySurvey> query = entityManager.createQuery(sql, BatterySurvey.class);

        resultList = query.getResultList();

        return resultList;
    }
}
