package ru.rishaleva.springBootSecurity.dto;

import ru.rishaleva.springBootSecurity.model.User;

import java.util.List;

public class UserRequest {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private Long age;
    private String email;
    private List<Long> roleIds;

    public User toUserForCreate() {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setName(name);
        u.setLastName(lastName);
        u.setAge(age);
        u.setEmail(email);
        return u;
    }

    public User toUserForUpdate(Long id, String existingPasswordHash) {
        User u = new User();
        u.setId(id);
        u.setUsername(username);
        u.setPassword((password == null || password.isBlank()) ? existingPasswordHash : password);
        u.setName(name);
        u.setLastName(lastName);
        u.setAge(age);
        u.setEmail(email);
        return u;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Long getAge() { return age; }
    public void setAge(Long age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(List<Long> roleIds) { this.roleIds = roleIds; }
}

