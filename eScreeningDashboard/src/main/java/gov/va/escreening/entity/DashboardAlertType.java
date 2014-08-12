package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author jjinn
 */
@Entity
@Table(name = "dashboard_alert_type")
@NamedQueries({
        @NamedQuery(name = "DashboardAlertType.findAll", query = "SELECT d FROM DashboardAlertType d") })
public class DashboardAlertType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dashboard_alert_type_id")
    private Integer dashboardAlertTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dashboardAlertType")
    private List<DashboardAlert> dashboardAlertList;

    public DashboardAlertType() {
    }

    public DashboardAlertType(Integer dashboardAlertTypeId) {
        this.dashboardAlertTypeId = dashboardAlertTypeId;
    }

    public DashboardAlertType(Integer dashboardAlertTypeId, String name, Date dateCreated) {
        this.dashboardAlertTypeId = dashboardAlertTypeId;
        this.name = name;
    }

    public Integer getDashboardAlertTypeId() {
        return dashboardAlertTypeId;
    }

    public void setDashboardAlertTypeId(Integer dashboardAlertTypeId) {
        this.dashboardAlertTypeId = dashboardAlertTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DashboardAlert> getDashboardAlertList() {
        return dashboardAlertList;
    }

    public void setDashboardAlertList(List<DashboardAlert> dashboardAlertList) {
        this.dashboardAlertList = dashboardAlertList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dashboardAlertTypeId != null ? dashboardAlertTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DashboardAlertType)) {
            return false;
        }
        DashboardAlertType other = (DashboardAlertType) object;
        if ((this.dashboardAlertTypeId == null && other.dashboardAlertTypeId != null)
                || (this.dashboardAlertTypeId != null && !this.dashboardAlertTypeId.equals(other.dashboardAlertTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.DashboardAlertType[ dashboardAlertTypeId=" + dashboardAlertTypeId
                + " ]";
    }

}
