package gov.va.escreening.entity;

import java.io.Serializable;

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

/**
 * 
 * @author jjinn
 */
@Entity
@Table(name = "program_survey")
@NamedQueries({
        @NamedQuery(name = "ProgramSurvey.findAll", query = "SELECT p FROM ProgramSurvey p") })
public class ProgramSurvey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "program_survey_id")
    private Integer programSurveyId;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne(optional = false)
    private Program program;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey survey;

    public ProgramSurvey() {
    }

    public ProgramSurvey(Integer programSurveyId) {
        this.programSurveyId = programSurveyId;
    }

    public Integer getProgramSurveyId() {
        return programSurveyId;
    }

    public void setProgramSurveyId(Integer programSurveyId) {
        this.programSurveyId = programSurveyId;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programSurveyId != null ? programSurveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramSurvey)) {
            return false;
        }
        ProgramSurvey other = (ProgramSurvey) object;
        if ((this.programSurveyId == null && other.programSurveyId != null)
                || (this.programSurveyId != null && !this.programSurveyId.equals(other.programSurveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.ProgramSurvey[ programSurveyId=" + programSurveyId + " ]";
    }

}
