package gov.va.escreening.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import gov.va.escreening.entity.MeasureType;

import org.springframework.stereotype.Repository;

@Repository
public class MeasureTypeRepositoryImpl extends AbstractHibernateRepository<MeasureType> implements MeasureTypeRepository {
    public MeasureTypeRepositoryImpl() {
        super();
        setClazz(MeasureType.class);
    }

	@Override
	public MeasureType findMeasureTypeByName(String name) 
		{
			String sql = "SELECT m FROM MeasureType m where m.name = :name ";

			TypedQuery<MeasureType> query = entityManager.createQuery(sql,
					MeasureType.class);
			query.setParameter("name", name);

			List<MeasureType> measureType = query.getResultList();

			if (measureType == null || measureType.size()==0)
			{
				return null;
			}
			else
				return measureType.get(0);
		}
	
}