package com.sergio.bakalarka.backend.model.dto;

import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private Long userId;
    private String username;
    private String role;
    private String email;
    private String phoneNumber;
    private String password;
    private Timestamp registrationDate;
    private Double balance;
    private String avatar;
    private Integer hasAvatar;
    private Long cartId;

    // Constructor with username parameter
    public UserDetailsDto(String username) {
        this.username = username;
    }

    // Constructor with additional parameters
    public UserDetailsDto(Long userId, String username, String role, String avatar) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.avatar = avatar;
    }




    // RowMapper method
    public static RowMapper<UserDetailsDto> getUserDetailsDtoMapper() {
        return (rs, rowNum) -> {
            UserDetailsDto user = new UserDetailsDto();
            user.setUserId(rs.getLong("USERID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setRole(rs.getString("ROLE"));
            user.setEmail(rs.getString("EMAIL"));
            user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setRegistrationDate(rs.getTimestamp("REGISTRATION_DATE"));
            user.setBalance(rs.getDouble("BALANCE"));
            user.setAvatar(rs.getString("AVATAR"));
            user.setHasAvatar(rs.getInt("HASAVATAR"));
            user.setCartId(rs.getLong("CARTID"));
            return user;
        };
    }
}