package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;

import java.util.List;

public interface AssessmentStatusService {

    /**
     * Retrieves the Assessment Status List dropdown list.
     * @return
     */
    List<DropDownObject> getAssessmentStatusList();
}
