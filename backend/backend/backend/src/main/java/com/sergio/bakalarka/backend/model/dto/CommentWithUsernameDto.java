package com.sergio.bakalarka.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentWithUsernameDto {

    private Long commentId;
    private String text;
    private Integer rating;
    private Timestamp createdAt;
    private Long userId;
    private String username;
    private String role;
    private Long productId;

    public static RowMapper<CommentWithUsernameDto> getCommentWithUsernameDtoMapper() {
        return (rs, rowNum) -> {
            CommentWithUsernameDto comment = new CommentWithUsernameDto();
            comment.setCommentId(rs.getLong("COMMENTID"));
            comment.setText(rs.getString("TEXT"));
            comment.setRating(rs.getInt("RATING"));
            comment.setCreatedAt(Timestamp.valueOf(rs.getTimestamp("CREATEDAT").toLocalDateTime()));
            comment.setUserId(rs.getLong("USERID"));
            comment.setUsername(rs.getString("USERNAME"));
            comment.setRole(rs.getString("ROLE"));
            comment.setProductId(rs.getLong("PRODUCTID"));
            //System.out.println("Mapped row to CommentWithUsernameDto: " + comment);
            return comment;
        };
    }


}
