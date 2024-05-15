package com.sergio.bakalarka.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDto {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Long amount;
    private String manufacturer;
    private String category;
    private String image;
    private Double rating;
    private Double sale;

    public static RowMapper<ProductsDto> getProductsDtoMapper() {
        return (rs, rowNum) -> {
            ProductsDto product = new ProductsDto();
            product.setProductId(rs.getLong("PRODUCTID"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            product.setPrice(rs.getDouble("PRICE"));
            product.setAmount(rs.getLong("AMOUNT"));
            product.setManufacturer(rs.getString("MANUFACTURER"));
            product.setCategory(rs.getString("CATEGORY"));
            product.setImage(rs.getString("IMAGE"));
            product.setRating(rs.getDouble("RATING"));
            product.setSale(rs.getDouble("SALE"));
            return product;
        };
    }
}
