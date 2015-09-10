package gov.va.escreening.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author jjinn
 */
@Entity
@Table(name = "clinic_program")
@NamedQueries({
        @NamedQuery(name = "ClinicProgram.findAll", query = "SELECT c FROM ClinicProgram c")})
public class ClinicProgram implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clinic_program_id")
    private Integer clinicProgramId;

    @JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id")
    @ManyToOne(optional = false)
    private Clinic clinic;

    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne(optional = false)
    private Program program;

    public ClinicProgram() {
    }

    public ClinicProgram(Integer clinicProgramId) {
        this.clinicProgramId = clinicProgramId;
    }

    public ClinicProgram(Clinic clinic, Program program) {
        this.program = program;
        this.clinic = clinic;
    }

    public Integer getClinicProgramId() {
        return clinicProgramId;
    }

    public void setClinicProgramId(Integer clinicProgramId) {
        this.clinicProgramId = clinicProgramId;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clinicProgramId != null ? clinicProgramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClinicProgram)) {
            return false;
        }
        ClinicProgram other = (ClinicProgram) object;
        if ((this.clinicProgramId == null && other.clinicProgramId != null)
                || (this.clinicProgramId != null && !this.clinicProgramId.equals(other.clinicProgramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.ClinicProgram[ clinicProgramId=" + clinicProgramId + " ]";
    }
}
