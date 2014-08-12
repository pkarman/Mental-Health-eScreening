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
@Table(name = "calculation_type")
@NamedQueries({
    @NamedQuery(name = "CalculationType.findAll", query = "SELECT c FROM CalculationType c")})
public class CalculationType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "calculation_type_id")
    private Integer calculationTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(mappedBy = "calculationType")
    private List<MeasureAnswer> measureAnswerList;

    public CalculationType() {
    }

    public CalculationType(Integer calculationTypeId) {
        this.calculationTypeId = calculationTypeId;
    }

    public CalculationType(Integer calculationTypeId, String name, Date dateCreated) {
        this.calculationTypeId = calculationTypeId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getCalculationTypeId() {
        return calculationTypeId;
    }

    public void setCalculationTypeId(Integer calculationTypeId) {
        this.calculationTypeId = calculationTypeId;
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

    public List<MeasureAnswer> getMeasureAnswerList() {
        return measureAnswerList;
    }

    public void setMeasureAnswerList(List<MeasureAnswer> measureAnswerList) {
        this.measureAnswerList = measureAnswerList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calculationTypeId != null ? calculationTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalculationType)) {
            return false;
        }
        CalculationType other = (CalculationType) object;
        if ((this.calculationTypeId == null && other.calculationTypeId != null) || (this.calculationTypeId != null && !this.calculationTypeId.equals(other.calculationTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.CalculationType[ calculationTypeId=" + calculationTypeId + " ]";
    }
    
}
