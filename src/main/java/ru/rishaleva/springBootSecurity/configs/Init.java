package ru.rishaleva.springBootSecurity.configs;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.rishaleva.springBootSecurity.model.Role;
import ru.rishaleva.springBootSecurity.model.User;
import ru.rishaleva.springBootSecurity.service.RoleService;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.util.Optional;
import java.util.Set;

@Component
public class Init implements ApplicationListener<ApplicationReadyEvent> {

    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Role roleAdmin = roleService.getRoleByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role r = new Role("ROLE_ADMIN");
                    roleService.saveRole(r);
                    return r;
                });

        Role roleUser = roleService.getRoleByName("ROLE_USER")
                .orElseGet(() -> {
                    Role r = new Role("ROLE_USER");
                    roleService.saveRole(r);
                    return r;
                });

        if (userService.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setName("Admin");
            admin.setLastName("Adminov");
            admin.setAge(30L);
            admin.setEmail("admin@mail.com");
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userService.addUser(admin);
        }

        if (userService.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setName("User");
            user.setLastName("Userov");
            user.setAge(25L);
            user.setEmail("user@mail.com");
            user.setRoles(Set.of(roleUser));
            userService.addUser(user);
        }

        System.out.println("✅ Init: Роли и пользователи успешно инициализированы");
    }
}



