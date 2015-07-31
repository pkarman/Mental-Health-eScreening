package gov.va.escreening.vista.dto;

import java.util.Set;

/**
 * Created by pouncilt on 5/7/14.
 */
public class HealthFactorLists{
    private Set<HealthFactor> historicalHealthFactors;
    private Set<HealthFactor> currentHealthFactors;

    public HealthFactorLists(Set<HealthFactor> currentHealthFactors, Set<HealthFactor> historicalHealthFactors) {
        this.currentHealthFactors = currentHealthFactors;
        this.historicalHealthFactors = historicalHealthFactors;
    }

    public Set<HealthFactor> getHistoricalHealthFactors() {
        return historicalHealthFactors;
    }

    public Set<HealthFactor> getCurrentHealthFactors() {
        return currentHealthFactors;
    }
}
