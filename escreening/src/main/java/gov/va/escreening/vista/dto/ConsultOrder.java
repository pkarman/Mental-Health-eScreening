package gov.va.escreening.vista.dto;

import java.util.Date;

/**
 * Created by pouncilt on 6/10/14.
 */
public class ConsultOrder {
    private Long orderIEN = null;
    private Integer groupNumber = null;
    private Date orderTime = null;
    private Date startTime = null;
    private Date endTime = null;
    private String status = null;
    private String signature = null;
    private String nurseName = null;
    private String clerkName = null;
    private Long providerIEN = null;
    private String providerName = null;
    private String actDA = null;
    private Boolean flagged = false;
    private String dcType = null;
    private String chartRev = null;
    private String deaNumber = null;
    private String providerVANumber = null;
    private String DigSig = null;
    private String locationName = null;
    private Long locationIEN = null;
    private String dcOriginalOrder = null;
    private String piece21 = null;
    private String title = null;


    public ConsultOrder(Long orderIEN, Integer groupNumber, Date orderTime, Date startTime,
                        Date endTime, String status, String signature, String nurseName, String clerkName,
                        Long providerIEN, String providerName, String actDA, Boolean flagged, String dcType,
                        String chartRev, String deaNumber, String providerVANumber, String digSig, String locationName,
                        Long locationIEN, String dcOriginalOrder, String piece21, String title) {

        this.orderIEN = orderIEN;
        this.groupNumber = groupNumber;
        this.orderTime = orderTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.signature = signature;
        this.nurseName = nurseName;
        this.clerkName = clerkName;
        this.providerIEN = providerIEN;
        this.providerName = providerName;
        this.actDA = actDA;
        this.flagged = flagged;
        this.dcType = dcType;
        this.chartRev = chartRev;
        this.deaNumber = deaNumber;
        this.providerVANumber = providerVANumber;
        DigSig = digSig;
        this.locationName = locationName;
        this.locationIEN = locationIEN;
        this.dcOriginalOrder = dcOriginalOrder;
        this.piece21 = piece21;
        this.title = title;
    }

    public Long getOrderIEN() {
        return orderIEN;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    public String getSignature() {
        return signature;
    }

    public String getNurseName() {
        return nurseName;
    }

    public String getClerkName() {
        return clerkName;
    }

    public Long getProviderIEN() {
        return providerIEN;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getActDA() {
        return actDA;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public String getDcType() {
        return dcType;
    }

    public String getChartRev() {
        return chartRev;
    }

    public String getDeaNumber() {
        return deaNumber;
    }

    public String getProviderVANumber() {
        return providerVANumber;
    }

    public String getDigSig() {
        return DigSig;
    }

    public String getLocationName() { return locationName; }

    public Long getLocationIEN() {
        return locationIEN;
    }

    public String getDcOriginalOrder() {
        return dcOriginalOrder;
    }

    public String getPiece21() {
        return piece21;
    }

    public String getTitle() {
        return title;
    }
}
