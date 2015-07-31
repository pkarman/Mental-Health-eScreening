package gov.va.escreening.service;

import gov.va.escreening.entity.ClinicalReminderSurvey;
import gov.va.escreening.repository.ClinicalReminderSurveyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ClinicalReminderSurveyServiceImpl implements ClinicalReminderSurveyService {

    private ClinicalReminderSurveyRepository clinicalReminderSurveyRepository;

    @Autowired
    public void setClinicalReminderSurveyRepository(ClinicalReminderSurveyRepository clinicalReminderSurveyRepository) {
        this.clinicalReminderSurveyRepository = clinicalReminderSurveyRepository;
    }

    public ClinicalReminderSurveyServiceImpl() {

    }

    public List<ClinicalReminderSurvey> findAllByVistaIen(String vistaIen) {

        List<ClinicalReminderSurvey> clinicalReminderSurveyList = clinicalReminderSurveyRepository
                .findAllByVistaIen(vistaIen);
        
        return clinicalReminderSurveyList;
    }

}
