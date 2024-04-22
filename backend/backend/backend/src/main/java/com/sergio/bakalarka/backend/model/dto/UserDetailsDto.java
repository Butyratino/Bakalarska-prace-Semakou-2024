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
    private String name;
    private byte[] avatar;
    private String password;
    private Integer hasAvatar;
    private String email;
    private String phoneNumber;
    private Timestamp registrationDate;
    private Double balance;
    private Long roleId;
    private Long cartId;

    // Constructor with username parameter
    public UserDetailsDto(String username) {
        this.name = username;
    }

    // Constructor with additional parameters
    public UserDetailsDto(Long userId, String username, Long roleId, String avatar) {
        this.userId = userId;
        this.name = username;
        this.roleId = roleId;
        // Convert the avatar URL to byte[]
        this.avatar = (avatar != null) ? avatar.getBytes() : null;
    }

    // RowMapper method
    public static RowMapper<UserDetailsDto> getUserDetailsDtoMapper() {
        return (rs, rowNum) -> {
            UserDetailsDto user = new UserDetailsDto();
            user.setUserId(rs.getLong("USERID"));
            user.setName(rs.getString("USERNAME"));
            user.setRoleId(rs.getLong("ROLE"));
            user.setAvatar(rs.getBytes("AVATAR"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setHasAvatar(rs.getInt("HASAVATAR"));
            user.setEmail(rs.getString("EMAIL"));
            user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            user.setRegistrationDate(rs.getTimestamp("REGISTRATION_DATE"));
            user.setBalance(rs.getDouble("BALANCE"));
            user.setRoleId(rs.getLong("ROLEID"));
            user.setCartId(rs.getLong("CARTID"));
            return user;
        };
    }
}
