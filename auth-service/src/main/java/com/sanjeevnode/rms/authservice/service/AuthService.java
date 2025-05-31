package com.sanjeevnode.rms.authservice.service;

import com.sanjeevnode.rms.authservice.dto.AuthResponse;
import com.sanjeevnode.rms.authservice.dto.RegisterUserDTO;
import com.sanjeevnode.rms.authservice.dto.UserDTO;
import com.sanjeevnode.rms.authservice.enums.Role;
import com.sanjeevnode.rms.authservice.exception.UserAlreadyExistException;
import com.sanjeevnode.rms.authservice.exception.UserNotFoundException;
import com.sanjeevnode.rms.authservice.model.User;
import com.sanjeevnode.rms.authservice.repository.UserRepository;
import com.sanjeevnode.rms.authservice.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    
    private JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public ApiResponse register(RegisterUserDTO request){


        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole()!=null ? request.getRole() : Role.ROLE_USER);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return ApiResponse.builder()
                .status(HttpStatus.CREATED)
                .message("User registered successfully")
                .data(new AuthResponse(user.getUsername(), user.getRole().name(),token))
                .build();
    }

    public ApiResponse login(RegisterUserDTO request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(user);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("User logged in successfully")
                .data(new AuthResponse(user.getUsername(), user.getRole().name(),token))
                .build();
    }

    public ApiResponse getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole().name());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setId(user.getId());
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("User details retrieved successfully")
                .data(userDTO)
                .build();
    }

}
