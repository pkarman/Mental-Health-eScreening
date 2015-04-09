package gov.va.escreening.dto.report;

import java.util.List;

/**
 * Created by kliu on 3/3/15.
 */
public class ImageInputDTO {
    List<IntervalDTO> intervals;

    List<DataPointDTO> dataPoints;

    public List<IntervalDTO> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<IntervalDTO> intervals) {
        this.intervals = intervals;
    }

    public List<DataPointDTO> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPointDTO> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
