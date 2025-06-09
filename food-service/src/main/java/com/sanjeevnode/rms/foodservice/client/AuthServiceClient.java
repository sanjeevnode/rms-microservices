package com.sanjeevnode.rms.foodservice.client;

import com.sanjeevnode.rms.foodservice.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="AUTH-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/auth/validate")
    ResponseEntity<ApiResponse> validateToken(@RequestParam("token") String token);
}
