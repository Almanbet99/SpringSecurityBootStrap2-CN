package ru.rishaleva.springBootSecurity.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.dao.UserDao;
import ru.rishaleva.springBootSecurity.dto.UserRequest;
import ru.rishaleva.springBootSecurity.dto.UserResponse;
import ru.rishaleva.springBootSecurity.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
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
    public List<UserResponse> getAllUserResponses() {
        return userDao.getAllUsers().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserResponseById(Long id) {
        User user = userDao.getUserById(id);
        if (user == null) throw new EntityNotFoundException("User not found");
        return UserResponse.from(user);
    }


    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        User user = request.toUserForCreate();
        user.setRoles(new HashSet<>(roleService.getByIds(request.getRoleIds())));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        return UserResponse.from(user);
    }


    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userDao.getUserById(id);
        if (existing == null) throw new EntityNotFoundException("User not found");

        existing.setUsername(request.getUsername());
        existing.setName(request.getName());
        existing.setLastName(request.getLastName());
        existing.setAge(request.getAge());
        existing.setEmail(request.getEmail());

        existing.setRoles(new HashSet<>(roleService.getByIds(request.getRoleIds())));

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userDao.updateUser(existing);
        return UserResponse.from(existing);
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userDao.getUserById(id);
        if (user == null) throw new EntityNotFoundException("User not found");
        userDao.removeUser(id);
    }


    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUserById(id);
    }
}

