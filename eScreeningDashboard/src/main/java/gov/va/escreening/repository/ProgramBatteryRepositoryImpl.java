package gov.va.escreening.repository;

import gov.va.escreening.entity.ProgramBattery;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ProgramBatteryRepositoryImpl extends AbstractHibernateRepository<ProgramBattery> implements ProgramBatteryRepository {

	public ProgramBatteryRepositoryImpl() {
		super();

		setClazz(ProgramBattery.class);
	}

	@Override
	public List<ProgramBattery> findAllByBatteryId(int batteryId) {

		List<ProgramBattery> resultList = new ArrayList<ProgramBattery>();
		String sql = "SELECT pb FROM ProgramBattery pb JOIN pb.battery b WHERE b.batteryId = :batteryId";

		TypedQuery<ProgramBattery> query = entityManager.createQuery(sql, ProgramBattery.class);
		query.setParameter("batteryId", batteryId);

		resultList = query.getResultList();

		return resultList;
	}

	@Override
	public List<ProgramBattery> findAllByProgramId(int programId) {
		
		List<ProgramBattery> resultList = new ArrayList<ProgramBattery>();
		String sql = "SELECT pb FROM ProgramBattery pb JOIN pb.program p WHERE p.programId = :programId";
		
		TypedQuery<ProgramBattery> query = entityManager.createQuery(sql, ProgramBattery.class);
		query.setParameter("programId", programId);
		
		resultList = query.getResultList();
		
		return resultList;
	}
}
