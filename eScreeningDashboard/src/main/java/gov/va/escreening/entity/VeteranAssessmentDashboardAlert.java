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
@Table(name = "veteran_assessment_dashboard_alert")
@NamedQueries({
        @NamedQuery(name = "VeteranAssessmentDashboardAlert.findAll", query = "SELECT v FROM VeteranAssessmentDashboardAlert v") })
public class VeteranAssessmentDashboardAlert implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Basic(optional = false)
    @Column(name = "veteran_assessment_dashboard_alert_id")
    private Integer veteranAssessmentDashboardAlertId;
    
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @JoinColumn(name = "dashboard_alert_id", referencedColumnName = "dashboard_alert_id")
    @ManyToOne(optional = false)
    private DashboardAlert dashboardAlert;
    
    @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
    @ManyToOne(optional = false)
    private VeteranAssessment veteranAssessment;

    public VeteranAssessmentDashboardAlert() {
    }

    public VeteranAssessmentDashboardAlert(Integer veteranAssessmentDashboardAlertId) {
        this.veteranAssessmentDashboardAlertId = veteranAssessmentDashboardAlertId;
    }

    public VeteranAssessmentDashboardAlert(Integer veteranAssessmentDashboardAlertId, Date dateCreated) {
        this.veteranAssessmentDashboardAlertId = veteranAssessmentDashboardAlertId;
        this.dateCreated = dateCreated;
    }

    public Integer getVeteranAssessmentDashboardAlertId() {
        return veteranAssessmentDashboardAlertId;
    }

    public void setVeteranAssessmentDashboardAlertId(Integer veteranAssessmentDashboardAlertId) {
        this.veteranAssessmentDashboardAlertId = veteranAssessmentDashboardAlertId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DashboardAlert getDashboardAlert() {
        return dashboardAlert;
    }

    public void setDashboardAlert(DashboardAlert dashboardAlert) {
        this.dashboardAlert = dashboardAlert;
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
        hash += (veteranAssessmentDashboardAlertId != null ? veteranAssessmentDashboardAlertId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeteranAssessmentDashboardAlert)) {
            return false;
        }
        VeteranAssessmentDashboardAlert other = (VeteranAssessmentDashboardAlert) object;
        if ((this.veteranAssessmentDashboardAlertId == null && other.veteranAssessmentDashboardAlertId != null)
                || (this.veteranAssessmentDashboardAlertId != null && !this.veteranAssessmentDashboardAlertId
                        .equals(other.veteranAssessmentDashboardAlertId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.VeteranAssessmentDashboardAlert[ veteranAssessmentDashboardAlertId="
                + veteranAssessmentDashboardAlertId + " ]";
    }

}
