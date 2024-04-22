package com.sergio.bakalarka.backend.repository;

import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductsDao {

    private final JdbcTemplate jdbcTemplate;

    public List<ProductsDto> getAllProducts() {
        String query = "SELECT * FROM PRODUCTS ORDER BY PRODUCTID";
        return jdbcTemplate.query(query, ProductsDto.getProductsDtoMapper());
    }
}
