package gov.va.escreening.service;

import gov.va.escreening.dto.VeteranAssessmentNoteDto;
import gov.va.escreening.entity.VeteranAssessmentNote;
import gov.va.escreening.repository.VeteranAssessmentNoteRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class VeteranAssessmentNoteServiceImpl implements VeteranAssessmentNoteService {

    private VeteranAssessmentNoteRepository veteranAssessmentNoteRepository;

    @Autowired
    public void setVeteranAssessmentNoteRepository(VeteranAssessmentNoteRepository veteranAssessmentNoteRepository) {
        this.veteranAssessmentNoteRepository = veteranAssessmentNoteRepository;
    }

    public VeteranAssessmentNoteServiceImpl() {

    }

    @Transactional(readOnly = true)
    @Override
    public List<VeteranAssessmentNoteDto> findAllByVeteranAssessmentId(int veteranAssessmentId) {

        List<VeteranAssessmentNoteDto> resultList = new ArrayList<VeteranAssessmentNoteDto>();

        List<VeteranAssessmentNote> veteranAssessmentNoteList = veteranAssessmentNoteRepository
                .findAllByVeteranAssessmentId(veteranAssessmentId);

        for (VeteranAssessmentNote veteranAssessmentNote : veteranAssessmentNoteList) {
            VeteranAssessmentNoteDto veteranAssessmentNoteDto = new VeteranAssessmentNoteDto();
            
            veteranAssessmentNoteDto.setVeteranAssessmentNoteId(veteranAssessmentNote.getVeteranAssessmentNoteId());
            veteranAssessmentNoteDto.setVeteranAssessmentId(veteranAssessmentNote.getVeteranAssessment().getVeteranAssessmentId());
            veteranAssessmentNoteDto.setClinicalNoteId(veteranAssessmentNote.getClinicalNote().getClinicalNoteId());
            veteranAssessmentNoteDto.setClinicalNoteTitle(veteranAssessmentNote.getClinicalNote().getTitle());

            resultList.add(veteranAssessmentNoteDto);
        }

        return resultList;
    }

}
