package com.sanjeevnode.rms.foodservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanjeevnode.rms.foodservice.dto.UserDTO;
import com.sanjeevnode.rms.foodservice.exception.InvalidTokenException;
import com.sanjeevnode.rms.foodservice.utils.ApiResponse;
import com.sanjeevnode.rms.foodservice.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; private final AppLogger logger = new AppLogger(AuthService.class, "AuthService");


    public UserDTO validateToken(String token) {
        String validateUrl = "http://AUTH-SERVICE:5000/auth/validate?token=" + token;
        logger.info("Validating token at URL: "+ validateUrl);
        ResponseEntity<ApiResponse> responseEntity =
                restTemplate.postForEntity(validateUrl, null, ApiResponse.class);

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
