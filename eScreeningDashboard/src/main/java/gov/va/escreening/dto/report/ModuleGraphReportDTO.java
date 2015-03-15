package gov.va.escreening.dto.report;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kliu on 3/1/15.
 */
public class ModuleGraphReportDTO implements Serializable{

    private String veteranCount;
    private String moduleName;
    private String scoreName;
    private String score;
    private String scoreMeaning;

    private String imageInput;

    private String scoreHistoryTitle;

    private List<ScoreHistoryDTO> scoreHistory;

    private Boolean hasData = true;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreMeaning() {
        return scoreMeaning;
    }

    public void setScoreMeaning(String scoreMeaning) {
        this.scoreMeaning = scoreMeaning;
    }

    public String getScoreHistoryTitle() {
        return scoreHistoryTitle;
    }

    public void setScoreHistoryTitle(String scoreHistoryTitle) {
        this.scoreHistoryTitle = scoreHistoryTitle;
    }

    public List<ScoreHistoryDTO> getScoreHistory() {
        return scoreHistory;
    }

    public void setScoreHistory(List<ScoreHistoryDTO> scoreHistory) {
        this.scoreHistory = scoreHistory;
    }


    public String getImageInput() {
        return imageInput;
    }

    public void setImageInput(String imageInput) {
        this.imageInput = imageInput;
    }

    public Boolean getHasData() {
        return hasData;
    }

    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }

    public String getVeteranCount() {
        return veteranCount;
    }

    public void setVeteranCount(String veteranCount) {
        this.veteranCount = veteranCount;
    }
}
