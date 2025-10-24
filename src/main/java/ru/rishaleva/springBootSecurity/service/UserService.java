package ru.rishaleva.springBootSecurity.service;

import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.dto.UserRequest;
import ru.rishaleva.springBootSecurity.dto.UserResponse;
import ru.rishaleva.springBootSecurity.model.User;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUserResponses();

    UserResponse getUserResponseById(Long id);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);

    User findByUsername(String username);

    // методы старого интерфейса (чтобы всё осталось совместимо)
    List<User> getAllUsers();

    void addUser(User admin);

    @Transactional
    void removeUser(Long id);

    User getUser(Long id);
}


