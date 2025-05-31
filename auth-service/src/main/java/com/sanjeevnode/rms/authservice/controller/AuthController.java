package com.sanjeevnode.rms.authservice.controller;


import com.sanjeevnode.rms.authservice.dto.RegisterUserDTO;
import com.sanjeevnode.rms.authservice.service.AuthService;
import com.sanjeevnode.rms.authservice.utils.ApiResponse;
import com.sanjeevnode.rms.authservice.utils.AppLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Tag(name = "Restaurant Management System", description = "Authentication and Authorization")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppLogger logger = new AppLogger(AuthController.class, "AuthController");

    @GetMapping("/health")
    public String healthCheck() {
        logger.info("Health check endpoint hit");
        return "Auth Service is running";
    }

    @PostMapping("/auth/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        logger.info("Register endpoint hit");
        return authService.register(registerUserDTO).buildResponse();
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login a user")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        logger.info("Login endpoint hit");
        return authService.login(registerUserDTO).buildResponse();
    }

    @GetMapping("/user")
    @Operation(summary = "Get user details")
    public ResponseEntity<ApiResponse> getUserDetails(@RequestParam String username) {
        logger.info("Get user details endpoint hit");
        return authService.getUserDetails(username).buildResponse();
    }
}
