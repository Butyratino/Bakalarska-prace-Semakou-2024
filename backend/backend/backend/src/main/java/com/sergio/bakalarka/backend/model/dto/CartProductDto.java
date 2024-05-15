package com.sergio.bakalarka.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {
    private Long productId;
    private String name;
    private String image;
    private double price;
    private double sale;
    //private int amount;

    public static RowMapper<CartProductDto> getCartProductDtoRowMapper() {
        return (rs, rowNum) -> {
            CartProductDto cartProduct = new CartProductDto();
            cartProduct.setProductId(rs.getLong("PRODUCTID"));
            cartProduct.setName(rs.getString("NAME"));
            cartProduct.setImage(rs.getString("IMAGE"));
            cartProduct.setPrice(rs.getLong("PRICE"));
            cartProduct.setSale(rs.getLong("SALE"));
            //cartProduct.setAmount(rs.getInt("AMOUNT"));
            return cartProduct;
        };
    }
}
