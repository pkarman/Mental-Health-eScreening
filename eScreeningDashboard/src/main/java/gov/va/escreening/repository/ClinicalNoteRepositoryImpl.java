package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicalNote;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ClinicalNoteRepositoryImpl extends AbstractHibernateRepository<ClinicalNote> implements
        ClinicalNoteRepository {

    public ClinicalNoteRepositoryImpl() {
        super();

        setClazz(ClinicalNote.class);
    }

    @Override
    public List<ClinicalNote> findAllForProgramId(int programId) {

        List<ClinicalNote> resultList = new ArrayList<ClinicalNote>();

        String sql = "SELECT cn FROM ClinicalNote cn ORDER BY cn.title";

        TypedQuery<ClinicalNote> query = entityManager.createQuery(sql, ClinicalNote.class);
        resultList = query.getResultList();

        return resultList;
    }
}
