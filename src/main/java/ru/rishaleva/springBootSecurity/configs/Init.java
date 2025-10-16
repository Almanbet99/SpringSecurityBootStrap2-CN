package ru.rishaleva.springBootSecurity.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rishaleva.springBootSecurity.model.Role;
import ru.rishaleva.springBootSecurity.model.User;
import ru.rishaleva.springBootSecurity.service.RoleService;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.util.Set;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Init(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role roleUser = roleService.getRoleByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role("ROLE_USER");
            roleService.addRole(roleUser);
        }

        Role roleAdmin = roleService.getRoleByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role("ROLE_ADMIN");
            roleService.addRole(roleAdmin);
        }

        if (userService.findByUserEmail("admin@mail.ru") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setName("Администратор");
            admin.setLastName("Главный");
            admin.setAge(30L);
            admin.setEmail("admin@mail.ru");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userService.addUser(admin);
        }

        if (userService.findByUserEmail("user@mail.ru") == null) {
            User user = new User();
            user.setUsername("user");
            user.setName("Пользователь");
            user.setLastName("Обычный");
            user.setAge(25L);
            user.setEmail("user@mail.ru");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(Set.of(roleUser));
            userService.addUser(user);
        }

        System.out.println("✅ Роли и пользователи успешно инициализированы!");
    }
}
