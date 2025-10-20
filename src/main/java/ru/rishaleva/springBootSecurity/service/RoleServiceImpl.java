package ru.rishaleva.springBootSecurity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.dao.RoleDao;
import ru.rishaleva.springBootSecurity.model.Role;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}



