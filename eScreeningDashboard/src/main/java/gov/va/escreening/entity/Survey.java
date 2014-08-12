package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "survey")
@NamedQueries({ @NamedQuery(name = "Survey.findAll", query = "SELECT s FROM Survey s") })
public class Survey implements Serializable, SurveyBaseProperties{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "survey_id")
    private Integer surveyId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "version")
    private Integer version;
    @Column(name = "display_order")
    private Integer displayOrder;
    @Basic(optional = false)
    @Column(name = "has_mha")
    private Boolean hasMha;
    @Column(name = "mha_test_name")
    private String mhaTestName;
    @Column(name = "mha_result_group_ien")
    private String mhaResultGroupIen;
    
    @Column(name = "vista_title")
    private String vistaTitle;
    
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survey")
    private List<VeteranAssessmentSurvey> veteranAssessmentSurveyList;
    
    @JoinColumn(name = "survey_section_id", referencedColumnName = "survey_section_id")
    @ManyToOne(optional = false)
    private SurveySection surveySection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survey")
    @OrderBy("pageNumber")
    private List<SurveyPage> surveyPageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survey")
    private List<SurveyMeasureResponse> surveyMeasureResponseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survey")
    private List<ClinicalReminderSurvey> clinicalReminderSurveyList;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="survey_template",
        joinColumns={ @JoinColumn(name="survey_id", referencedColumnName="survey_id") },
        inverseJoinColumns={ @JoinColumn(name="template_id", referencedColumnName="template_id", unique=true) }
    )
    private Set<Template> templates;

    public Survey() {
    }

    public Survey(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Survey(Integer surveyId, String name, Date dateCreated) {
        this.surveyId = surveyId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean isMha() {
        return hasMha;
    }

    public void setMha(Boolean hasMha) {
        this.hasMha = hasMha;
    }

    public String getMhaTestName() {
        return mhaTestName;
    }

    public void setMhaTestName(String mhaTestName) {
        this.mhaTestName = mhaTestName;
    }

    public String getMhaResultGroupIen() {
        return mhaResultGroupIen;
    }

    public void setMhaResultGroupIen(String mhaResultGroupIen) {
        this.mhaResultGroupIen = mhaResultGroupIen;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<VeteranAssessmentSurvey> getVeteranAssessmentSurveyList() {
        return veteranAssessmentSurveyList;
    }

    public void setVeteranAssessmentSurveyList(
            List<VeteranAssessmentSurvey> veteranAssessmentSurveyList) {
        this.veteranAssessmentSurveyList = veteranAssessmentSurveyList;
    }

    public SurveySection getSurveySection() {
        return surveySection;
    }

    public void setSurveySection(SurveySection surveySection) {
        this.surveySection = surveySection;
    }

    public List<SurveyPage> getSurveyPageList() {
        return surveyPageList;
    }

    public void setSurveyPageList(List<SurveyPage> surveyPageList) {
        this.surveyPageList = surveyPageList;
    }

    public List<SurveyMeasureResponse> getSurveyMeasureResponseList() {
        return surveyMeasureResponseList;
    }

    public void setSurveyMeasureResponseList(
            List<SurveyMeasureResponse> surveyMeasureResponseList) {
        this.surveyMeasureResponseList = surveyMeasureResponseList;
    }

    public List<ClinicalReminderSurvey> getClinicalReminderSurveyList() {
        return clinicalReminderSurveyList;
    }

    public void setClinicalReminderSurveyList(List<ClinicalReminderSurvey> clinicalReminderSurveyList) {
        this.clinicalReminderSurveyList = clinicalReminderSurveyList;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }
    
    public Boolean isClinicalReminder()
    {
        return clinicalReminderSurveyList!=null 
                && (!clinicalReminderSurveyList.isEmpty());
    }

    public String getVistaTitle() {
        return vistaTitle;
    }

    public void setVistaTitle(String vistaTitle) {
        this.vistaTitle = vistaTitle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyId != null ? surveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Survey)) {
            return false;
        }
        Survey other = (Survey) object;
        if ((this.surveyId == null && other.surveyId != null)
                || (this.surveyId != null && !this.surveyId
                        .equals(other.surveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.Survey[ surveyId=" + surveyId
                + " ]";
    }

}
