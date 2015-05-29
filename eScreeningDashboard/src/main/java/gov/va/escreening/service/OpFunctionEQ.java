package gov.va.escreening.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by munnoo on 4/22/15.
 */
@Component("opFunction_EQ")
public class OpFunctionEQ implements OpFunction {
    @Override
    public boolean apply(Integer left, Integer right) {
        return left.compareTo(right) == 0;
    }
}
