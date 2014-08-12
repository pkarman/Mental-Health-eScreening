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
 * @author Robin Carnow
 */
@Entity
@Table(name = "veteran_assessment_measure_visibility")
@NamedQueries({ @NamedQuery(name = "VeteranAssessmentMeasureVisibility.findAll", query = "SELECT v FROM VeteranAssessmentMeasureVisibility v") })
public class VeteranAssessmentMeasureVisibility implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "veteran_assessment_measure_visibility_id")
    private Integer veteranAssessmentMeasureVisibilityId;
    @JoinColumn(name = "measure_id", referencedColumnName = "measure_id")
    @ManyToOne(optional = false)
    private Measure measure;
    @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
    @ManyToOne(optional = false)
    private VeteranAssessment veteranAssessment;
    @Basic(optional = false)
    @Column(name = "is_visible")
    private Boolean isVisible;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    public VeteranAssessmentMeasureVisibility(){}
            
    public VeteranAssessmentMeasureVisibility(VeteranAssessment assessment, Measure measure){
        this.veteranAssessment = assessment;
        this.measure = measure;
        this.isVisible = false;
    }
    
    public Integer getVeteranAssessmentMeasureVisibilityId() {
        return veteranAssessmentMeasureVisibilityId;
    }
    public void setVeteranAssessmentMeasureVisibilityId(Integer veteranAssessmentMeasureVisibilityId) {
        this.veteranAssessmentMeasureVisibilityId = veteranAssessmentMeasureVisibilityId;
    }
    public Measure getMeasure() {
        return measure;
    }
    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
    public VeteranAssessment getVeteranAssessment() {
        return veteranAssessment;
    }
    public void setVeteranAssessment(VeteranAssessment veteranAssessment) {
        this.veteranAssessment = veteranAssessment;
    }
    public Boolean getIsVisible() {
        return isVisible;
    }
    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }    
}
