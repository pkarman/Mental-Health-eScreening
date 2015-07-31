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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "system_property")
@NamedQueries({
    @NamedQuery(name = "SystemProperty.findAll", query = "SELECT s FROM SystemProperty s")})
public class SystemProperty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "system_property_id")
    private Integer systemPropertyId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "boolean_value")
    private Integer booleanValue;
    @Column(name = "number_value")
    private Integer numberValue;
    @Column(name = "text_value")
    private String textValue;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public SystemProperty() {
    }

    public SystemProperty(Integer systemPropertyId) {
        this.systemPropertyId = systemPropertyId;
    }

    public SystemProperty(Integer systemPropertyId, String name, Date dateCreated) {
        this.systemPropertyId = systemPropertyId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getSystemPropertyId() {
        return systemPropertyId;
    }

    public void setSystemPropertyId(Integer systemPropertyId) {
        this.systemPropertyId = systemPropertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Integer booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Integer getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Integer numberValue) {
        this.numberValue = numberValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
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
        hash += (systemPropertyId != null ? systemPropertyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemProperty)) {
            return false;
        }
        SystemProperty other = (SystemProperty) object;
        if ((this.systemPropertyId == null && other.systemPropertyId != null) || (this.systemPropertyId != null && !this.systemPropertyId.equals(other.systemPropertyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.SystemProperty[ systemPropertyId=" + systemPropertyId + " ]";
    }
    
}
