package com.sergio.bakalarka.backend.controller;

import com.sergio.bakalarka.backend.model.dto.AddToCartDto;
import com.sergio.bakalarka.backend.model.dto.CommentWithUsernameDto;
import com.sergio.bakalarka.backend.model.dto.CommentsDto;
import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import com.sergio.bakalarka.backend.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/all")
    public List<ProductsDto> getAllProducts() {
        List<ProductsDto> products = productsService.getAllProducts();
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsDto> getProductById(@PathVariable Long id) {
        ProductsDto product = productsService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentWithUsernameDto>> getCommentsByProductId(@PathVariable Long id) {
        List<CommentWithUsernameDto> comments = productsService.getCommentsByProductId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/comments/new")
    public ResponseEntity<String> addComment(@RequestBody CommentWithUsernameDto comment) {
        productsService.addComment(comment);
        return new ResponseEntity<>("Comment added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{id}/addToCart")
    public ResponseEntity<String> addToCart(@PathVariable Long id, @RequestBody AddToCartDto addToCartDto) {
        Long userId = addToCartDto.getUserId();
        Long productId = addToCartDto.getProductId();
        productsService.addToCart(userId, productId);
        return new ResponseEntity<>("Product added to cart successfully", HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editProduct(@PathVariable Long id, @RequestBody ProductsDto editedProductDto) {

        System.out.println("Cathed: " + editedProductDto.toString());

        boolean success = productsService.editProduct(id, editedProductDto);
        if (success) {
            return new ResponseEntity<>("Product edited successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to edit product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        try {
            productsService.deleteProductById(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product");
        }
    }
}
