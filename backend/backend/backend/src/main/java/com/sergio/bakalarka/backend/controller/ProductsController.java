package com.sergio.bakalarka.backend.controller;

import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import com.sergio.bakalarka.backend.repository.ProductsDao;
import com.sergio.bakalarka.backend.service.ProductsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/all")
    public List<ProductsDto> getAllAttractions() {
        List<ProductsDto> attractions = productsService.getAllProducts();
        return attractions;
    }

}
