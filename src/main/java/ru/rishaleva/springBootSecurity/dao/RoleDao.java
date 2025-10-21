package ru.rishaleva.springBootSecurity.dao;

import org.springframework.stereotype.Repository;
import ru.rishaleva.springBootSecurity.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDao {
    List<Role> getAllRoles();
    void saveRole(Role role);
    Optional<Role> getRoleByName(String name);
    List<Role> findAllByIds(List<Long> ids);
}


