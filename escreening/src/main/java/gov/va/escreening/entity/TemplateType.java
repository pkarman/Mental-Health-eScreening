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
@Table(name = "template_type")
@NamedQueries({
    @NamedQuery(name = "TemplateType.findAll", query = "SELECT t FROM TemplateType t")})
public class TemplateType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "template_type_id")
    private Integer templateTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "templateType")
    private List<Template> templateList;

    public TemplateType() {
    }

    public TemplateType(Integer templateTypeId) {
        this.templateTypeId = templateTypeId;
    }

    public TemplateType(Integer templateTypeId, String name, Date dateCreated) {
        this.templateTypeId = templateTypeId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getTemplateTypeId() {
        return templateTypeId;
    }

    public void setTemplateTypeId(Integer templateTypeId) {
        this.templateTypeId = templateTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateTypeId != null ? templateTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplateType)) {
            return false;
        }
        TemplateType other = (TemplateType) object;
        if ((this.templateTypeId == null && other.templateTypeId != null) || (this.templateTypeId != null && !this.templateTypeId.equals(other.templateTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.TemplateType[ templateTypeId=" + templateTypeId + " ]";
    }
    
}
