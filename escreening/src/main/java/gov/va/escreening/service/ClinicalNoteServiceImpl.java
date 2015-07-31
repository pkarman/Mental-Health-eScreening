package gov.va.escreening.service;

import gov.va.escreening.domain.ClinicalNoteDto;
import gov.va.escreening.entity.ClinicalNote;
import gov.va.escreening.repository.ClinicalNoteRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ClinicalNoteServiceImpl implements ClinicalNoteService {

    private ClinicalNoteRepository clinicalNoteRepository;

    @Autowired
    public void setClinicalNoteRepository(ClinicalNoteRepository clinicalNoteRepository) {
        this.clinicalNoteRepository = clinicalNoteRepository;
    }

    public ClinicalNoteServiceImpl() {

    }

    @Transactional(readOnly = true)
    @Override
    public List<ClinicalNoteDto> findAllForProgramId(int programId) {

        List<ClinicalNoteDto> resultList = new ArrayList<ClinicalNoteDto>();

        List<ClinicalNote> clinicalNoteList = clinicalNoteRepository.findAllForProgramId(programId);

        for (ClinicalNote clinicalNote : clinicalNoteList) {
            ClinicalNoteDto clinicalNoteDto = new ClinicalNoteDto();

            clinicalNoteDto.setClinicalNoteId(clinicalNote.getClinicalNoteId());
            clinicalNoteDto.setClinicalNoteIen(clinicalNote.getVistaIen());
            clinicalNoteDto.setTitle(clinicalNote.getTitle());

            resultList.add(clinicalNoteDto);
        }

        return resultList;
    }

}
