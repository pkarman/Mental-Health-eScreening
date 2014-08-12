package gov.va.escreening.repository;

import gov.va.escreening.entity.Battery;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class BatteryRepositoryImpl extends AbstractHibernateRepository<Battery> implements BatteryRepository {

    public BatteryRepositoryImpl() {
        super();

        setClazz(Battery.class);
    }

    @Override
    public List<Battery> getBatteryList() {

        List<Battery> resultList = new ArrayList<Battery>();
        String sql = "SELECT b FROM Battery b ORDER BY b.name";

        TypedQuery<Battery> query = entityManager.createQuery(sql, Battery.class);

        resultList = query.getResultList();

        return resultList;
    }
}
