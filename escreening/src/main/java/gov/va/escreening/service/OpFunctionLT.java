package gov.va.escreening.service;

import org.springframework.stereotype.Component;

/**
 * Created by munnoo on 4/22/15.
 */
@Component("opFunction_LT")
public class OpFunctionLT implements OpFunction {
    @Override
    public boolean apply(Integer left, Integer right) {
        return left.compareTo(right) < 0;
    }
}
