package com.sanjeevnode.rms.foodservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanjeevnode.rms.foodservice.client.AuthServiceClient;
import com.sanjeevnode.rms.foodservice.dto.UserDTO;
import com.sanjeevnode.rms.foodservice.exception.InvalidTokenException;
import com.sanjeevnode.rms.foodservice.utils.ApiResponse;
import com.sanjeevnode.rms.foodservice.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthServiceClient authServiceClient;

    private final ObjectMapper objectMapper; private final AppLogger logger = new AppLogger(AuthService.class, "AuthService");


    public UserDTO validateToken(String token) {
        ResponseEntity<ApiResponse> responseEntity = authServiceClient.validateToken(token);
        logger.info(responseEntity.toString());

        var statusCode = responseEntity.getStatusCode();

        if (!statusCode.is2xxSuccessful() || responseEntity.getBody() == null) {
            logger.error("Token validation failed with status: " + statusCode,new InvalidTokenException("Invalid token"));
            throw new InvalidTokenException("Invalid token");
        }
        var body = responseEntity.getBody();
        return objectMapper.convertValue(body.getData(), UserDTO.class);
    }
}
