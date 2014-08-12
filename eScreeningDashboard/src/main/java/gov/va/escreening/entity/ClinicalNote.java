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
 * @author jjinn
 */
@Entity
@Table(name = "clinical_note")
@NamedQueries({
        @NamedQuery(name = "ClinicalNote.findAll", query = "SELECT c FROM ClinicalNote c") })
public class ClinicalNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clinical_note_id")
    private Integer clinicalNoteId;
    @Column(name = "vista_ien")
    private String vistaIen;
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public ClinicalNote() {
    }

    public ClinicalNote(Integer clinicalNoteId) {
        this.clinicalNoteId = clinicalNoteId;
    }

    public ClinicalNote(Integer clinicalNoteId, Date dateCreated) {
        this.clinicalNoteId = clinicalNoteId;
        this.dateCreated = dateCreated;
    }

    public Integer getClinicalNoteId() {
        return clinicalNoteId;
    }

    public void setClinicalNoteId(Integer clinicalNoteId) {
        this.clinicalNoteId = clinicalNoteId;
    }

    public String getVistaIen() {
        return vistaIen;
    }

    public void setVistaIen(String vistaIen) {
        this.vistaIen = vistaIen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        hash += (clinicalNoteId != null ? clinicalNoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClinicalNote)) {
            return false;
        }
        ClinicalNote other = (ClinicalNote) object;
        if ((this.clinicalNoteId == null && other.clinicalNoteId != null)
                || (this.clinicalNoteId != null && !this.clinicalNoteId.equals(other.clinicalNoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.ClinicalNote[ clinicalNoteId=" + clinicalNoteId + " ]";
    }

}
