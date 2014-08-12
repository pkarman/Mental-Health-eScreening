package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.HealthFactor;
import gov.va.escreening.vista.dto.HealthFactorHeader;
import gov.va.escreening.vista.dto.HealthFactorImmunization;
import gov.va.escreening.vista.dto.HealthFactorProvider;
import gov.va.escreening.vista.dto.HealthFactorVisitData;
import gov.va.escreening.vista.dto.VisitTypeEnum;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by pouncilt on 5/5/14.
 */
public class ORWPCE_SAVE_RequestParameters extends VistaLinkRequestParameters {
    private HealthFactorHeader healthFactorHeader = null;
    private Set<HealthFactorVisitData> healthFactorVisitDataSet = new LinkedHashSet<HealthFactorVisitData>();
    private HealthFactorProvider healthFactorProvider = null;
    private Set<HealthFactorImmunization> healthFactorImmunizations = new LinkedHashSet<HealthFactorImmunization>();
    private Set<HealthFactor> healthFactors = new LinkedHashSet<HealthFactor>();
    private Long noteIEN = null;
    private Long locationIEN = null;
    private Boolean historicalHealthFactor = false;

    public ORWPCE_SAVE_RequestParameters(Long noteIEN,
                                         Long locationIEN,
                                         Boolean historicalHealthFactor,
                                         HealthFactorHeader healthFactorHeader,
                                         Set<HealthFactorVisitData> healthFactorVisitDataSet,
                                         HealthFactorProvider healthFactorProvider,
                                         Set<HealthFactor> healthFactors) {
        this.noteIEN = noteIEN;
        this.locationIEN = locationIEN;
        this.healthFactorHeader = healthFactorHeader;
        this.healthFactorVisitDataSet = healthFactorVisitDataSet;
        this.healthFactorProvider = healthFactorProvider;
        this.healthFactors = healthFactors;
        this.noteIEN = noteIEN;
        this.locationIEN = locationIEN;
        if(historicalHealthFactor != null) this.historicalHealthFactor = historicalHealthFactor;

        checkRequiredParameters();
    }

    public ORWPCE_SAVE_RequestParameters(Long noteIEN,
                                         Long locationIEN,
                                         Boolean historicalHealthFactor,
                                         HealthFactorHeader healthFactorHeader,
                                         Set<HealthFactorVisitData> healthFactorVisitDataSet,
                                         HealthFactorProvider healthFactorProvider,
                                         Set<HealthFactorImmunization> healthFactorImmunizations,
                                         Set<HealthFactor> healthFactors) {
        this.noteIEN = noteIEN;
        this.locationIEN = locationIEN;
        this.healthFactorHeader = healthFactorHeader;
        this.healthFactorVisitDataSet = healthFactorVisitDataSet;
        this.healthFactorProvider = healthFactorProvider;
        this.healthFactorImmunizations = healthFactorImmunizations;
        this.healthFactors = healthFactors;
        if(historicalHealthFactor != null) this.historicalHealthFactor = historicalHealthFactor;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        if (noteIEN == null) throw new NullPointerException("Request Parameter \"noteIEN\" cannot be Null.");
        if (locationIEN == null) throw new NullPointerException("Request Parameter \"locationIEN\" cannot be Null.");
        if (healthFactorHeader == null) throw new NullPointerException("Request Parameter \"healthFactorHeader\" cannot be Null.");
        if (healthFactorVisitDataSet == null) throw new NullPointerException("Request Parameter \"healthFactorVisitDataSet\" cannot be Null.");
        if (healthFactorProvider == null) throw new NullPointerException("Request Parameter \"healthFactorProvider\" cannot be Null.");
        if (healthFactors == null) throw new NullPointerException("Request Parameter \"healthFactors\" cannot be Null.");

        checkVisitDataForHistoricalHealthFactorConstraint();
    }

    private void checkVisitDataForHistoricalHealthFactorConstraint() {
        boolean requiredOLType = (historicalHealthFactor)? true : false;
        boolean requiredHLType = (historicalHealthFactor)? false : true;
        boolean requiredPRType = (historicalHealthFactor)? true : false;
        boolean olTypeFound = false;
        boolean hlTypeFound = false;
        boolean prTypeFound = false;

        for (HealthFactorVisitData healthFactorVisitData: healthFactorVisitDataSet) {
            if(healthFactorVisitData.getType() == VisitTypeEnum.OUTSIDE_LOCATION_HIST && requiredOLType) {
                olTypeFound = true;
            }

            if(healthFactorVisitData.getType() == VisitTypeEnum.ENCOUNTER_LOCATION && requiredHLType) {
                hlTypeFound = true;
            }

            if(healthFactorVisitData.getType() == VisitTypeEnum.PARENT_VISIT_IEN_HIST && requiredPRType) {
                prTypeFound = true;
            }
        }

        if (requiredPRType && !prTypeFound) {
            throw new IllegalArgumentException("The " + VisitTypeEnum.PARENT_VISIT_IEN_HIST + " must be present in the Visit Data because it is historical data.");
        }

        if (requiredOLType && requiredHLType){
            throw new IllegalArgumentException("Both the " + VisitTypeEnum.ENCOUNTER_LOCATION.getCode() + " and " + VisitTypeEnum.OUTSIDE_LOCATION_HIST + " cannot be present in the Visit Data.");
        } else if (requiredOLType && !requiredHLType) {
            if(!olTypeFound) {
                throw new IllegalArgumentException("The " + VisitTypeEnum.OUTSIDE_LOCATION_HIST + " must be present in the Visit Data because it is historical data.");
            }
            if(hlTypeFound) {
                throw new IllegalArgumentException("The " + VisitTypeEnum.ENCOUNTER_LOCATION + " cannot be present in the Visit Data because it is historical data.");
            }
        } else if (!requiredOLType && requiredHLType) {
            if(!hlTypeFound) {
                throw new IllegalArgumentException("The " + VisitTypeEnum.ENCOUNTER_LOCATION + " must be present in the Visit Data because it is NOT historical data.");
            }
            if(olTypeFound) {
                throw new IllegalArgumentException("The " + VisitTypeEnum.OUTSIDE_LOCATION_HIST + " cannot be present in the Visit Data because it is NOT historical data.");
            }
        } else {
            throw new IllegalArgumentException("One of the following Visit Data Type has to be present in the Visit Data: " + VisitTypeEnum.ENCOUNTER_LOCATION.getCode() + " or " + VisitTypeEnum.OUTSIDE_LOCATION_HIST + ".");
        }
    }

    public Long getNoteIEN() {
        return noteIEN;
    }

    public Long getLocationIEN() {
        return locationIEN;
    }

    public HealthFactorHeader getHealthFactorHeader() {
        return healthFactorHeader;
    }

    public Set<HealthFactorVisitData> getHealthFactorVisitDataSet() {
        return healthFactorVisitDataSet;
    }

    public HealthFactorProvider getHealthFactorProvider() {
        return healthFactorProvider;
    }

    public Set<HealthFactorImmunization> getHealthFactorImmunizations() {
        return healthFactorImmunizations;
    }

    public Set<HealthFactor> getHealthFactors() {
        return healthFactors;
    }
}
