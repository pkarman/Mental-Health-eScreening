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
@Table(name = "clinical_reminder")
@NamedQueries({
        @NamedQuery(name = "ClinicalReminder.findAll", query = "SELECT c FROM ClinicalReminder c") })
public class ClinicalReminder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clinical_reminder_id")
    private Integer clinicalReminderId;
    @Column(name = "vista_ien")
    private String vistaIen;
    @Column(name = "name")
    private String name;
    @Column(name = "print_name")
    private String printName;
    @Column(name = "clinical_reminder_class_code")
    private String clinicalReminderClassCode;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinicalReminder")
    private List<ClinicalReminderSurvey> clinicalReminderSurveyList;

    public ClinicalReminder() {
    }

    public ClinicalReminder(Integer clinicalReminderId) {
        this.clinicalReminderId = clinicalReminderId;
    }

    public ClinicalReminder(Integer clinicalReminderId, Date dateCreated) {
        this.clinicalReminderId = clinicalReminderId;
        this.dateCreated = dateCreated;
    }

    public Integer getClinicalReminderId() {
        return clinicalReminderId;
    }

    public void setClinicalReminderId(Integer clinicalReminderId) {
        this.clinicalReminderId = clinicalReminderId;
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

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getClinicalReminderClassCode() {
        return clinicalReminderClassCode;
    }

    public void setClinicalReminderClassCode(String clinicalReminderClassCode) {
        this.clinicalReminderClassCode = clinicalReminderClassCode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<ClinicalReminderSurvey> getClinicalReminderSurveyList() {
        return clinicalReminderSurveyList;
    }

    public void setClinicalReminderSurveyList(List<ClinicalReminderSurvey> clinicalReminderSurveyList) {
        this.clinicalReminderSurveyList = clinicalReminderSurveyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clinicalReminderId != null ? clinicalReminderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClinicalReminder)) {
            return false;
        }
        ClinicalReminder other = (ClinicalReminder) object;
        if ((this.clinicalReminderId == null && other.clinicalReminderId != null)
                || (this.clinicalReminderId != null && !this.clinicalReminderId.equals(other.clinicalReminderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.ClinicalReminder[ clinicalReminderId=" + clinicalReminderId + " ]";
    }

}
