package gov.va.escreening.service;

import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.repository.ClinicRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public List<Clinic> getClinics() {
        return clinicRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClinicDto> getClinicDtoList() {

        List<ClinicDto> clinicDtoList = new ArrayList<ClinicDto>();

        List<Clinic> clinicList = clinicRepository.findAll();

        for (Clinic clinic : clinicList) {
            ClinicDto clinicDto = new ClinicDto();
            clinicDto.setClinicId(clinic.getClinicId());
            clinicDto.setClinicName(clinic.getName());

            if (clinic.getProgram() != null) {
                clinicDto.setProgramId(clinic.getProgram().getProgramId());
                clinicDto.setProgramName(clinic.getProgram().getName());
            }

            clinicDtoList.add(clinicDto);
        }

        return clinicDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getDropDownObjectsByProgramId(int programId) {

        List<DropDownObject> dropDownObjectList = new ArrayList<DropDownObject>();

        List<Clinic> clinicList = clinicRepository.findByProgramId(programId);

        for (Clinic clinic : clinicList) {
            dropDownObjectList.add(new DropDownObject(clinic.getClinicId().toString(), clinic.getName()));
        }

        return dropDownObjectList;
    }

    @Override
    public Integer create(String name, String vistaIen) {

        Clinic clinic = new Clinic();
        clinic.setName(name);
        clinic.setVistaIen(vistaIen);

        clinicRepository.create(clinic);

        return clinic.getClinicId();
    }
}
