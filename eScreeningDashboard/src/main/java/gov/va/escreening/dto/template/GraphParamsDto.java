package gov.va.escreening.dto.template;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO that holds parameters for our graphical templates
 * 
 * @author Robin Carnow
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphParamsDto {
    private static final String GRAPHICAL_FREEMARKER_WRAPPER = "${%s}";
    private static final Pattern GRAPICAL_FREEMARKER_UNWRAPPER = Pattern.compile("\\$\\{([^\\}]+)\\}");
    private Integer varId;
    private String title;
    private String footer;
    private Integer numberOfMonths;
    private String score;
    private Map<String, Double> intervals;
    private Double maxXPoint;
    private List<Double> ticks;
    
    public Integer getVarId() {
        return varId;
    }
    public void setVarId(Integer varId) {
        this.varId = varId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFooter() {
        return footer;
    }
    public void setFooter(String footer) {
        this.footer = footer;
    }
    public Integer getNumberOfMonths() {
        return numberOfMonths;
    }
    public void setNumberOfMonths(Integer numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }
    public String getScore() {
        return score;
    }
    public String unwrappedScore(){
        Matcher m = GRAPICAL_FREEMARKER_UNWRAPPER.matcher(score);
        if(m.matches()){
            return m.group(1);
        }
        return score;
    }
    public void setScore(String score) {
        this.score = score == null || score.startsWith("$") ? score : String.format(GRAPHICAL_FREEMARKER_WRAPPER, score);
    }
    public Map<String, Double> getIntervals() {
        return intervals;
    }
    public void setIntervals(Map<String, Double> intervals) {
        this.intervals = intervals;
    }
    public Double getMaxXPoint() {
        return maxXPoint;
    }
    public void setMaxXPoint(Double maxXPoint) {
        this.maxXPoint = maxXPoint;
    }
    public List<Double> getTicks() {
        return ticks;
    }
    public void setTicks(List<Double> ticks) {
        this.ticks = ticks;
    }
}
