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
@Table(name = "health_factor_dialog_prompt")
@NamedQueries({
        @NamedQuery(name = "HealthFactorDialogPrompt.findAll", query = "SELECT h FROM HealthFactorDialogPrompt h") })
public class HealthFactorDialogPrompt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "health_factor_dialog_prompt_id")
    private Integer healthFactorDialogPromptId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "health_factor_id", referencedColumnName = "health_factor_id")
    @ManyToOne(optional = false)
    private HealthFactor healthFactor;
    @JoinColumn(name = "dialog_prompt_id", referencedColumnName = "dialog_prompt_id")
    @ManyToOne(optional = false)
    private DialogPrompt dialogPrompt;

    public HealthFactorDialogPrompt() {
    }

    public HealthFactorDialogPrompt(Integer healthFactorDialogPromptId) {
        this.healthFactorDialogPromptId = healthFactorDialogPromptId;
    }

    public HealthFactorDialogPrompt(Integer healthFactorDialogPromptId, Date dateCreated) {
        this.healthFactorDialogPromptId = healthFactorDialogPromptId;
        this.dateCreated = dateCreated;
    }

    public Integer getHealthFactorDialogPromptId() {
        return healthFactorDialogPromptId;
    }

    public void setHealthFactorDialogPromptId(Integer healthFactorDialogPromptId) {
        this.healthFactorDialogPromptId = healthFactorDialogPromptId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public HealthFactor getHealthFactor() {
        return healthFactor;
    }

    public void setHealthFactor(HealthFactor healthFactor) {
        this.healthFactor = healthFactor;
    }

    public DialogPrompt getDialogPrompt() {
        return dialogPrompt;
    }

    public void setDialogPrompt(DialogPrompt dialogPrompt) {
        this.dialogPrompt = dialogPrompt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (healthFactorDialogPromptId != null ? healthFactorDialogPromptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HealthFactorDialogPrompt)) {
            return false;
        }
        HealthFactorDialogPrompt other = (HealthFactorDialogPrompt) object;
        if ((this.healthFactorDialogPromptId == null && other.healthFactorDialogPromptId != null)
                || (this.healthFactorDialogPromptId != null && !this.healthFactorDialogPromptId
                        .equals(other.healthFactorDialogPromptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.va.escreeningdashboard.entity.HealthFactorDialogPrompt[ healthFactorDialogPromptId="
                + healthFactorDialogPromptId + " ]";
    }

}
