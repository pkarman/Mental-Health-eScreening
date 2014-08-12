package gov.va.escreening.repository;

import gov.va.escreening.entity.Program;

import java.util.ArrayList;
import java.util.List;

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
}