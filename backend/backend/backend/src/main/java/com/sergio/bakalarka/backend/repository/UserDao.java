package com.sergio.bakalarka.backend.repository;

import com.sergio.bakalarka.backend.controller.UserController;
import com.sergio.bakalarka.backend.model.dto.ChangeRoleRequest;
import com.sergio.bakalarka.backend.model.dto.RegistrationUserRequest;
import com.sergio.bakalarka.backend.model.dto.UserDetailsDto;
import com.sergio.bakalarka.backend.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private static Integer userId = 1;


    private final JdbcTemplate jdbcTemplate;

    public User getUserById(Integer id) {
        String query = "SELECT * FROM USERS WHERE USERID = ?";
        List<User> foundUsers = jdbcTemplate.query(query, new Object[]{id}, User.getUserMapper());
        if (foundUsers.size() != 1) {
            throw new DaoException("User with ID " + id + " not found");
        }
        return foundUsers.get(0);
    }

    public List<UserDetailsDto> getAllUsers() {
        String query = "SELECT * FROM USERS ORDER BY USERID";
        List<UserDetailsDto> foundUsers = jdbcTemplate.query(query, UserDetailsDto.getUserDetailsDtoMapper());
        return foundUsers;
    }

    public void addNewUser(RegistrationUserRequest user) {
        String userQuery = "INSERT INTO USERS (USERID, USERNAME, PASSWORD, ROLE, CARTID, REGISTRATION_DATE) " +
                "VALUES (?,?,?,?,?,CURRENT_TIMESTAMP)";

        String cartQuery = "INSERT INTO CARTS (CARTID, AMOUNT) VALUES (?,?)";

        Long nextUserId = jdbcTemplate.queryForObject("SELECT user_id_sequence.NEXTVAL FROM DUAL", Long.class);
        System.out.println("XXXXXXXX " + nextUserId);
        jdbcTemplate.update(cartQuery, ps -> {
            ps.setLong(1, nextUserId);
            ps.setLong(2, 0);
        });


        jdbcTemplate.update(userQuery, ps -> {
            ps.setLong(1, nextUserId);
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setLong(5, nextUserId);
        });
    }
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE USERNAME like ?";
        List<User> foundUsers = jdbcTemplate.query(query, new Object[]{username}, User.getUserMapper());
        if (foundUsers.size() != 1) {
            throw new RuntimeException("User with username " + username + " not found");
        }
        return foundUsers.get(0);
    }

    public void updateUserDetails(Integer id, UserDetailsDto user) {
        String sql = "UPDATE USERS " +
                "SET EMAIL = ?, " +
                "USERNAME = ?, " +
                "PHONE_NUMBER = ?, " +
                "BALANCE = ?, " +
                "ROLE = ? " +
                "WHERE USERID = ?";

        jdbcTemplate.update(sql, user.getEmail(), user.getUsername(), user.getPhoneNumber(), user.getBalance(), user.getRole(), id);

        System.out.println("User details edited: " + user.toString());
        System.out.println("XZX " + id);
    }

    public ResponseEntity<UserDetailsDto> getUserDetailsByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE USERNAME like ?"; //todo
        List<UserDetailsDto> foundUsers = jdbcTemplate.query(query, new Object[]{username}, UserDetailsDto.getUserDetailsDtoMapper());
        if (foundUsers.size() != 1) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(foundUsers.get(0), HttpStatus.OK);
    }

    public List<UserDetailsDto> changeUserRole(ChangeRoleRequest request) {
        String query = "UPDATE USERS SET ROLE = ? WHERE USERID = ?";
        jdbcTemplate.update(query, request.getRole().equals("admin") ? "admin" : "user", request.getIdUser());
        return getAllUsers();
    }

    public boolean checkIsUserExistByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE USERNAME like ?";
        List<User> foundUsers = jdbcTemplate.query(query, new Object[]{username}, User.getUserMapper());
        return foundUsers.size() > 0;
    }


    public void updateUserPicture(Integer id, byte[] picture) {
        String query = "UPDATE USERS SET AVATAR = ? WHERE USERID = ?";
        jdbcTemplate.update(query, picture, id);
    }


    public String getUserAvatarUrl(Integer userId) {
        System.out.println("DAO getUserAvatarUrl was cococolled!");
        String query = "SELECT AVATAR FROM USERS WHERE USERID = ?";
        List<byte[]> avatarBytesList = jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> rs.getBytes("AVATAR"));

        if (!avatarBytesList.isEmpty()) {
            // Convert the byte array to a Base64-encoded string if needed
            return Base64.getEncoder().encodeToString(avatarBytesList.get(0));
        } else {
            // Handle case when user is not found or avatar is null
            return null; // Or throw an exception if you prefer
        }
    }

    public byte[] getUserAvatarData(Integer userId) {
        System.out.println("DAO getUserAvatarData was called!");
        String query = "SELECT AVATAR FROM USERS WHERE USERID = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, byte[].class);
    }

    private String getUsernameByUserId(Integer userId) {
        String query = "SELECT USERNAME FROM USERS WHERE USERID = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, String.class);
    }
}