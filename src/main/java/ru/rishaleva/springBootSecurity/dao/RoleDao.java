package ru.rishaleva.springBootSecurity.dao;

import ru.rishaleva.springBootSecurity.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    List<Role> getAllRoles();
    void saveRole(Role role);
    Optional<Role> getRoleByName(String name);
}

