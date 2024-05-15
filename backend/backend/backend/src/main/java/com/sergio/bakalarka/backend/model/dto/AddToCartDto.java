package com.sergio.bakalarka.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {
    private Long userId;
    private Long productId;

    public static RowMapper<AddToCartDto> getAddToCartDtoMapper() {
        return (rs, rowNum) -> {
            AddToCartDto addToCart = new AddToCartDto();
            addToCart.setUserId(rs.getLong("USERID"));
            addToCart.setProductId(rs.getLong("PRODUCTID"));
            return addToCart;
        };
    }
}
