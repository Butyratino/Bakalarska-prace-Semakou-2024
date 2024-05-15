package com.sergio.bakalarka.backend.repository;

import com.sergio.bakalarka.backend.model.dto.CommentWithUsernameDto;
import com.sergio.bakalarka.backend.model.dto.ProductsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductsDao {

    private final JdbcTemplate jdbcTemplate;



    public List<ProductsDto> getAllProducts() {
        String query = "SELECT * FROM PRODUCTS ORDER BY PRODUCTID";
        return jdbcTemplate.query(query, ProductsDto.getProductsDtoMapper());
    }

    public ProductsDto getProductById(Long id) {
        String query = "SELECT * FROM Products WHERE ProductID = ?";
        List<ProductsDto> foundProducts = jdbcTemplate.query(query, new Object[]{id}, ProductsDto.getProductsDtoMapper());
        if (foundProducts.size() != 1) {
            throw new DaoException("Product with ID " + id + " not found or not unique");
        }
        return foundProducts.get(0);
    }

    public List<CommentWithUsernameDto> getCommentsByProductId(Long id) {
        String query = "SELECT " +
                "c.COMMENTID, " +
                "c.TEXT, " +
                "c.RATING, " +
                "c.CREATEDAT, " +
                "c.USERID, " +
                "u.USERNAME, " +
                "u.ROLE, " +
                "c.PRODUCTID " +
                "FROM " +
                "Comments c " +
                "JOIN " +
                "Users u ON c.USERID = u.USERID " +
                "WHERE " +
                "c.PRODUCTID = ?";

        //System.out.println("Executing SQL query: " + query + " with parameter: " + id);

        List<CommentWithUsernameDto> comments = jdbcTemplate.query(query, new Object[]{id}, CommentWithUsernameDto.getCommentWithUsernameDtoMapper());

        // Print each received object
//        for (CommentWithUsernameDto comment : comments) {
//            System.out.println("Received comment: " + comment);
//        }



        return comments;
    }


    public void addComment(CommentWithUsernameDto comment) {
        System.out.println("addComment was called!");
        String query = "INSERT INTO Comments (COMMENTID, TEXT, RATING, CREATEDAT, USERID, PRODUCTID) " +
                "VALUES (?, ?, ?, SYSTIMESTAMP, ?, ?)";

        Long nextCommentId = jdbcTemplate.queryForObject("SELECT comment_seq.NEXTVAL FROM DUAL", Long.class);
        System.out.println("nextCommentId is: " + nextCommentId);

        jdbcTemplate.update(query, nextCommentId, comment.getText(), comment.getRating(), comment.getUserId(), comment.getProductId());

        reEvaluateRatings(comment.getProductId());
    }

    public void reEvaluateRatings(double productId) {
        String query = "UPDATE Products " +
                "SET RATING = ( " +
                "    SELECT ROUND(AVG(RATING), 2) AS AVG_RATING " +
                "    FROM Comments " +
                "    WHERE PRODUCTID = ? " +
                "    GROUP BY PRODUCTID " +
                ") " +
                "WHERE PRODUCTID = ?";

        jdbcTemplate.update(query, productId, productId);
    }

    public void addToCart(Long userId, Long productId) {
        String sql = "INSERT INTO PRODUCTS_IN_CARTS (PRODUCT_IN_CARTID, CARTID, PRODUCTID) VALUES (?, ?, ?)";
        Long nextProductInCartId = jdbcTemplate.queryForObject("SELECT product_in_cart_seq.NEXTVAL FROM DUAL", Long.class);
        jdbcTemplate.update(sql, nextProductInCartId, userId, productId);
    }

    public boolean editProduct(Long id, ProductsDto editedProductDto) {
        String query = "UPDATE PRODUCTS " +
                "SET NAME = ?, DESCRIPTION = ?, PRICE = ?, AMOUNT = ?, MANUFACTURER = ?, CATEGORY = ?, IMAGE = ?, RATING = ?, SALE = ? " +
                "WHERE PRODUCTID = ?";

        int rowsAffected = jdbcTemplate.update(query,
                editedProductDto.getName(),
                editedProductDto.getDescription(),
                editedProductDto.getPrice(),
                editedProductDto.getAmount(),
                editedProductDto.getManufacturer(),
                editedProductDto.getCategory(),
                editedProductDto.getImage(),
                editedProductDto.getRating(),
                editedProductDto.getSale(),
                id);

        return rowsAffected > 0;
    }

    public void deleteProductById(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM PRODUCTS_IN_CARTS WHERE PRODUCTID = ?", id);
            jdbcTemplate.update("DELETE FROM Comments WHERE PRODUCTID = ?", id);
            int rowsAffected = jdbcTemplate.update("DELETE FROM PRODUCTS WHERE PRODUCTID = ?", id);
            if (rowsAffected == 0) {
                throw new DaoException("Failed to delete product with ID: " + id + ". Product not found.");
            }
        } catch (Exception e) {
            throw new DaoException("Failed to delete product with ID: " + id);
        }
    }




}
