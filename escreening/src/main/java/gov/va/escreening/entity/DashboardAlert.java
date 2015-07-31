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
@Table(name = "dashboard_alert")
@NamedQueries({
        @NamedQuery(name = "DashboardAlert.findAll", query = "SELECT d FROM DashboardAlert d") })
public class DashboardAlert implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dashboard_alert_id")
    private Integer dashboardAlertId;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "message")
    private String message;
    
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @JoinColumn(name = "dashboard_alert_type_id", referencedColumnName = "dashboard_alert_type_id")
    @ManyToOne(optional = false)
    private DashboardAlertType dashboardAlertType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dashboardAlert")
    private List<VeteranAssessmentDashboardAlert> veteranAssessmentDashboardAlertList;

    public DashboardAlert() {
    }

    public DashboardAlert(Integer dashboardAlertId) {
        this.dashboardAlertId = dashboardAlertId;
    }

    public DashboardAlert(Integer dashboardAlertId, String name, String message, Date dateCreated) {
        this.dashboardAlertId = dashboardAlertId;
        this.name = name;
        this.message = message;
        this.dateCreated = dateCreated;
    }

    public Integer getDashboardAlertId() {
        return dashboardAlertId;
    }

    public void setDashboardAlertId(Integer dashboardAlertId) {
        this.dashboardAlertId = dashboardAlertId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DashboardAlertType getDashboardAlertType() {
        return dashboardAlertType;
    }

    public void setDashboardAlertType(DashboardAlertType dashboardAlertType) {
        this.dashboardAlertType = dashboardAlertType;
    }

    public List<VeteranAssessmentDashboardAlert> getVeteranAssessmentDashboardAlertList() {
        return veteranAssessmentDashboardAlertList;
    }

    public void setVeteranAssessmentDashboardAlertList(
            List<VeteranAssessmentDashboardAlert> veteranAssessmentDashboardAlertList) {
        this.veteranAssessmentDashboardAlertList = veteranAssessmentDashboardAlertList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dashboardAlertId != null ? dashboardAlertId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DashboardAlert)) {
            return false;
        }
        DashboardAlert other = (DashboardAlert) object;
        if ((this.dashboardAlertId == null && other.dashboardAlertId != null)
                || (this.dashboardAlertId != null && !this.dashboardAlertId.equals(other.dashboardAlertId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.DashboardAlert[ dashboardAlertId=" + dashboardAlertId + " ]";
    }

}
