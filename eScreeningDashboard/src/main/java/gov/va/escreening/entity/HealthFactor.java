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
@Table(name = "health_factor")
@NamedQueries({
        @NamedQuery(name = "HealthFactor.findAll", query = "SELECT h FROM HealthFactor h") })
public class HealthFactor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "health_factor_id")
    private Integer healthFactorId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "vista_ien")
    private String vistaIen;
    @Column(name = "is_historical")
    private Boolean isHistorical;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "healthFactor")
    private List<HealthFactorDialogPrompt> healthFactorDialogPromptList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "healthFactor")
    private List<VeteranAssessmentHealthFactor> veteranAssessmentHealthFactorList;
    @JoinColumn(name = "clinical_reminder_id", referencedColumnName = "clinical_reminder_id")
    @ManyToOne(optional = false)
    private ClinicalReminder clinicalReminder;

    public HealthFactor() {
    }

    public HealthFactor(Integer healthFactorId) {
        this.healthFactorId = healthFactorId;
    }

    public HealthFactor(Integer healthFactorId, String name, String vistaIen, Date dateCreated) {
        this.healthFactorId = healthFactorId;
        this.name = name;
        this.vistaIen = vistaIen;
        this.dateCreated = dateCreated;
    }

    public Integer getHealthFactorId() {
        return healthFactorId;
    }

    public void setHealthFactorId(Integer healthFactorId) {
        this.healthFactorId = healthFactorId;
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

    public Boolean getIsHistorical() {
        return isHistorical;
    }

    public void setIsHistorical(Boolean isHistorical) {
        this.isHistorical = isHistorical;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<HealthFactorDialogPrompt> getHealthFactorDialogPromptList() {
        return healthFactorDialogPromptList;
    }

    public void setHealthFactorDialogPromptList(List<HealthFactorDialogPrompt> healthFactorDialogPromptList) {
        this.healthFactorDialogPromptList = healthFactorDialogPromptList;
    }

    public List<VeteranAssessmentHealthFactor> getVeteranAssessmentHealthFactorList() {
        return veteranAssessmentHealthFactorList;
    }

    public void setVeteranAssessmentHealthFactorList(
            List<VeteranAssessmentHealthFactor> veteranAssessmentHealthFactorList) {
        this.veteranAssessmentHealthFactorList = veteranAssessmentHealthFactorList;
    }

    public ClinicalReminder getClinicalReminder() {
        return clinicalReminder;
    }

    public void setClinicalReminder(ClinicalReminder clinicalReminder) {
        this.clinicalReminder = clinicalReminder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (healthFactorId != null ? healthFactorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HealthFactor)) {
            return false;
        }
        HealthFactor other = (HealthFactor) object;
        if ((this.healthFactorId == null && other.healthFactorId != null)
                || (this.healthFactorId != null && !this.healthFactorId.equals(other.healthFactorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.HealthFactor[ healthFactorId=" + healthFactorId + " ]";
    }

}
