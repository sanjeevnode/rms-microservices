package com.sanjeevnode.rms.orderservice.service;

import com.sanjeevnode.rms.orderservice.repository.OrderRepository;
import com.sanjeevnode.rms.orderservice.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public ApiResponse getAllOrders() {
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Orders found")
                .data(orderRepository.findAll())
                .build();
    }
}
