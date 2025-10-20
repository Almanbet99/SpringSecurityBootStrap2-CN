package ru.rishaleva.springBootSecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rishaleva.springBootSecurity.model.User;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existing = userService.getUser(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        updatedUser.setId(id);
        userService.updateUser(updatedUser);
        return ResponseEntity.ok().build();
    }
}
