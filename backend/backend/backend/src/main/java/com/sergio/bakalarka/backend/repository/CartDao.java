package com.sergio.bakalarka.backend.repository;

import com.sergio.bakalarka.backend.model.dto.CartProductDto;
import com.sergio.bakalarka.backend.model.dto.CommentWithUsernameDto;
import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public List<ProductsDto> getCartProducts(Long cartId) {
        String query = "SELECT p.PRODUCTID, p.NAME, p.DESCRIPTION, p.RATING, p.MANUFACTURER, " +
                "p.CATEGORY, p.IMAGE, p.PRICE, p.SALE, p.AMOUNT " +
                "FROM PRODUCTS_IN_CARTS c " +
                "JOIN PRODUCTS p ON c.PRODUCTID = p.PRODUCTID " +
                "WHERE c.CARTID = ?";

        return jdbcTemplate.query(query, ProductsDto.getProductsDtoMapper(), cartId);
    }


    public boolean buyProduct(Long productId, Long cartId) {
        String deleteQuery = "DELETE FROM PRODUCTS_IN_CARTS WHERE CARTID = ? AND PRODUCTID = ?";
        String updateBalance = "UPDATE USERS " +
                "SET BALANCE = BALANCE - (SELECT p.PRICE * (100 - p.SALE) / 100 " +
                "FROM PRODUCTS p WHERE p.PRODUCTID = ?) " +
                "WHERE USERID = ?";

        Long price = jdbcTemplate.queryForObject(
                "SELECT PRICE FROM PRODUCTS WHERE PRODUCTID = ?",
                Long.class,
                productId
        );

        Long nextOrderId = jdbcTemplate.queryForObject("SELECT order_seq.NEXTVAL FROM DUAL", Long.class);
        Long nextOrderDetailId = jdbcTemplate.queryForObject("SELECT order_details_seq.NEXTVAL FROM DUAL", Long.class);

        String createOrder = "INSERT INTO ORDERS (ORDERID, ORDER_DATE, TOTAL_PRICE, USERID) " +
                "VALUES (?, SYSTIMESTAMP, ?, ?)";
        String insertOrderDetails = "INSERT INTO ORDER_DETAILS (ORDER_DETAILID, ORDERID, PRODUCTID, QUANTITY, UNIT_PRICE) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(createOrder, nextOrderId, price, cartId);
        jdbcTemplate.update(insertOrderDetails, nextOrderDetailId, nextOrderId, productId, 1, price * 1);


        int rowsAffected = jdbcTemplate.update(deleteQuery, cartId, productId);
        jdbcTemplate.update(updateBalance, productId, cartId);

        return rowsAffected > 0;
    }


    public boolean deleteProduct(Long productId, Long cartId) {
        String deleteQuery = "DELETE FROM PRODUCTS_IN_CARTS WHERE cartid = ? AND productid = ?";
        int rowsAffected = jdbcTemplate.update(deleteQuery, cartId, productId);
        return rowsAffected > 0;
    }
}
