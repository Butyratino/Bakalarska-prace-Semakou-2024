package com.sergio.bakalarka.backend.service;

import com.sergio.bakalarka.backend.model.dto.CartProductDto;
import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import com.sergio.bakalarka.backend.repository.CartDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;
    public List<ProductsDto> getCartProducts(Long cartId) {
        return cartDao.getCartProducts(cartId);
    }


    public boolean buyProduct(Long productId, Long cartId) {
        return cartDao.buyProduct(productId, cartId);
    }

    public boolean deleteProduct(Long productId, Long cartId) {
        return cartDao.deleteProduct(productId, cartId);
    }
}
