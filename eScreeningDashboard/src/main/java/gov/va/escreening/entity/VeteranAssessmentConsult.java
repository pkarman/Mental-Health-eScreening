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
@Table(name = "veteran_assessment_consult")
@NamedQueries({
        @NamedQuery(name = "VeteranAssessmentConsult.findAll", query = "SELECT v FROM VeteranAssessmentConsult v") })
public class VeteranAssessmentConsult implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "veteran_assessment_consult_id")
    private Integer veteranAssessmentConsultId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "consult_id", referencedColumnName = "consult_id")
    @ManyToOne(optional = false)
    private Consult consult;
    @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
    @ManyToOne(optional = false)
    private VeteranAssessment veteranAssessment;

    public VeteranAssessmentConsult() {
    }

    public VeteranAssessmentConsult(Integer veteranAssessmentConsultId) {
        this.veteranAssessmentConsultId = veteranAssessmentConsultId;
    }

    public VeteranAssessmentConsult(Integer veteranAssessmentConsultId, Date dateCreated) {
        this.veteranAssessmentConsultId = veteranAssessmentConsultId;
        this.dateCreated = dateCreated;
    }

    public Integer getVeteranAssessmentConsultId() {
        return veteranAssessmentConsultId;
    }

    public void setVeteranAssessmentConsultId(Integer veteranAssessmentConsultId) {
        this.veteranAssessmentConsultId = veteranAssessmentConsultId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Consult getConsult() {
        return consult;
    }

    public void setConsult(Consult consult) {
        this.consult = consult;
    }

    public VeteranAssessment getVeteranAssessment() {
        return veteranAssessment;
    }

    public void setVeteranAssessment(VeteranAssessment veteranAssessment) {
        this.veteranAssessment = veteranAssessment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veteranAssessmentConsultId != null ? veteranAssessmentConsultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeteranAssessmentConsult)) {
            return false;
        }
        VeteranAssessmentConsult other = (VeteranAssessmentConsult) object;
        if ((this.veteranAssessmentConsultId == null && other.veteranAssessmentConsultId != null)
                || (this.veteranAssessmentConsultId != null && !this.veteranAssessmentConsultId
                        .equals(other.veteranAssessmentConsultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.VeteranAssessmentConsult[ veteranAssessmentConsultId="
                + veteranAssessmentConsultId + " ]";
    }

}
