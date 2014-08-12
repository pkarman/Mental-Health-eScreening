package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "veteran_assessment_survey")
@NamedQueries({ @NamedQuery(name = "VeteranAssessmentSurvey.findAll", query = "SELECT v FROM VeteranAssessmentSurvey v") })
public class VeteranAssessmentSurvey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "veteran_assessment_survey_id")
	private Integer veteranAssessmentSurveyId;

	@Column(name = "total_question_count")
	private Integer totalQuestionCount;

	@Column(name = "total_response_count")
	private Integer totalResponseCount;

	@Column(name = "mha_result")
	private String mhaResult;

	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
	@ManyToOne(optional = false)
	private VeteranAssessment veteranAssessment;

	@JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
	@ManyToOne(optional = false)
	private Survey survey;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "veteranAssessmentSurvey", fetch = FetchType.LAZY)
	private List<SurveyAttempt> surveyAttemptList;

	public VeteranAssessmentSurvey() {
	}

	public VeteranAssessmentSurvey(Integer veteranAssessmentSurveyId) {
		this.veteranAssessmentSurveyId = veteranAssessmentSurveyId;
	}

	public VeteranAssessmentSurvey(Integer veteranAssessmentSurveyId, Date dateCreated) {
		this.veteranAssessmentSurveyId = veteranAssessmentSurveyId;
		this.dateCreated = dateCreated;
	}

	public VeteranAssessmentSurvey(VeteranAssessment assessment, Survey survey) {
		this.veteranAssessment = assessment;
		this.survey = survey;
	}

	public Integer getVeteranAssessmentSurveyId() {
		return veteranAssessmentSurveyId;
	}

	public void setVeteranAssessmentSurveyId(Integer veteranAssessmentSurveyId) {
		this.veteranAssessmentSurveyId = veteranAssessmentSurveyId;
	}

	public Integer getTotalQuestionCount() {
		return totalQuestionCount;
	}

	public void setTotalQuestionCount(Integer totalQuestionCount) {
		this.totalQuestionCount = totalQuestionCount;
	}

	public Integer getTotalResponseCount() {
		return totalResponseCount;
	}

	public void setTotalResponseCount(Integer totalResponseCount) {
		this.totalResponseCount = totalResponseCount;
	}

	public String getMhaResult() {
		return mhaResult;
	}

	public void setMhaResult(String mhaResult) {
		this.mhaResult = mhaResult;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public VeteranAssessment getVeteranAssessment() {
		return veteranAssessment;
	}

	public void setVeteranAssessment(VeteranAssessment veteranAssessment) {
		this.veteranAssessment = veteranAssessment;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public List<SurveyAttempt> getSurveyAttemptList() {
		return surveyAttemptList;
	}

	public void setSurveyAttemptList(List<SurveyAttempt> surveyAttemptList) {
		this.surveyAttemptList = surveyAttemptList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (veteranAssessmentSurveyId != null ? veteranAssessmentSurveyId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof VeteranAssessmentSurvey)) {
			return false;
		}
		VeteranAssessmentSurvey other = (VeteranAssessmentSurvey) object;
		if ((this.veteranAssessmentSurveyId == null && other.veteranAssessmentSurveyId != null) || (this.veteranAssessmentSurveyId != null && !this.veteranAssessmentSurveyId.equals(other.veteranAssessmentSurveyId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.VeteranAssessmentSurvey[ veteranAssessmentSurveyId=" + veteranAssessmentSurveyId + " ]";
	}

}
