package gov.va.escreening.repository;

import gov.va.escreening.entity.NoteTitle;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class NoteTitleRepositoryImpl extends AbstractHibernateRepository<NoteTitle> implements NoteTitleRepository {

    public NoteTitleRepositoryImpl() {
        super();

        setClazz(NoteTitle.class);
    }

    @Override
    public List<NoteTitle> getNoteTitleList(Integer programId) {

        List<NoteTitle> resultList = new ArrayList<NoteTitle>();

        String sql = "SELECT nt FROM NoteTitle nt JOIN nt.noteTitleMapList ntm JOIN ntm.program p WHERE p.programId = :programId ORDER BY nt.name";

        TypedQuery<NoteTitle> query = entityManager.createQuery(sql, NoteTitle.class);
        query.setParameter("programId", programId);

        resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<NoteTitle> getNoteTitleList() {

        List<NoteTitle> resultList = new ArrayList<NoteTitle>();

        String sql = "SELECT nt FROM NoteTitle nt ORDER BY nt.name";

        TypedQuery<NoteTitle> query = entityManager.createQuery(sql, NoteTitle.class);

        resultList = query.getResultList();

        return resultList;
    }
}
