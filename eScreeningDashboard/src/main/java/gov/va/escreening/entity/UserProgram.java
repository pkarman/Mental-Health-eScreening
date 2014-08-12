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

@Entity
@Table(name = "user_program")
@NamedQueries({
        @NamedQuery(name = "UserProgram.findAll", query = "SELECT u FROM UserProgram u") })
public class UserProgram implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_program_id")
    private Integer userProgramId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne(optional = false)
    private Program program;

    public UserProgram() {
    }

    public UserProgram(Integer userProgramId) {
        this.userProgramId = userProgramId;
    }

    public UserProgram(Integer userProgramId, Date dateCreated) {
        this.userProgramId = userProgramId;
        this.dateCreated = dateCreated;
    }

    public Integer getUserProgramId() {
        return userProgramId;
    }

    public void setUserProgramId(Integer userProgramId) {
        this.userProgramId = userProgramId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userProgramId != null ? userProgramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserProgram)) {
            return false;
        }
        UserProgram other = (UserProgram) object;
        if ((this.userProgramId == null && other.userProgramId != null)
                || (this.userProgramId != null && !this.userProgramId.equals(other.userProgramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.UserProgram[ userProgramId=" + userProgramId + " ]";
    }

}
