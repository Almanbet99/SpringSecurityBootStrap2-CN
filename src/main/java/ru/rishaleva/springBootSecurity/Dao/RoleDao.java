package ru.rishaleva.springBootSecurity.Dao;

import org.springframework.stereotype.Repository;
import ru.rishaleva.springBootSecurity.model.Role;

import java.util.List;

@Repository
public interface RoleDao {
    List<Role> getRoles();

    Role findById(Long id);
    void addRole(Role role);
}
