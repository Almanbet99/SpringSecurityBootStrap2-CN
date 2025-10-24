package ru.rishaleva.springBootSecurity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.dao.RoleDao;
import ru.rishaleva.springBootSecurity.model.Role;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Transactional
    @Override
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Override
    public Role findByNameOrThrow(String name) {
        return roleDao.getRoleByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + name));
    }

    @Override
    public Role findByNameOrCreate(String name) {
        return roleDao.getRoleByName(name).orElseGet(() -> {
            Role r = new Role(name);
            roleDao.saveRole(r);
            return r;
        });
    }

    @Override
    public List<Role> findAllByIds(List<Long> ids) {
        return roleDao.findAllByIds(ids);
    }

    @Override
    public int getByIds(List<Long> roleIds) {
        return 0;
    }
}

