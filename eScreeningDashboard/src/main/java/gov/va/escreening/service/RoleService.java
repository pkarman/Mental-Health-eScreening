package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Role;

import java.util.List;

public interface RoleService {

    /**
     * Retrieves all the roles.
     * @return
     */
    List<Role> getRoles();

    /**
     * Retrieves all the roles as a DropDownObject list.
     * @return
     */
    List<DropDownObject> getRoleDropDownObjects();

}
