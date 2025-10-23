package ru.rishaleva.springBootSecurity.dto;

import ru.rishaleva.springBootSecurity.model.Role;
import ru.rishaleva.springBootSecurity.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private Long age;
    private String email;
    private List<RoleMini> roles;

    public static UserResponse from(User u) {
        UserResponse r = new UserResponse();
        r.id = u.getId();
        r.username = u.getUsername();
        r.name = u.getName();
        r.lastName = u.getLastName();
        r.age = u.getAge();
        r.email = u.getEmail();
        r.roles = u.getRoles().stream()
                .map(role -> new RoleMini(role.getId(), role.getName()))
                .collect(Collectors.toList());
        return r;
    }

    public static class RoleMini {
        public Long id;
        public String name;

        public RoleMini(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public Long getAge() { return age; }
    public String getEmail() { return email; }
    public List<RoleMini> getRoles() { return roles; }
}

