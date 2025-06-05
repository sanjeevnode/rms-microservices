package com.sanjeevnode.rms.orderservice.client;


import com.sanjeevnode.rms.orderservice.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="auth-service", url="${auth.service.url}")
public interface AuthServiceClient {

    @PostMapping("/auth/validate")
    ResponseEntity<ApiResponse> validateToken(@RequestParam("token") String token);
}
