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

/**
 * 
 * @author jjinn
 */
@Entity
@Table(name = "consult")
@NamedQueries({
        @NamedQuery(name = "Consult.findAll", query = "SELECT c FROM Consult c") })
public class Consult implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "consult_id")
    private Integer consultId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "vista_ien")
    private String vistaIen;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consult")
    private List<VeteranAssessmentConsult> veteranAssessmentConsultList;

    public Consult() {
    }

    public Consult(Integer consultId) {
        this.consultId = consultId;
    }

    public Consult(Integer consultId, String name, String vistaIen, Date dateCreated) {
        this.consultId = consultId;
        this.name = name;
        this.vistaIen = vistaIen;
        this.dateCreated = dateCreated;
    }

    public Integer getConsultId() {
        return consultId;
    }

    public void setConsultId(Integer consultId) {
        this.consultId = consultId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVistaIen() {
        return vistaIen;
    }

    public void setVistaIen(String vistaIen) {
        this.vistaIen = vistaIen;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<VeteranAssessmentConsult> getVeteranAssessmentConsultList() {
        return veteranAssessmentConsultList;
    }

    public void setVeteranAssessmentConsultList(List<VeteranAssessmentConsult> veteranAssessmentConsultList) {
        this.veteranAssessmentConsultList = veteranAssessmentConsultList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultId != null ? consultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consult)) {
            return false;
        }
        Consult other = (Consult) object;
        if ((this.consultId == null && other.consultId != null)
                || (this.consultId != null && !this.consultId.equals(other.consultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.Consult[ consultId=" + consultId + " ]";
    }

}
