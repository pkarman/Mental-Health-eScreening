package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "note_title")
@NamedQueries({
        @NamedQuery(name = "NoteTitle.findAll", query = "SELECT n FROM NoteTitle n") })
public class NoteTitle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "note_title_id")
    private Integer noteTitleId;
    @Column(name = "vista_ien")
    private String vistaIen;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(mappedBy = "noteTitle")
    private List<VeteranAssessment> veteranAssessmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noteTitle")
    private List<NoteTitleMap> noteTitleMapList;

    public NoteTitle() {
    }

    public NoteTitle(Integer noteTitleId) {
        this.noteTitleId = noteTitleId;
    }

    public NoteTitle(Integer noteTitleId, Date dateCreated) {
        this.noteTitleId = noteTitleId;
        this.dateCreated = dateCreated;
    }

    public Integer getNoteTitleId() {
        return noteTitleId;
    }

    public void setNoteTitleId(Integer noteTitleId) {
        this.noteTitleId = noteTitleId;
    }

    public String getVistaIen() {
        return vistaIen;
    }

    public void setVistaIen(String vistaIen) {
        this.vistaIen = vistaIen;
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

    public List<VeteranAssessment> getVeteranAssessmentList() {
        return veteranAssessmentList;
    }

    public void setVeteranAssessmentList(List<VeteranAssessment> veteranAssessmentList) {
        this.veteranAssessmentList = veteranAssessmentList;
    }

    public List<NoteTitleMap> getNoteTitleMapList() {
        return noteTitleMapList;
    }

    public void setNoteTitleMapList(List<NoteTitleMap> noteTitleMapList) {
        this.noteTitleMapList = noteTitleMapList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noteTitleId != null ? noteTitleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoteTitle)) {
            return false;
        }
        NoteTitle other = (NoteTitle) object;
        if ((this.noteTitleId == null && other.noteTitleId != null)
                || (this.noteTitleId != null && !this.noteTitleId.equals(other.noteTitleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.NoteTitle[ noteTitleId=" + noteTitleId + " ]";
    }

}
