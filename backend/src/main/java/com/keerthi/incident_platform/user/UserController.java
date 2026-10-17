package com.keerthi.incident_platform.user;

import com.keerthi.incident_platform.user.dto.CreateUserRequest;
import com.keerthi.incident_platform.user.dto.UserResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/admin-test")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminTest() {
        return "You are an admin";
    }

    @GetMapping("/engineer-test")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public String engineerTest() {
        return "You are an engineer or admin";
    }

    @GetMapping("/viewer-test")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'VIEWER')")
    public String viewerTest() {
        return "You are authenticated";
    }
}