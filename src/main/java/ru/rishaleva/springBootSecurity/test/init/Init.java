package ru.rishaleva.springBootSecurity.test.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rishaleva.springBootSecurity.service.RoleServiceImpl;
import ru.rishaleva.springBootSecurity.service.UserServiceImpl;
import ru.rishaleva.springBootSecurity.model.Role;
import ru.rishaleva.springBootSecurity.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

    private RoleServiceImpl roleService;
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Init(RoleServiceImpl roleService, UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleService.addRole(userRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleService.addRole(adminRole);

        Set<Role> userRoles = new HashSet<>(Arrays.asList(userRole));
        Set<Role> adminRoles = new HashSet<>(Arrays.asList(adminRole));

        User admin = new User();
        admin.setName("admin");
        admin.setLastName("admin");
        admin.setAge(30L);
        admin.setEmail("admin@mail.ru");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        userService.addUser(admin);

        User user = new User();
        user.setName("user");
        user.setLastName("user");
        user.setAge(20L);
        user.setEmail("user@mail.ru");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(userRoles);
        userService.addUser(user);
    }
}