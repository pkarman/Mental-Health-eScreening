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
@Table(name = "dialog_prompt")
@NamedQueries({
        @NamedQuery(name = "DialogPrompt.findAll", query = "SELECT d FROM DialogPrompt d") })
public class DialogPrompt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dialog_prompt_id")
    private Integer dialogPromptId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dialogPrompt")
    private List<HealthFactorDialogPrompt> healthFactorDialogPromptList;

    public DialogPrompt() {
    }

    public DialogPrompt(Integer dialogPromptId) {
        this.dialogPromptId = dialogPromptId;
    }

    public DialogPrompt(Integer dialogPromptId, String name, Date dateCreated) {
        this.dialogPromptId = dialogPromptId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getDialogPromptId() {
        return dialogPromptId;
    }

    public void setDialogPromptId(Integer dialogPromptId) {
        this.dialogPromptId = dialogPromptId;
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

    public List<HealthFactorDialogPrompt> getHealthFactorDialogPromptList() {
        return healthFactorDialogPromptList;
    }

    public void setHealthFactorDialogPromptList(List<HealthFactorDialogPrompt> healthFactorDialogPromptList) {
        this.healthFactorDialogPromptList = healthFactorDialogPromptList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dialogPromptId != null ? dialogPromptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DialogPrompt)) {
            return false;
        }
        DialogPrompt other = (DialogPrompt) object;
        if ((this.dialogPromptId == null && other.dialogPromptId != null)
                || (this.dialogPromptId != null && !this.dialogPromptId.equals(other.dialogPromptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.va.escreeningdashboard.entity.DialogPrompt[ dialogPromptId=" + dialogPromptId + " ]";
    }

}
