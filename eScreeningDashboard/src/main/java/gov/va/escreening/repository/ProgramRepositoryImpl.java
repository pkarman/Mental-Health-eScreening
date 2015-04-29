package gov.va.escreening.repository;

import gov.va.escreening.entity.Program;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ProgramRepositoryImpl extends AbstractHibernateRepository<Program> implements
        ProgramRepository {

    public ProgramRepositoryImpl() {
        super();

        setClazz(Program.class);
    }

    @Override
    public List<Program> getProgramList() {

        List<Program> resultList = new ArrayList<Program>();
        String sql = "SELECT p FROM Program p ORDER BY p.programId";

        TypedQuery<Program> query = entityManager.createQuery(sql, Program.class);

        resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<Program> getActiveProgramList() {

        List<Program> resultList = new ArrayList<Program>();
        String sql = "SELECT p FROM Program p WHERE p.isDisabled = false ORDER BY p.programId";

        TypedQuery<Program> query = entityManager.createQuery(sql, Program.class);

        resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<Program> findByProgramIdList(List<Integer> programIdList) {

        List<Program> resultList = new ArrayList<Program>();

        String sql = "SELECT p FROM Program p WHERE p.programId IN (:programIdList)";

        if (programIdList != null && programIdList.size() > 0) {
            TypedQuery<Program> query = entityManager.createQuery(sql, Program.class);
            query.setParameter("programIdList", programIdList);
            resultList = query.getResultList();
        }

        return resultList;
    }

	@Override
	public List<Program> findProgramForUser(int id) {
	    //Here a native query is being created so the "p" in "program" must be lowercase. This may work on Windoze but not on Linux where case matters. 
		String sql = "SELECT p.* FROM program p inner join user_program up on p.program_id=up.program_id WHERE up.user_id=:id "
				+ "and p.is_disabled=0";
		Query q = entityManager.createNativeQuery(sql, Program.class).setParameter("id", id);
		
		@SuppressWarnings("unchecked")
        List<Program> result = q.getResultList();
		
		return result;
	}
}