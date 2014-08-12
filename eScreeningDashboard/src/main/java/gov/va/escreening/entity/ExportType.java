/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jocchiuzzo
 */
@Entity
@Table(name = "export_type")
@NamedQueries({
    @NamedQuery(name = "ExportType.findAll", query = "SELECT e FROM ExportType e")})
public class ExportType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "export_type_id")
    private Integer exportTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exportType")
    private List<ExportLog> exportLogList;

    public ExportType() {
    }

    public ExportType(Integer exportTypeId) {
        this.exportTypeId = exportTypeId;
    }

    public ExportType(Integer exportTypeId, String name, Date dateCreated) {
        this.exportTypeId = exportTypeId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getExportTypeId() {
        return exportTypeId;
    }

    public void setExportTypeId(Integer exportTypeId) {
        this.exportTypeId = exportTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<ExportLog> getExportLogList() {
        return exportLogList;
    }

    public void setExportLogList(List<ExportLog> exportLogList) {
        this.exportLogList = exportLogList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exportTypeId != null ? exportTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExportType)) {
            return false;
        }
        ExportType other = (ExportType) object;
        if ((this.exportTypeId == null && other.exportTypeId != null) || (this.exportTypeId != null && !this.exportTypeId.equals(other.exportTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.va.escreeningdashboard.entity.ExportType[ exportTypeId=" + exportTypeId + " ]";
    }
    
}
