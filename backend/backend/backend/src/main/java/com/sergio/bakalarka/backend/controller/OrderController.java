package com.sergio.bakalarka.backend.controller;

import com.sergio.bakalarka.backend.model.dto.OrderDetailsDto;
import com.sergio.bakalarka.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDetailsDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDetailsDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

}
