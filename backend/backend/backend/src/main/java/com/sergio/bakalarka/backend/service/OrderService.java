package com.sergio.bakalarka.backend.service;

import com.sergio.bakalarka.backend.model.dto.OrderDetailsDto;
import com.sergio.bakalarka.backend.repository.OrderDetailsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDetailsDao orderDetailsDao;

    public List<OrderDetailsDto> getOrdersByUserId(Long userId) {
        return orderDetailsDao.getOrdersByUserId(userId);
    }
}
