package ru.rishaleva.springBootSecurity.configs;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rishaleva.springBootSecurity.model.Role;
import ru.rishaleva.springBootSecurity.model.User;
import ru.rishaleva.springBootSecurity.service.RoleService;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.util.Set;

@Component
public class Init implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Init(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Role roleAdmin = roleService.findByNameOrCreate("ROLE_ADMIN");
        Role roleUser  = roleService.findByNameOrCreate("ROLE_USER");

        if (userService.findByUsername("admin") == null) {
            User admin = new User("admin",
                    passwordEncoder.encode("admin"),
                    "Admin", "Adminov", 30L, "admin@mail.ru");
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userService.addUser(admin);
        }

        if (userService.findByUsername("user") == null) {
            User user = new User("user",
                    passwordEncoder.encode("user"),
                    "User", "Userov", 25L, "user@mail.ru");
            user.setRoles(Set.of(roleUser));
            userService.addUser(user);
        }
    }
}




