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

    void createWithRoles(User user, List<Long> roleIds);
    void updateWithRoles(User user, List<Long> roleIds);

}



