package gov.va.escreening.entity;

import java.io.Serializable;

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

/**
 * 
 * @author jjinn
 */
@Entity
@Table(name = "veteran_assessment_note")
@NamedQueries({
        @NamedQuery(name = "VeteranAssessmentNote.findAll", query = "SELECT v FROM VeteranAssessmentNote v") })
public class VeteranAssessmentNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "veteran_assessment_note_id")
    private Integer veteranAssessmentNoteId;
    @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
    @ManyToOne(optional = false)
    private VeteranAssessment veteranAssessment;
    @JoinColumn(name = "clinical_note_id", referencedColumnName = "clinical_note_id")
    @ManyToOne(optional = false)
    private ClinicalNote clinicalNote;

    public VeteranAssessmentNote() {
    }

    public VeteranAssessmentNote(Integer veteranAssessmentNoteId) {
        this.veteranAssessmentNoteId = veteranAssessmentNoteId;
    }

    public Integer getVeteranAssessmentNoteId() {
        return veteranAssessmentNoteId;
    }

    public void setVeteranAssessmentNoteId(Integer veteranAssessmentNoteId) {
        this.veteranAssessmentNoteId = veteranAssessmentNoteId;
    }

    public VeteranAssessment getVeteranAssessment() {
        return veteranAssessment;
    }

    public void setVeteranAssessment(VeteranAssessment veteranAssessment) {
        this.veteranAssessment = veteranAssessment;
    }

    public ClinicalNote getClinicalNote() {
        return clinicalNote;
    }

    public void setClinicalNote(ClinicalNote clinicalNote) {
        this.clinicalNote = clinicalNote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veteranAssessmentNoteId != null ? veteranAssessmentNoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeteranAssessmentNote)) {
            return false;
        }
        VeteranAssessmentNote other = (VeteranAssessmentNote) object;
        if ((this.veteranAssessmentNoteId == null && other.veteranAssessmentNoteId != null)
                || (this.veteranAssessmentNoteId != null && !this.veteranAssessmentNoteId
                        .equals(other.veteranAssessmentNoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.VeteranAssessmentNote[ veteranAssessmentNoteId="
                + veteranAssessmentNoteId + " ]";
    }

}
