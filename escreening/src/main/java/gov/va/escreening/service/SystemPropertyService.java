package gov.va.escreening.service;

import gov.va.escreening.entity.SystemProperty;

public interface SystemPropertyService {
    /**
     * Retrieves system property by systemPropertyId
     * @param veteranAssessmentId
     * @return
     */
    SystemProperty findBySystemPropertyId(Integer systemPropertyId);
}
