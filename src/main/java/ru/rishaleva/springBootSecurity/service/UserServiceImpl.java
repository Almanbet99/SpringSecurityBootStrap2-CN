package ru.rishaleva.springBootSecurity.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.dao.UserDao;
import ru.rishaleva.springBootSecurity.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        String rawOrHash = user.getPassword();
        if (!isBCryptHash(rawOrHash)) {
            user.setPassword(passwordEncoder.encode(rawOrHash));
        }
        userDao.updateUser(user);
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByUserEmail(String name) {
        return null;
    }

    @Override
    public User findByUserName(String name) {
        return null;
    }

    private boolean isBCryptHash(String value) {
        if (value == null) return false;
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }
}
