package gov.va.escreening.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kliu on 3/3/15.
 */
public class DataPointDTO implements Serializable {

    private Date scoreDate;
    private Integer score;

    public Date getScoreDate() {
        return scoreDate;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
