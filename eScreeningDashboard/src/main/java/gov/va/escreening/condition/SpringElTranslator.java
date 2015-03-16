package gov.va.escreening.condition;

import gov.va.escreening.dto.template.TemplateBaseContent;

import java.util.Set;

public class SpringElTranslator implements BlockTranslator {

    @Override
    public String translateCondition(String operator, TemplateBaseContent inLeft,
            TemplateBaseContent right, Set<Integer> avIds) {
        // TODO Auto-generated method stub
        return "condition placeholder";
    }

}
