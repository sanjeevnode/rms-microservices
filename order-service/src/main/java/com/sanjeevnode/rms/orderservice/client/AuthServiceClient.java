package com.sanjeevnode.rms.orderservice.client;


import com.sanjeevnode.rms.orderservice.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="AUTH-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/auth/v0/validate")
    ResponseEntity<ApiResponse> validateToken(@RequestParam("token") String token);
}
