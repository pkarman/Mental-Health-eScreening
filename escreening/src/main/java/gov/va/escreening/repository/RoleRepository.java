package gov.va.escreening.repository;

import gov.va.escreening.entity.Role;

import java.util.List;

public interface RoleRepository extends RepositoryInterface<Role> {

	List<Role> getRoles();

}
