package gov.va.escreening.dto.dashboard;

import java.io.Serializable;
import java.util.Date;

public class DataExportFilterOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer exportLogId;
	private Integer exportedByUserId;
	private Integer clinicianUserId;
	private Integer createdByUserId;
	private Integer exportTypeId;
	private Date assessmentStart;
	private Date assessmentEnd;
	private Integer programId;
	private Integer veteranId;
	private String comment;
	private String filePath;

	public Integer getExportLogId() {
		return exportLogId;
	}

	public void setExportLogId(Integer exportLogId) {
		this.exportLogId = exportLogId;
	}

	public Integer getExportedByUserId() {
		return exportedByUserId;
	}

	public void setExportedByUserId(Integer exportedByUserId) {
		this.exportedByUserId = exportedByUserId;
	}

	public Integer getClinicianUserId() {
		return clinicianUserId;
	}

	public void setClinicianUserId(Integer clinicianUserId) {
		this.clinicianUserId = clinicianUserId;
	}

	public Integer getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Integer getExportTypeId() {
		return exportTypeId;
	}

	public void setExportTypeId(Integer exportTypeId) {
		this.exportTypeId = exportTypeId;
	}

	public Date getAssessmentStart() {
		return assessmentStart;
	}

	public void setAssessmentStart(Date assessmentStart) {
		this.assessmentStart = assessmentStart;
	}

	public Date getAssessmentEnd() {
		return assessmentEnd;
	}

	public void setAssessmentEnd(Date assessmentEnd) {
		this.assessmentEnd = assessmentEnd;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public Integer getVeteranId() {
		return veteranId;
	}

	public void setVeteranId(Integer veteranId) {
		this.veteranId = veteranId;
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

	@Override
	public String toString() {
		return "DataExportFilterOptions [exportLogId=" + exportLogId + ", exportedByUserId=" + exportedByUserId + ", clinicianUserId=" + clinicianUserId + ", createdByUserId=" + createdByUserId + ", exportTypeId=" + exportTypeId + ", assessmentStart=" + assessmentStart + ", assessmentEnd=" + assessmentEnd + ", programId=" + programId + ", veteranId=" + veteranId + ", comment=" + comment + ", filePath=" + filePath + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessmentEnd == null) ? 0 : assessmentEnd.hashCode());
		result = prime * result + ((assessmentStart == null) ? 0 : assessmentStart.hashCode());
		result = prime * result + ((clinicianUserId == null) ? 0 : clinicianUserId.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((createdByUserId == null) ? 0 : createdByUserId.hashCode());
		result = prime * result + ((exportLogId == null) ? 0 : exportLogId.hashCode());
		result = prime * result + ((exportTypeId == null) ? 0 : exportTypeId.hashCode());
		result = prime * result + ((exportedByUserId == null) ? 0 : exportedByUserId.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((programId == null) ? 0 : programId.hashCode());
		result = prime * result + ((veteranId == null) ? 0 : veteranId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DataExportFilterOptions)) {
			return false;
		}
		DataExportFilterOptions other = (DataExportFilterOptions) obj;
		if (assessmentEnd == null) {
			if (other.assessmentEnd != null) {
				return false;
			}
		} else if (!assessmentEnd.equals(other.assessmentEnd)) {
			return false;
		}
		if (assessmentStart == null) {
			if (other.assessmentStart != null) {
				return false;
			}
		} else if (!assessmentStart.equals(other.assessmentStart)) {
			return false;
		}
		if (clinicianUserId == null) {
			if (other.clinicianUserId != null) {
				return false;
			}
		} else if (!clinicianUserId.equals(other.clinicianUserId)) {
			return false;
		}
		if (comment == null) {
			if (other.comment != null) {
				return false;
			}
		} else if (!comment.equals(other.comment)) {
			return false;
		}
		if (createdByUserId == null) {
			if (other.createdByUserId != null) {
				return false;
			}
		} else if (!createdByUserId.equals(other.createdByUserId)) {
			return false;
		}
		if (exportLogId == null) {
			if (other.exportLogId != null) {
				return false;
			}
		} else if (!exportLogId.equals(other.exportLogId)) {
			return false;
		}
		if (exportTypeId == null) {
			if (other.exportTypeId != null) {
				return false;
			}
		} else if (!exportTypeId.equals(other.exportTypeId)) {
			return false;
		}
		if (exportedByUserId == null) {
			if (other.exportedByUserId != null) {
				return false;
			}
		} else if (!exportedByUserId.equals(other.exportedByUserId)) {
			return false;
		}
		if (filePath == null) {
			if (other.filePath != null) {
				return false;
			}
		} else if (!filePath.equals(other.filePath)) {
			return false;
		}
		if (programId == null) {
			if (other.programId != null) {
				return false;
			}
		} else if (!programId.equals(other.programId)) {
			return false;
		}
		if (veteranId == null) {
			if (other.veteranId != null) {
				return false;
			}
		} else if (!veteranId.equals(other.veteranId)) {
			return false;
		}
		return true;
	}

}