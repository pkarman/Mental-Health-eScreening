package gov.va.escreening.dto;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.template.TemplateIfBlockDTO;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.exception.IllegalSystemStateException;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RuleDto {
    
    private static final Logger logger = LoggerFactory.getLogger(RuleDto.class);

    private Integer id;
    private String name;
    private TemplateIfBlockDTO condition;
    
    public RuleDto(Rule dbRule){
        id = dbRule.getRuleId();
        name = dbRule.getName();
        
        //set the condition field by parsing the saved json structure
        if (dbRule.getCondition() != null) {
            // now parsing the json file
            ObjectMapper om = new ObjectMapper();
            try {
                condition = (om.readValue(dbRule.getCondition(), TemplateIfBlockDTO.class));
            }catch(IOException e){
                String errorMsg = "Error reading json file field for condition. ID: " + dbRule.getRuleId();
                
                logger.error(errorMsg, e);
                
                ErrorBuilder.throwing(IllegalSystemStateException.class)
                .toAdmin(errorMsg)
                .toUser("Error creating rule. Please call support.")
                .throwIt();
            }
        } 
    }
    
    /**
     * Used for light object
     * @param id
     * @param name
     */
    public RuleDto(Integer id, String name){
        this.id = id;
        this.name = name;
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

    public TemplateIfBlockDTO getCondition() {
        return condition;
    }

    public void setCondition(TemplateIfBlockDTO condition) {
        this.condition = condition;
    }
    
    /**
     * Needed for json decoding. please use the other constructor 
     */
    @SuppressWarnings("unused")
    private RuleDto(){}
}
