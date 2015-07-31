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

@Entity
@Table(name = "note_title_map")
@NamedQueries({
        @NamedQuery(name = "NoteTitleMap.findAll", query = "SELECT n FROM NoteTitleMap n") })
public class NoteTitleMap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "note_title_map_id")
    private Integer noteTitleMapId;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne
    private Program program;
    @JoinColumn(name = "note_title_id", referencedColumnName = "note_title_id")
    @ManyToOne(optional = false)
    private NoteTitle noteTitle;

    public NoteTitleMap() {
    }

    public NoteTitleMap(Integer noteTitleMapId) {
        this.noteTitleMapId = noteTitleMapId;
    }

    public Integer getNoteTitleMapId() {
        return noteTitleMapId;
    }

    public void setNoteTitleMapId(Integer noteTitleMapId) {
        this.noteTitleMapId = noteTitleMapId;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public NoteTitle getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(NoteTitle noteTitle) {
        this.noteTitle = noteTitle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noteTitleMapId != null ? noteTitleMapId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoteTitleMap)) {
            return false;
        }
        NoteTitleMap other = (NoteTitleMap) object;
        if ((this.noteTitleMapId == null && other.noteTitleMapId != null)
                || (this.noteTitleMapId != null && !this.noteTitleMapId.equals(other.noteTitleMapId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.NoteTitleMap[ noteTitleMapId=" + noteTitleMapId + " ]";
    }

}
