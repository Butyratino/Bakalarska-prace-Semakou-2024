package com.sergio.bakalarka.backend.controller;

import com.sergio.bakalarka.backend.model.dto.CartProductDto;
import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import com.sergio.bakalarka.backend.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<ProductsDto>> getCartProducts(@PathVariable Long cartId) {
        List<ProductsDto> cartProducts = cartService.getCartProducts(cartId);
        return new ResponseEntity<>(cartProducts, HttpStatus.OK);
    }

    @PostMapping("/buy/{productId}/{cartId}")
    public ResponseEntity<String> buyProduct(@PathVariable Long productId, @PathVariable Long cartId) {
        boolean isBought = cartService.buyProduct(productId, cartId);
        if (isBought) {
            return new ResponseEntity<>("Product bought successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to buy product", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{productId}/{cartId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, @PathVariable Long cartId) {
        boolean isDeleted = cartService.deleteProduct(productId, cartId);
        if (isDeleted) {
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete product", HttpStatus.BAD_REQUEST);
        }
    }

}
