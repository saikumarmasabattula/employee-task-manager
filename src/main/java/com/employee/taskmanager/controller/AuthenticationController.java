package com.employee.taskmanager.controller;

import com.employee.taskmanager.dto.AuthenticationResponse;
import com.employee.taskmanager.dto.LoginRequest;
import com.employee.taskmanager.dto.RegisterRequest;
import com.employee.taskmanager.entity.User;
import com.employee.taskmanager.enums.Role;
import com.employee.taskmanager.security.CustomUserDetailsService;
import com.employee.taskmanager.security.JwtService;
import com.employee.taskmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User Registration and Login APIs")
public class AuthenticationController {
        private static final Logger logger =
        LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account."
    )
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {
        logger.info("Registration request received for user: {}", request.getUsername());

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        // Default role is USER
        if (request.getRole() == null) {
            user.setRole(Role.USER);
        } else {
            user.setRole(request.getRole());
        }

        userService.registerUser(user);
        logger.info("User registered successfully: {}", request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @Operation(
            summary = "Login",
            description = "Authenticates the user and returns a JWT token."
    )
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest request) {
                 
        logger.info("Login attempt for user: {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(request.getUsername());

        String token = jwtService.generateToken(userDetails);

         logger.info("User logged in successfully: {}", request.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}