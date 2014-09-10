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
@Table(name = "export_log_data")
@NamedQueries({ @NamedQuery(name = "ExportLogData.findAll", query = "SELECT e FROM ExportLogData e") })
public class ExportLogData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "export_log_data_id")
	private Integer exportLogDataId;

	@JoinColumn(name = "export_log_id", referencedColumnName = "export_log_id")
	@ManyToOne
	private ExportLog exportLog;

	@Basic(optional = false)
	@Column(name = "export_log_data")
	private String exportLogData;

	@Basic(optional = false)
	@Column(name = "export_log_data_index")
	private Integer dataIndex;

	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public Integer getExportLogDataId() {
		return exportLogDataId;
	}

	public void setExportLogDataId(Integer exportLogDataId) {
		this.exportLogDataId = exportLogDataId;
	}

	public ExportLog getExportLog() {
		return exportLog;
	}

	public void setExportLog(ExportLog exportLog) {
		this.exportLog = exportLog;
	}

	public String getExportLogData() {
		return exportLogData;
	}

	public void setExportLogData(String exportLogData) {
		this.exportLogData = exportLogData;
	}

	public Integer getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(Integer dataIndex) {
		this.dataIndex = dataIndex;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ExportLogData(String exportLogData) {
		setExportLogData(exportLogData);
	}

	public ExportLogData() {
	}

}
