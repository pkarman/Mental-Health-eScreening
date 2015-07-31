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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "assessment_variable_column")
@NamedQueries({
    @NamedQuery(name = "AssessmentVariableColumn.findAll", query = "SELECT a FROM AssessmentVariableColumn a")})
public class AssessmentVariableColumn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assessment_variable_column_id")
    private Integer assessmentVariableColumnId;
    @Basic(optional = false)
    @Column(name = "column_num")
    private int columnNum;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "assessment_variable_id", referencedColumnName = "assessment_variable_id")
    @ManyToOne(optional = false)
    private AssessmentVariable assessmentVariableId;

    public AssessmentVariableColumn() {
    }

    public AssessmentVariableColumn(Integer assessmentVariableColumnId) {
        this.assessmentVariableColumnId = assessmentVariableColumnId;
    }

    public AssessmentVariableColumn(Integer assessmentVariableColumnId, int columnNum, Date dateCreated) {
        this.assessmentVariableColumnId = assessmentVariableColumnId;
        this.columnNum = columnNum;
        this.dateCreated = dateCreated;
    }

    public Integer getAssessmentVariableColumnId() {
        return assessmentVariableColumnId;
    }

    public void setAssessmentVariableColumnId(Integer assessmentVariableColumnId) {
        this.assessmentVariableColumnId = assessmentVariableColumnId;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AssessmentVariable getAssessmentVariableId() {
        return assessmentVariableId;
    }

    public void setAssessmentVariableId(AssessmentVariable assessmentVariableId) {
        this.assessmentVariableId = assessmentVariableId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentVariableColumnId != null ? assessmentVariableColumnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentVariableColumn)) {
            return false;
        }
        AssessmentVariableColumn other = (AssessmentVariableColumn) object;
        if ((this.assessmentVariableColumnId == null && other.assessmentVariableColumnId != null) || (this.assessmentVariableColumnId != null && !this.assessmentVariableColumnId.equals(other.assessmentVariableColumnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.AssessmentVariableColumn[ assessmentVariableColumnId=" + assessmentVariableColumnId + " ]";
    }
    
}
