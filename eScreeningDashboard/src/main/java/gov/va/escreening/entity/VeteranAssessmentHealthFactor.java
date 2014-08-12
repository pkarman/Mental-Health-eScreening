package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author jjinn
 */
@Entity
@Table(name = "veteran_assessment_health_factor")
@NamedQueries({
        @NamedQuery(name = "VeteranAssessmentHealthFactor.findAll", query = "SELECT v FROM VeteranAssessmentHealthFactor v") })
public class VeteranAssessmentHealthFactor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "veteran_assessment_health_factor_id")
    private Integer veteranAssessmentHealthFactorId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
    @ManyToOne(optional = false)
    private VeteranAssessment veteranAssessment;
    @JoinColumn(name = "health_factor_id", referencedColumnName = "health_factor_id")
    @ManyToOne(optional = false)
    private HealthFactor healthFactor;

    public VeteranAssessmentHealthFactor() {
    }

    public VeteranAssessmentHealthFactor(Integer veteranAssessmentHealthFactorId) {
        this.veteranAssessmentHealthFactorId = veteranAssessmentHealthFactorId;
    }

    public VeteranAssessmentHealthFactor(Integer veteranAssessmentHealthFactorId, Date dateCreated) {
        this.veteranAssessmentHealthFactorId = veteranAssessmentHealthFactorId;
        this.dateCreated = dateCreated;
    }

    public Integer getVeteranAssessmentHealthFactorId() {
        return veteranAssessmentHealthFactorId;
    }

    public void setVeteranAssessmentHealthFactorId(Integer veteranAssessmentHealthFactorId) {
        this.veteranAssessmentHealthFactorId = veteranAssessmentHealthFactorId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public VeteranAssessment getVeteranAssessment() {
        return veteranAssessment;
    }

    public void setVeteranAssessment(VeteranAssessment veteranAssessment) {
        this.veteranAssessment = veteranAssessment;
    }

    public HealthFactor getHealthFactor() {
        return healthFactor;
    }

    public void setHealthFactor(HealthFactor healthFactor) {
        this.healthFactor = healthFactor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veteranAssessmentHealthFactorId != null ? veteranAssessmentHealthFactorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeteranAssessmentHealthFactor)) {
            return false;
        }
        VeteranAssessmentHealthFactor other = (VeteranAssessmentHealthFactor) object;
        if ((this.veteranAssessmentHealthFactorId == null && other.veteranAssessmentHealthFactorId != null)
                || (this.veteranAssessmentHealthFactorId != null && !this.veteranAssessmentHealthFactorId
                        .equals(other.veteranAssessmentHealthFactorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.VeteranAssessmentHealthFactor[ veteranAssessmentHealthFactorId="
                + veteranAssessmentHealthFactorId + " ]";
    }

}
