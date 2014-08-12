package gov.va.escreening.service;

import gov.va.escreening.entity.ClinicSurvey;
import gov.va.escreening.repository.ClinicSurveyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ClinicSurveyServiceImpl implements ClinicSurveyService {

    private ClinicSurveyRepository clinicSurveyRepository;

    @Autowired
    public void setClinicSurveyRepository(ClinicSurveyRepository clinicSurveyRepository) {
        this.clinicSurveyRepository = clinicSurveyRepository;
    }

    public ClinicSurveyServiceImpl() {

    }

    public List<ClinicSurvey> findAllByClinicId(int clinicId) {

        List<ClinicSurvey> clinicSurveyList = clinicSurveyRepository.findAllByClinicId(clinicId);

        return clinicSurveyList;
    }

}
