package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 6/13/14.
 */
public class ConsultationServiceNameInfo {
    private Long ien = null;
    private String name = null;
    private Long parentIEN = null;
    private Boolean hasChild = false;
    private String usage = null;
    private Long orderableItemIEN = null;

    public ConsultationServiceNameInfo(Long ien, String name, Long parentIEN, Boolean hasChild, String usage, Long orderableItemIEN) {
        this.ien = ien;
        this.name = name;
        this.parentIEN = parentIEN;
        this.hasChild = hasChild;
        this.usage = usage;
        this.orderableItemIEN = orderableItemIEN;
    }

    public Long getIen() {
        return ien;
    }

    public String getName() {
        return name;
    }

    public Long getParentIEN() {
        return parentIEN;
    }

    public Boolean hasChild() {
        return hasChild;
    }

    public String getHasChildSymbol() {
        return (hasChild())? "+": "";
    }

    public String getUsage() {
        return usage;
    }

    public Long getOrderableItemIEN() {
        return orderableItemIEN;
    }
}
