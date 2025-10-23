package ru.rishaleva.springBootSecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rishaleva.springBootSecurity.dto.UserRequest;
import ru.rishaleva.springBootSecurity.dto.UserResponse;
import ru.rishaleva.springBootSecurity.model.User;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        var users = userService.getAllUsers()
                .stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable Long id) {
        var user = userService.getUser(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest req) {
        User u = req.toUserForCreate();
        userService.createWithRoles(u, req.getRoleIds());
        return ResponseEntity.created(URI.create("/api/users/" + u.getId()))
                .body(UserResponse.from(u));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest req) {
        var existing = userService.getUser(id);
        if (existing == null) return ResponseEntity.notFound().build();

        User u = req.toUserForUpdate(id, existing.getPassword());
        userService.updateWithRoles(u, req.getRoleIds());
        return ResponseEntity.ok(UserResponse.from(userService.getUser(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var existing = userService.getUser(id);
        if (existing == null) return ResponseEntity.notFound().build();
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}
