package com.sergio.bakalarka.backend.service;

import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import com.sergio.bakalarka.backend.repository.ProductsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsDao productsDao;

    public List<ProductsDto> getAllProducts() {
        return productsDao.getAllProducts();
    }
}
