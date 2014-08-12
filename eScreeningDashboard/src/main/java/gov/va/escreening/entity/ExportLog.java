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
@Table(name = "export_log")
@NamedQueries({
        @NamedQuery(name = "ExportLog.findAll", query = "SELECT e FROM ExportLog e") })
public class ExportLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "export_log_id")
    private Integer exportLogId;
    @Column(name = "assessment_start_filter")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assessmentStartFilter;
    @Column(name = "assessment_end_filter")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assessmentEndFilter;
    @Column(name = "comment")
    private String comment;
    @Basic(optional = false)
    @Column(name = "file_path")
    private String filePath;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "user_id")
    // @ManyToOne(optional = false)
    @ManyToOne
    private User createdByUser;
    @JoinColumn(name = "clinician_user_id", referencedColumnName = "user_id")
    // @ManyToOne(optional = false)
    @ManyToOne
    private User clinician;
    @JoinColumn(name = "exported_by_user_id", referencedColumnName = "user_id")
    // @ManyToOne(optional = false)
    @ManyToOne
    private User exportedByUser;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne
    private Program program;
    @JoinColumn(name = "export_type_id", referencedColumnName = "export_type_id")
    @ManyToOne(optional = false)
    private ExportType exportType;
    @JoinColumn(name = "veteran_id", referencedColumnName = "veteran_id")
    @ManyToOne
    private Veteran veteran;

    public ExportLog() {
    }

    public ExportLog(Integer exportLogId) {
        this.exportLogId = exportLogId;
    }

    public ExportLog(Integer exportLogId, String filePath, Date dateCreated) {
        this.exportLogId = exportLogId;
        this.filePath = filePath;
        this.dateCreated = dateCreated;
    }

    public Integer getExportLogId() {
        return exportLogId;
    }

    public void setExportLogId(Integer exportLogId) {
        this.exportLogId = exportLogId;
    }

    public Date getAssessmentStartFilter() {
        return assessmentStartFilter;
    }

    public void setAssessmentStartFilter(Date assessmentStartFilter) {
        this.assessmentStartFilter = assessmentStartFilter;
    }

    public Date getAssessmentEndFilter() {
        return assessmentEndFilter;
    }

    public void setAssessmentEndFilter(Date assessmentEndFilter) {
        this.assessmentEndFilter = assessmentEndFilter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public User getClinician() {
        return clinician;
    }

    public void setClinician(User clinician) {
        this.clinician = clinician;
    }

    public User getExportedByUser() {
        return exportedByUser;
    }

    public void setExportedByUser(User exportedByUser) {
        this.exportedByUser = exportedByUser;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ExportType getExportType() {
        return exportType;
    }

    public void setExportType(ExportType exportType) {
        this.exportType = exportType;
    }

    public Veteran getVeteran() {
        return veteran;
    }

    public void setVeteran(Veteran veteran) {
        this.veteran = veteran;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exportLogId != null ? exportLogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExportLog)) {
            return false;
        }
        ExportLog other = (ExportLog) object;
        if ((this.exportLogId == null && other.exportLogId != null)
                || (this.exportLogId != null && !this.exportLogId.equals(other.exportLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.va.escreeningdashboard.entity.ExportLog[ exportLogId=" + exportLogId + " ]";
    }

}
