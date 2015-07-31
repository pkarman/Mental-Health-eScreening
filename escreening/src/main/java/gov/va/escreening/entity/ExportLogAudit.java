package gov.va.escreening.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "export_log_audit")
@NamedQueries({@NamedQuery(name = "ExportLogAudit.findAll", query = "SELECT e FROM ExportLogAudit e")})
public class ExportLogAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "export_log_audit_id")
    private Integer exportLogAuditId;

    @Column(name = "comment")
    private String comment;

    @Basic(optional = false)
    @Column(name = "date_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @JoinColumn(name = "exported_by_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User exportedByUser;

    @JoinColumn(name = "export_log_id", referencedColumnName = "export_log_id")
    @ManyToOne
    private ExportLog exportLog;

    public User getExportedByUser() {
        return exportedByUser;
    }

    public void setExportedByUser(User exportedByUser) {
        this.exportedByUser = exportedByUser;
    }

    public Integer getExportLogAuditId() {
        return exportLogAuditId;
    }

    public void setExportLogAuditId(Integer exportLogAuditId) {
        this.exportLogAuditId = exportLogAuditId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public ExportLog getExportLog() {
        return exportLog;
    }

    public void setExportLog(ExportLog exportLog) {
        this.exportLog = exportLog;
    }

    public ExportLog getAuditExportLog() {
        exportLog.setComment(comment);
        exportLog.setExportedByUser(exportedByUser);
        return exportLog;
    }
}
