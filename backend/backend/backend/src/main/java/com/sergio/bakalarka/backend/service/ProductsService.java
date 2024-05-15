package com.sergio.bakalarka.backend.service;

import com.sergio.bakalarka.backend.model.dto.CommentWithUsernameDto;
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

    public ProductsDto getProductById(Long id) {
        return productsDao.getProductById(id);
    }

    public List<CommentWithUsernameDto> getCommentsByProductId(Long id) {
        return productsDao.getCommentsByProductId(id);
    }

    public void addComment(CommentWithUsernameDto comment) {
        productsDao.addComment(comment);
    }

    public void addToCart(Long userId, Long productId) {
        productsDao.addToCart(userId, productId);
    }

    public boolean editProduct(Long id, ProductsDto editedProductDto) {
        return productsDao.editProduct(id, editedProductDto);
    }

    public void deleteProductById(Long id) {
        productsDao.deleteProductById(id);
    }
}
