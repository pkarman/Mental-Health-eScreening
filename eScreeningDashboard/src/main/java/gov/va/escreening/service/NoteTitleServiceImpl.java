package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.NoteTitle;
import gov.va.escreening.repository.NoteTitleRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class NoteTitleServiceImpl implements NoteTitleService {

    private NoteTitleRepository noteTitleRepository;

    @Autowired
    public void setNoteTitleRepository(NoteTitleRepository noteTitleRepository) {
        this.noteTitleRepository = noteTitleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getNoteTitleList(Integer programId) {

        List<DropDownObject> resultList = new ArrayList<DropDownObject>();

        List<NoteTitle> noteTitleList = noteTitleRepository.getNoteTitleList(programId);

        if (noteTitleList != null) {
            for (NoteTitle noteTitle : noteTitleList) {
                resultList.add(new DropDownObject(noteTitle.getNoteTitleId().toString(), noteTitle.getName()));
            }
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getNoteTitleList() {

        List<DropDownObject> resultList = new ArrayList<DropDownObject>();

        List<NoteTitle> noteTitleList = noteTitleRepository.getNoteTitleList();

        if (noteTitleList != null) {
            for (NoteTitle noteTitle : noteTitleList) {
                resultList.add(new DropDownObject(noteTitle.getNoteTitleId().toString(), noteTitle.getName()));
            }
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<NoteTitle> findAll() {
        return noteTitleRepository.findAll();
    }

    @Override
    public Integer create(String name, String vistaIen) {

        NoteTitle noteTitle = new NoteTitle();
        noteTitle.setName(name);
        noteTitle.setVistaIen(vistaIen);

        noteTitleRepository.create(noteTitle);

        return noteTitle.getNoteTitleId();
    }

}
