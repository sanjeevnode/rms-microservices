package com.sanjeevnode.rms.orderservice.controller;

import com.sanjeevnode.rms.orderservice.service.OrderService;
import com.sanjeevnode.rms.orderservice.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
@Tag(name = "Restaurant Management System", description = "Order Service")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/health")
    @Operation(summary = "Health Check Endpoint")
    public String healthCheck() {
        return "Order Service is running";
    }

    @GetMapping("/")
    @Operation(summary = "Get Orders for a user")
    public ResponseEntity<ApiResponse> getOrders() {
        return orderService.getAllOrders().buildResponse();
    }
}
