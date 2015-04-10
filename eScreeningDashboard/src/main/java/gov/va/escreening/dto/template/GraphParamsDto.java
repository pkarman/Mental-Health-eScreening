package gov.va.escreening.dto.template;

import java.util.List;
import java.util.Map;

/**
 * DTO that holds parameters for our graphical templates
 * 
 * @author Robin Carnow
 *
 */
public class GraphParamsDto {

    private Integer varId;
    private String title;
    private String footer;
    private Integer numberOfMonths;
    private Map<String, Integer> intervals;
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
    public Map<String, Integer> getIntervals() {
        return intervals;
    }
    public void setIntervals(Map<String, Integer> intervals) {
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
