package gov.va.escreening.service;

import gov.va.escreening.entity.ProgramSurvey;
import gov.va.escreening.repository.ProgramSurveyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProgramSurveyServiceImpl implements ProgramSurveyService {

    private ProgramSurveyRepository programSurveyRepository;

    @Autowired
    public void setProgramSurveyRepository(ProgramSurveyRepository programSurveyRepository) {
        this.programSurveyRepository = programSurveyRepository;
    }

    public ProgramSurveyServiceImpl() {

    }

    public List<ProgramSurvey> findAllByProgramId(int programId) {

        List<ProgramSurvey> programSurveyList = programSurveyRepository.findAllByProgramId(programId);

        return programSurveyList;
    }

}
