package gov.va.escreening.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gov.va.escreening.dto.DropDownObject;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ClinicDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private final List<ProgramDto> programDtos = Lists.newArrayList();
    private Integer clinicId;
    private String clinicName;
    private String clinicIen;

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicIen() {
        return clinicIen;
    }

    public void setClinicIen(String clinicIen) {
        this.clinicIen = clinicIen;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public List<ProgramDto> getProgramDtos() {
        return programDtos;
    }

    public Set<DropDownObject> getProgramsAsDropDownList() {
        Set<DropDownObject> programs = Sets.newLinkedHashSet();
        for (ProgramDto pdto : programDtos) {
            programs.add(new DropDownObject(pdto.getProgramId().toString(), pdto.getName()));
        }
        return programs;
    }
}
