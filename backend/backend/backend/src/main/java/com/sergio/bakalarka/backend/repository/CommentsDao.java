package com.sergio.bakalarka.backend.repository;

import com.sergio.bakalarka.backend.model.dto.CommentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentsDao {

    private final JdbcTemplate jdbcTemplate;

    public List<CommentsDto> getAllComments() {
        String query = "SELECT * FROM COMMENTS ORDER BY COMMENTID";
        return jdbcTemplate.query(query, CommentsDto.getCommentsDtoMapper());
    }

    public List<CommentsDto> getCommentsByProductId(Long productId) {
        String query = "SELECT * FROM COMMENTS WHERE PRODUCTID = ?";
        return jdbcTemplate.query(query, new Object[]{productId}, CommentsDto.getCommentsDtoMapper());
    }

}
