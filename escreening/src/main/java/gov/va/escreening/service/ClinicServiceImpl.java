package gov.va.escreening.service;

import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.ProgramDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.entity.ClinicProgram;
import gov.va.escreening.entity.Program;
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
            clinicDtoList.add(createClinicDto(clinic));
        }

        return clinicDtoList;
    }

    private ClinicDto createClinicDto(Clinic clinic) {
        ClinicDto clinicDto = new ClinicDto();
        clinicDto.setClinicId(clinic.getClinicId());
        clinicDto.setClinicName(clinic.getName());
        clinicDto.setClinicIen(clinic.getVistaIen());

        if (clinic.getClinicProgramList() != null) {
            for (ClinicProgram cp : clinic.getClinicProgramList()) {
                clinicDto.getProgramDtos().add(createProgramDto(cp.getProgram()));
            }
        }
        return clinicDto;
    }

    private ProgramDto createProgramDto(Program program) {
        return new ProgramDto(program.getProgramId(), program.getName(), program.getIsDisabled());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getClinicOptionsByName(String query) {
        List<Clinic> clinics = clinicRepository.getClinicsByName(query);
        List<DropDownObject> clinicList = new ArrayList<DropDownObject>(clinics.size());
        for (Clinic c : clinics) {
            clinicList.add(new DropDownObject(c.getVistaIen(), c.getName()));
        }
        return clinicList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getDropDownObjectsByProgramId(int programId) {

        List<DropDownObject> dropDownObjectList = new ArrayList<DropDownObject>();

        List<ClinicProgram> cpList = clinicRepository.findByProgramId(programId);

        for (ClinicProgram cp : cpList) {
            Clinic clinic = cp.getClinic();
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

    @Override
    public String getClinicNameById(Integer clinicId) {
        Clinic c = clinicRepository.findOne(clinicId);
        if (c != null) {
            return c.getName();
        }
        return null;
    }

    @Override
    public List<Integer> getAllVeteranIds(Integer clinicId) {
        return clinicRepository.getAllVeteranIds(clinicId);
    }

    @Override
    @Transactional
    public ClinicDto getClinicDtoByIen(String varClinicIen) {
        int dividerPos = varClinicIen.indexOf("|");
        String clinicIen = varClinicIen.substring(0, dividerPos);
        String clinicName = varClinicIen.substring(dividerPos + 1);

        Clinic clinicByIen = clinicRepository.findByIen(clinicIen);
        Clinic selectedClinic = selectClinicByIenAndName(clinicByIen, clinicIen, clinicName);

        return createClinicDto(selectedClinic);
    }

    private Clinic selectClinicByIenAndName(Clinic clinicByIen, String clinicIen, String clinicName) {
        Clinic selectedClinic = null;
        if (clinicName.equals(clinicByIen.getName())) {
            selectedClinic = clinicByIen;
        } else {
            clinicByIen.setVistaIen(null);
            selectedClinic = new Clinic(clinicIen, clinicName);
            clinicRepository.create(selectedClinic);
        }

        return selectedClinic;
    }
}
