package com.sergio.bakalarka.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {

    private Long commentId;
    private String text;
    private Double rating;
    private Timestamp createdAt;
    private Long userId;
    private Long productId;



    public static RowMapper<CommentsDto> getCommentsDtoMapper() {
        return (rs, rowNum) -> {
            CommentsDto comment = new CommentsDto();
            comment.setCommentId(rs.getLong("COMMENTID"));
            comment.setText(rs.getString("TEXT"));
            comment.setRating(rs.getDouble("RATING")); //Integer
            comment.setCreatedAt(Timestamp.valueOf(rs.getTimestamp("CREATEDAT").toLocalDateTime()));
            comment.setUserId(rs.getLong("USERID"));
            comment.setProductId(rs.getLong("PRODUCTID"));
            return comment;
        };
    }


}
