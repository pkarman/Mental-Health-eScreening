package gov.va.escreening.dto.report;

import java.io.Serializable;

/**
 * Created by kliu on 3/3/15.
 */
public class IntervalDTO implements Serializable {
    private Integer score;
    private String scoreMeaning;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getScoreMeaning() {
        return scoreMeaning;
    }

    public void setScoreMeaning(String scoreMeaning) {
        this.scoreMeaning = scoreMeaning;
    }
}
