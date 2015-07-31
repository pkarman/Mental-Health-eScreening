package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.UserStatus;

import java.util.List;

public interface UserStatusService {
    
    /**
     * Returns all the user status reference data.
     * @return
     */
	List<UserStatus> getUserStatusList();
	
    /**
     * Retrieves all the user status as a DropDownObject list.
     * @return
     */
    List<DropDownObject> getUserStatusDropDownObjects();
}
