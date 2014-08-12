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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "survey_page")
@NamedQueries({ @NamedQuery(name = "SurveyPage.findAll", query = "SELECT s FROM SurveyPage s") })
public class SurveyPage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "survey_page_id")
    private Integer surveyPageId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "page_number")
    private int pageNumber;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey survey;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderColumn(name = "display_order")
    @JoinTable(
            name = "survey_page_measure",
            joinColumns = @JoinColumn(name = "survey_page_id", referencedColumnName = "survey_page_id"),
            inverseJoinColumns = @JoinColumn(name = "measure_id", referencedColumnName = "measure_id", unique = true))
    private List<Measure> measures;

    public SurveyPage() {
    }

    public SurveyPage(Integer surveyPageId) {
        this.surveyPageId = surveyPageId;
    }

    public SurveyPage(Integer surveyPageId, int pageNumber, Date dateCreated) {
        this.surveyPageId = surveyPageId;
        this.pageNumber = pageNumber;
        this.dateCreated = dateCreated;
    }

    public Integer getSurveyPageId() {
        return surveyPageId;
    }

    public void setSurveyPageId(Integer surveyPageId) {
        this.surveyPageId = surveyPageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * Note: if the returned list contains nulls it is because of an error in the sql script which inserts entries into
     * survey_page_measure where 0 was not used for the first display_order index and/or there is a gap in the
     * display_order
     * @return
     */
    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyPageId != null ? surveyPageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof SurveyPage)) {
            return false;
        }
        SurveyPage other = (SurveyPage) object;
        if ((this.surveyPageId == null && other.surveyPageId != null)
                || (this.surveyPageId != null && !this.surveyPageId
                        .equals(other.surveyPageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.SurveyPage[ surveyPageId="
                + surveyPageId + " ]";
    }

}
