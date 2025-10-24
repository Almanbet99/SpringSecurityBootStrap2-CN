package ru.rishaleva.springBootSecurity.service;

import ru.rishaleva.springBootSecurity.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    void saveRole(Role role);

    Role findByNameOrThrow(String name);
    Role findByNameOrCreate(String name);

    List<Role> findAllByIds(List<Long> ids);

    int getByIds(List<Long> roleIds);
}
