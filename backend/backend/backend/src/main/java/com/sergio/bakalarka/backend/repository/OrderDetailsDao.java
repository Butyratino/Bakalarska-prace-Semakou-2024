package com.sergio.bakalarka.backend.repository;

import com.sergio.bakalarka.backend.model.dto.OrderDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public List<OrderDetailsDto> getOrdersByUserId(Long userId) {
        String query = "SELECT * " +
                "FROM ORDERS o " +
                "JOIN ORDER_DETAILS od ON o.ORDERID = od.ORDERID " +
                "JOIN PRODUCTS p ON od.PRODUCTID = p.PRODUCTID " +
                "WHERE o.USERID = ?";

        List<OrderDetailsDto> list = jdbcTemplate.query(query, new Object[]{userId}, OrderDetailsDto.getOrderDetailsDtoMapper());
        return list;
    }
}
