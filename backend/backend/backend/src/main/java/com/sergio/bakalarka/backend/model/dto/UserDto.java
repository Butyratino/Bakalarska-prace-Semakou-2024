package com.sergio.bakalarka.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String username;
    private String role;

    public static RowMapper<UserDto> getUserDtoMapper() {
        return (rs, rowNum) -> {
            UserDto user = new UserDto();
            user.setId(rs.getInt("USERID"));
            user.setUsername(rs.getString("LOGIN"));
            user.setRole(rs.getString("ROLE"));
            return user;
        };
    }
}