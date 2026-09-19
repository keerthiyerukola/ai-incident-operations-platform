package com.keerthi.incident_platform.auth;

import com.keerthi.incident_platform.user.dto.CreateUserRequest;
import com.keerthi.incident_platform.user.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(
            AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserResponse register(
            @RequestBody CreateUserRequest request
    ) {
        return authenticationService.register(request);
    }
    @PostMapping("/login")
public LoginResponse login(@RequestBody LoginRequest request) {
    return authenticationService.login(request);
}
}