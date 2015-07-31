package gov.va.escreening.dto.report;

/**
 * Created by kliu on 3/19/15.
 */
public class Report593ByDayDTO {
    private String date;
    private String dayOfWeek;
    private String total;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
