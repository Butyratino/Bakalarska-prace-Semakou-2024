package com.sergio.bakalarka.backend.model.entity;

import com.sergio.bakalarka.backend.model.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@NoArgsConstructor
@Data
public class User implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String role;
    private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    // todo: expand with view
    public static RowMapper<User> getUserMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("USERID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setRole(rs.getString("ROLE"));
            return user;
        };
    }

    public UserDto toUserDto() {
        return new UserDto(id, username, role);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}