package ru.rishaleva.springBootSecurity.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.dao.UserDao;
import ru.rishaleva.springBootSecurity.model.Role;
import ru.rishaleva.springBootSecurity.model.User;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            User existing = userDao.getUser(user.getId());
            user.setPassword(existing.getPassword());
        } else if (!isBCryptHash(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


    @Override
    public void createWithRoles(User user, List<Long> roleIds) {
        var roles = new HashSet<Role>();
        if (roleIds == null || roleIds.isEmpty()) {
            roles.add(roleService.findByNameOrThrow("ROLE_USER"));
        } else {
            roles.addAll(roleService.findAllByIds(roleIds));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    public void updateWithRoles(User user, List<Long> roleIds) {
        var roles = new HashSet<Role>();
        if (roleIds == null || roleIds.isEmpty()) {
            roles.add(roleService.findByNameOrThrow("ROLE_USER"));
        } else {
            roles.addAll(roleService.findAllByIds(roleIds));
        }
        user.setRoles(roles);

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            User existing = userDao.getUser(user.getId());
            user.setPassword(existing.getPassword());
        } else if (!isBCryptHash(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDao.updateUser(user);
    }

    private boolean isBCryptHash(String value) {
        if (value == null) return false;
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }
}
