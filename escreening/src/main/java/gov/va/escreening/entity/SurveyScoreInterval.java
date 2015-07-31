package gov.va.escreening.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kliu on 3/6/15.
 */
@Entity
@Table(name = "survey_score_interval")
public class SurveyScoreInterval implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    private Survey survey;


    @Column(length = 20)
    private String min;

    @Column(length = 20)
    private String max;

    @Column(length = 100)
    private String meaning;

    @Column(name = "exception")
    private boolean exception;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isException() {
        return exception;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }
}
