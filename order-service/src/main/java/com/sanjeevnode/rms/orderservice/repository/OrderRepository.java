package com.sanjeevnode.rms.orderservice.repository;

import com.sanjeevnode.rms.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserId(String userId);
}
