package ru.rishaleva.springBootSecurity.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    public void saveRole(Role role) {
        if (role.getId() == null) {
            entityManager.persist(role);
        } else {
            entityManager.merge(role);
        }
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        List<Role> roles = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultList();

        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }
}



