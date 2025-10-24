package ru.rishaleva.springBootSecurity.dao;

import ru.rishaleva.springBootSecurity.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(Long id); // ← исправили название метода

    void addUser(User user);

    void updateUser(User user);

    void removeUser(Long id);

    User findByUsername(String username);
}



