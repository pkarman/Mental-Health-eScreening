package gov.va.escreening.repository;

import gov.va.escreening.entity.Role;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends AbstractHibernateRepository<Role> implements RoleRepository {

    public RoleRepositoryImpl() {
        super();

        setClazz(Role.class);
    }

    public List<Role> getRoles() {

        String sql = "SELECT r FROM Role r ORDER BY r.name";

        TypedQuery<Role> query = entityManager.createQuery(sql, Role.class);

        List<Role> roles = query.getResultList();

        return roles;
    }

}
