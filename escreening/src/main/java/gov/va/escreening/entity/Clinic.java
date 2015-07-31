package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "clinic")
@NamedQueries({ @NamedQuery(name = "Clinic.findAll", query = "SELECT c FROM Clinic c") })
public class Clinic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clinic_id")
    private Integer clinicId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "vista_ien")
    private String vistaIen;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinic")
    private List<UserClinic> userClinicList;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne
    private Program program;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinic")
    private List<VeteranAssessment> veteranAssessmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinic")
    private List<ClinicSurvey> clinicSurveyList;

    public Clinic() {
    }

    public Clinic(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Clinic(Integer clinicId, String name, Date dateCreated) {
        this.clinicId = clinicId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVistaIen() {
        return vistaIen;
    }

    public void setVistaIen(String vistaIen) {
        this.vistaIen = vistaIen;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<UserClinic> getUserClinicList() {
        return userClinicList;
    }

    public void setUserClinicList(List<UserClinic> userClinicList) {
        this.userClinicList = userClinicList;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public List<VeteranAssessment> getVeteranAssessmentList() {
        return veteranAssessmentList;
    }

    public void setVeteranAssessmentList(List<VeteranAssessment> veteranAssessmentList) {
        this.veteranAssessmentList = veteranAssessmentList;
    }

    public List<ClinicSurvey> getClinicSurveyList() {
        return clinicSurveyList;
    }

    public void setClinicSurveyList(List<ClinicSurvey> clinicSurveyList) {
        this.clinicSurveyList = clinicSurveyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clinicId != null ? clinicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Clinic)) {
            return false;
        }
        Clinic other = (Clinic) object;
        if ((this.clinicId == null && other.clinicId != null)
                || (this.clinicId != null && !this.clinicId
                        .equals(other.clinicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.Clinic[ clinicId=" + clinicId + " ]";
    }

}
