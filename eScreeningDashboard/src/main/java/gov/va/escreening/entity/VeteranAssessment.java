package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Entity
@Table(name = "veteran_assessment")
@NamedQueries({ @NamedQuery(name = "VeteranAssessment.findAll", query = "SELECT v FROM VeteranAssessment v") })
public class VeteranAssessment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "access_mode")
	private Integer accessMode;

	@JoinColumn(name = "assessment_status_id", referencedColumnName = "assessment_status_id")
	@ManyToOne(optional = false)
	private AssessmentStatus assessmentStatus;

	@JoinColumn(name = "battery_id", referencedColumnName = "battery_id")
	@ManyToOne
	private Battery battery;

	@JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id")
	@ManyToOne
	private Clinic clinic;

	@JoinColumn(name = "clinician_id", referencedColumnName = "user_id")
	@ManyToOne
	private User clinician;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "veteran_assessment_consult", joinColumns = { @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id") }, inverseJoinColumns = { @JoinColumn(name = "consult_id", referencedColumnName = "consult_id", unique = true) })
	private Set<Consult> consults;

	@JoinColumn(name = "created_by_user_id", referencedColumnName = "user_id")
	@ManyToOne(optional = false)
	private User createdByUser;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "veteran_assessment_dashboard_alert", joinColumns = { @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id") }, inverseJoinColumns = { @JoinColumn(name = "dashboard_alert_id", referencedColumnName = "dashboard_alert_id", unique = true) })
	private Set<DashboardAlert> dashboardAlerts;

	@Column(name = "date_completed")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCompleted;

	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Basic(optional = false)
	@Column(name = "date_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	@Column(name = "duration")
	private Integer duration;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "veteran_assessment_health_factor", joinColumns = { @JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id") }, inverseJoinColumns = { @JoinColumn(name = "health_factor_id", referencedColumnName = "health_factor_id", unique = true) })
	private Set<HealthFactor> healthFactors;

	@JoinColumn(name = "note_title_id", referencedColumnName = "note_title_id")
	@ManyToOne
	private NoteTitle noteTitle;

	@Column(name = "percent_complete")
	private Integer percentComplete;

	@JoinColumn(name = "program_id", referencedColumnName = "program_id")
	@ManyToOne
	private Program program;

	@JoinColumn(name = "signed_by_user_id", referencedColumnName = "user_id")
	@ManyToOne
	private User signedByUser;

	@Transient
	private ImmutableMap<Integer, Survey> surveyMap = null;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "veteranAssessment")
	private List<SurveyMeasureResponse> surveyMeasureResponseList;

	@JoinColumn(name = "veteran_id", referencedColumnName = "veteran_id")
	@ManyToOne(optional = false)
	private Veteran veteran;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "veteran_assessment_id")
	private Integer veteranAssessmentId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "veteranAssessment", fetch = FetchType.LAZY)
	private List<VeteranAssessmentNote> veteranAssessmentNoteList;

	/**
	 * Since we have state that must be tracked in the VeteranAssessmentSurvey
	 * we cannot use a set of Survey here (which would have made more sense)
	 */
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "veteranAssessment")
	private List<VeteranAssessmentSurvey> veteranAssessmentSurveyList;

	public VeteranAssessment() {
	}

	public VeteranAssessment(Integer veteranAssessmentId) {
		this.veteranAssessmentId = veteranAssessmentId;
	}

	public VeteranAssessment(Integer veteranAssessmentId, Date dateCreated) {
		this.veteranAssessmentId = veteranAssessmentId;
		this.dateCreated = dateCreated;
	}

	/**
	 * @param survey
	 * @return true if the given survey is contained in this assessment. Will
	 *         return false if the passed in survey's getSurveyId() method
	 *         returns null;
	 */
	public boolean containsSurvey(@Nullable Survey survey) {
		return survey != null && survey.getSurveyId() != null && getSurveyMap().containsKey(survey.getSurveyId());
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof VeteranAssessment)) {
			return false;
		}
		VeteranAssessment other = (VeteranAssessment) object;
		if ((this.veteranAssessmentId == null && other.veteranAssessmentId != null) || (this.veteranAssessmentId != null && !this.veteranAssessmentId.equals(other.veteranAssessmentId))) {
			return false;
		}
		return true;
	}

	public Integer getAccessMode() {
		return accessMode;
	}

	public AssessmentStatus getAssessmentStatus() {
		return assessmentStatus;
	}

	public Battery getBattery() {
		return battery;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public User getClinician() {
		return clinician;
	}

	public Set<Consult> getConsults() {
		return consults;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public Set<DashboardAlert> getDashboardAlerts() {
		return dashboardAlerts;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public Integer getDuration() {
		return duration;
	}

	public Set<HealthFactor> getHealthFactors() {
		return healthFactors;
	}

	public NoteTitle getNoteTitle() {
		return noteTitle;
	}

	public Integer getPercentComplete() {
		return percentComplete;
	}

	public Program getProgram() {
		return program;
	}

	public User getSignedByUser() {
		return signedByUser;
	}

	/**
	 * @return a non-null, immutable map from survey IDs to the corresponding
	 *         Survey object.
	 */
	public ImmutableMap<Integer, Survey> getSurveyMap() {
		if (surveyMap == null) {
			ImmutableMap.Builder<Integer, Survey> mapBuilder = ImmutableMap.builder();
			if (veteranAssessmentSurveyList != null) {
				for (VeteranAssessmentSurvey vas : veteranAssessmentSurveyList) {
					mapBuilder.put(vas.getSurvey().getSurveyId(), vas.getSurvey());
				}
			}
			surveyMap = mapBuilder.build();
		}
		return surveyMap;
	}

	public List<SurveyMeasureResponse> getSurveyMeasureResponseList() {
		return surveyMeasureResponseList;
	}

	/**
	 * Used for iterating through the surveys of this assessment. If you need to
	 * know if a survey exists in this assessment, it is faster to call
	 * getSurveyMap() and use that.
	 * 
	 * @return
	 */
	public ImmutableCollection<Survey> getSurveys() {
		return getSurveyMap().values();
	}

	public Veteran getVeteran() {
		return veteran;
	}

	public Integer getVeteranAssessmentId() {
		return veteranAssessmentId;
	}

	public List<VeteranAssessmentNote> getVeteranAssessmentNoteList() {
		return veteranAssessmentNoteList;
	}

	/**
	 * Please note: If a getter is added for veteranAssessmentSurveyList please
	 * take into account the fact that surveyMap should be set to null after any
	 * change of that list. This means that to make this work we would have to
	 * return an ImmutableList from it.
	 */
	public ImmutableList<VeteranAssessmentSurvey> getVeteranAssessmentSurveyList() {
		return ImmutableList.copyOf(veteranAssessmentSurveyList);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (veteranAssessmentId != null ? veteranAssessmentId.hashCode() : 0);
		return hash;
	}

	public void setAccessMode(Integer accessMode) {
		this.accessMode = accessMode;
	}

	public void setAssessmentStatus(AssessmentStatus assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public void setClinician(User clinician) {
		this.clinician = clinician;
	}

	public void setConsults(Set<Consult> consults) {
		this.consults = consults;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public void setDashboardAlerts(Set<DashboardAlert> dashboardAlerts) {
		this.dashboardAlerts = dashboardAlerts;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setHealthFactors(Set<HealthFactor> healthFactors) {
		this.healthFactors = healthFactors;
	}

	public void setNoteTitle(NoteTitle noteTitle) {
		this.noteTitle = noteTitle;
	}

	public void setPercentComplete(Integer percentComplete) {
		this.percentComplete = percentComplete;
		if (this.percentComplete == 100) {
			setDateCompleted(new Date());
		}
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public void setSignedByUser(User signedByUser) {
		this.signedByUser = signedByUser;
	}

	public void setSurveyMeasureResponseList(
			List<SurveyMeasureResponse> surveyMeasureResponseList) {
		this.surveyMeasureResponseList = surveyMeasureResponseList;
	}

	/**
	 * This will update the contained List<VeteranAssessmentSurvey> to only
	 * contain the surveys that are given.<br/>
	 * If the current list contains a given survey that VeteranAssessmentSurvey
	 * instance will be used; otherwise a new one is created.<br/>
	 * This process is not free so make it count :) <br/>
	 * 
	 * @param surveys
	 */
	public void setSurveys(Set<Survey> surveys) {
		Map<Survey, VeteranAssessmentSurvey> surveyVasMap = Collections.emptyMap();

		if (!surveys.isEmpty() && veteranAssessmentSurveyList != null) {
			ImmutableMap.Builder<Survey, VeteranAssessmentSurvey> mapBuilder = ImmutableMap.builder();
			for (VeteranAssessmentSurvey vas : veteranAssessmentSurveyList) {
				mapBuilder.put(vas.getSurvey(), vas);
			}
			surveyVasMap = mapBuilder.build();
		}

		List<VeteranAssessmentSurvey> newVasList = new ArrayList<VeteranAssessmentSurvey>(surveys.size());
		for (Survey survey : surveys) {
			VeteranAssessmentSurvey newVas = surveyVasMap.get(survey);
			if (newVas == null) {
				newVas = new VeteranAssessmentSurvey(this, survey);
			}
			newVasList.add(newVas);
		}

		if (veteranAssessmentSurveyList == null) {
			veteranAssessmentSurveyList = new ArrayList<VeteranAssessmentSurvey>();
		}

		veteranAssessmentSurveyList.clear();
		veteranAssessmentSurveyList.addAll(newVasList);
		surveyMap = null;
	}

	public void setVeteran(Veteran veteran) {
		this.veteran = veteran;
	}

	public void setVeteranAssessmentId(Integer veteranAssessmentId) {
		this.veteranAssessmentId = veteranAssessmentId;
	}

	public void setVeteranAssessmentNoteList(
			List<VeteranAssessmentNote> veteranAssessmentNoteList) {
		this.veteranAssessmentNoteList = veteranAssessmentNoteList;
	}

	/**
	 * Note: Normally you want to interact with the Surveys not with the
	 * VeteranAssessmentSurveyList. If you don't need to access fields in the
	 * join table itself your code will end up being much simpiler if you just
	 * update the surveys using get/setSurvey methods.
	 */
	public void setVeteranAssessmentSurveyList(
			List<VeteranAssessmentSurvey> veteranAssessmentSurveyList) {
		this.veteranAssessmentSurveyList = veteranAssessmentSurveyList;
		surveyMap = null;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.VeteranAssessment[ veteranAssessmentId=" + veteranAssessmentId + " ]";
	}
}
