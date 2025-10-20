package ru.rishaleva.springBootSecurity.dao;

import ru.rishaleva.springBootSecurity.model.User;
import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    User getUser(Long id);
    void addUser(User user);
    void updateUser(User user);
    void removeUser(Long id);
    User findByUsername(String username);
}



