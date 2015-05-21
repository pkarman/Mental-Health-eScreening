package gov.va.escreening.service;

import gov.va.escreening.domain.ClinicalReminderDto;
import gov.va.escreening.entity.ClinicalReminder;
import gov.va.escreening.repository.ClinicalReminderRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ClinicalReminderServiceImpl implements ClinicalReminderService {

    private ClinicalReminderRepository clinicalReminderRepository;

    @Autowired
    public void setClinicalReminderRepository(ClinicalReminderRepository clinicalReminderRepository) {
        this.clinicalReminderRepository = clinicalReminderRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClinicalReminderDto> findAll() {
        List<ClinicalReminder> list = clinicalReminderRepository.findAll();
        
        List<ClinicalReminderDto> dtoList = new ArrayList<ClinicalReminderDto>(list.size());
        for(ClinicalReminder c : list)
        {
        	dtoList.add(new ClinicalReminderDto(c.getClinicalReminderId(), c.getPrintName()+"("+c.getVistaIen() + ")"));
        }
        return dtoList;
    }

    @Override
    public Integer create(String name, String vistaIen, String printName, String clinicalReminderClassCode) {

        ClinicalReminder clinicalReminder = new ClinicalReminder();
        clinicalReminder.setName(name);
        clinicalReminder.setVistaIen(vistaIen);
        clinicalReminder.setPrintName(printName);
        clinicalReminder.setClinicalReminderClassCode(clinicalReminderClassCode);

        clinicalReminderRepository.create(clinicalReminder);

        return clinicalReminder.getClinicalReminderId();
    }
}
