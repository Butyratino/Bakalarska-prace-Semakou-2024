package com.sergio.bakalarka.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private Long orderId;
    private Timestamp orderDate;
    private Double totalPrice;
    private Long userId;
    private Long productId;
    private Long quantity;
    private Double unitPrice;
    private Long orderDetailId;
    private String name;
    private String description;
    private Double price;
    private String manufacturer;
    private String category;


    public static RowMapper<OrderDetailsDto> getOrderDetailsDtoMapper() {
        return (rs, rowNum) -> {
            OrderDetailsDto orderDetails = new OrderDetailsDto();
            orderDetails.setOrderId(rs.getLong("ORDERID"));
            orderDetails.setOrderDate(Timestamp.valueOf(rs.getTimestamp("ORDER_DATE").toLocalDateTime()));
            orderDetails.setTotalPrice(rs.getDouble("TOTAL_PRICE"));
            orderDetails.setUserId(rs.getLong("USERID"));
            orderDetails.setProductId(rs.getLong("PRODUCTID"));
            orderDetails.setQuantity(rs.getLong("QUANTITY"));
            orderDetails.setUnitPrice(rs.getDouble("UNIT_PRICE"));
            orderDetails.setOrderDetailId(rs.getLong("ORDER_DETAILID"));
            orderDetails.setName(rs.getString("NAME"));
            orderDetails.setDescription(rs.getString("DESCRIPTION"));
            orderDetails.setPrice(rs.getDouble("PRICE"));
            orderDetails.setManufacturer(rs.getString("MANUFACTURER"));
            orderDetails.setCategory(rs.getString("CATEGORY"));
            return orderDetails;
        };
    }
}
