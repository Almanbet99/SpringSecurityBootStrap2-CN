package ru.rishaleva.springBootSecurity.service;

import ru.rishaleva.springBootSecurity.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    void saveRole(Role role);
    Optional<Role> getRoleByName(String name);
}
