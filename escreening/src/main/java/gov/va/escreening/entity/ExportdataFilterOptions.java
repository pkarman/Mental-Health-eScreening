/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jocchiuzzo
 */
@Entity
@Table(name = "exportdata_filter_options")
@NamedQueries({
    @NamedQuery(name = "ExportdataFilterOptions.findAll", query = "SELECT e FROM ExportdataFilterOptions e")})
public class ExportdataFilterOptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "exportdata_filter_options_id")
    private Integer exportdataFilterOptionsId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "num_days")
    private int numDays;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public ExportdataFilterOptions() {
    }

    public ExportdataFilterOptions(Integer exportdataFilterOptionsId) {
        this.exportdataFilterOptionsId = exportdataFilterOptionsId;
    }

    public ExportdataFilterOptions(Integer exportdataFilterOptionsId, String name, int numDays, Date dateCreated) {
        this.exportdataFilterOptionsId = exportdataFilterOptionsId;
        this.name = name;
        this.numDays = numDays;
        this.dateCreated = dateCreated;
    }

    public Integer getExportdataFilterOptionsId() {
        return exportdataFilterOptionsId;
    }

    public void setExportdataFilterOptionsId(Integer exportdataFilterOptionsId) {
        this.exportdataFilterOptionsId = exportdataFilterOptionsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exportdataFilterOptionsId != null ? exportdataFilterOptionsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExportdataFilterOptions)) {
            return false;
        }
        ExportdataFilterOptions other = (ExportdataFilterOptions) object;
        if ((this.exportdataFilterOptionsId == null && other.exportdataFilterOptionsId != null) || (this.exportdataFilterOptionsId != null && !this.exportdataFilterOptionsId.equals(other.exportdataFilterOptionsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.va.escreeningdashboard.entity.ExportdataFilterOptions[ exportdataFilterOptionsId=" + exportdataFilterOptionsId + " ]";
    }
    
}
