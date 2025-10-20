package ru.rishaleva.springBootSecurity.service;

import ru.rishaleva.springBootSecurity.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(Long id);

    User findByUsername(String username);

    User findByUserEmail(String name);

    User findByUserName(String name);

}



