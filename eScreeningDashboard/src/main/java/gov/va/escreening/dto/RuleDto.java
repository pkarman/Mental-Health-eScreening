package gov.va.escreening.dto;

import gov.va.escreening.entity.Rule;

public class RuleDto {

    private Integer id;
    private String name;
    private String expression;
    
    public RuleDto(Rule dbRule){
        setId(dbRule.getRuleId());
        setName(dbRule.getName());
        setExpression(dbRule.getExpression());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    /**
     * Needed for json decoding. please use the other constructor 
     */
    @SuppressWarnings("unused")
    private RuleDto(){}
}
