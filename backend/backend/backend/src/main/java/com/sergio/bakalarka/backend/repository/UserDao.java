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
import java.sql.PreparedStatement;
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
        String query = "INSERT INTO USERS (USERID, USERNAME, PASSWORD, ROLE) VALUES (?,?,?,?)";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId++);
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            return ps;
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


    public void updateUserDetails(Integer id, UserDetailsDto userDetails) {
        String procedureCall = "{CALL update_user_details (?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        jdbcTemplate.update(
                procedureCall,
                id,
                userDetails.getAvatar(),
                userDetails.getPassword(),
                userDetails.getName(),
                userDetails.getRoleId(),
                userDetails.getHasAvatar(),
                userDetails.getEmail()
        );
    }


    public ResponseEntity<UserDetailsDto> getUserDetailsByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE USERNAME like ?"; //todo
        List<UserDetailsDto> foundUsers = jdbcTemplate.query(query, new Object[]{username}, UserDetailsDto.getUserDetailsDtoMapper());
        if (foundUsers.size() != 1) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(foundUsers.get(0), HttpStatus.OK);
    }

    public void addUserDetails(Integer id, UserDetailsDto userDetails) { //todo
        String query = "insert into contact_details(name, surname, email, city, phone_number, document_number, id_user, img)" +
                "values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, userDetails.getName());
            ps.setString(2, userDetails.getName());
            ps.setString(3, userDetails.getEmail());
            ps.setString(4, userDetails.getName());
            ps.setString(5, userDetails.getName());
            ps.setString(6, userDetails.getName());
            ps.setInt(7, id);
            ps.setString(8, userDetails.getName());
            return ps;
        });
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

    public double calculateTotalPayments(Integer userId) {
        try {
            // Define the OUT parameter for the function result
            SqlParameter outParameter = new SqlOutParameter("result", Types.DOUBLE);

            // Set up the call to the stored function
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withFunctionName("calculateSumOfPayments")
                    .withReturnValue()
                    .declareParameters(outParameter);

            // Execute the function and get the result
            Map<String, Object> result = jdbcCall.execute(userId);

            // Retrieve the result from the Map
            Double totalPayments = (Double) result.get("result");

            // Display the result in a browser dialog window (use logger instead of JOptionPane in a web application)
            logger.info("Total payments for user with ID {}: {}", userId, totalPayments);

            return totalPayments;
        } catch (Exception e) {
            // Handle exceptions
            logger.error("Error calculating total payments", e);
            throw new RuntimeException("Error calculating total payments", e);
        }
    }


    public String findRichestUser() {
        try {
            // Create SimpleJdbcCall for the stored procedure
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("findUserWithHighestPayments")
                    .declareParameters(
                            new SqlOutParameter("p_highestUserId", Types.INTEGER),
                            new SqlOutParameter("p_highestTotalPayments", Types.DECIMAL),
                            new SqlOutParameter("p_richestUsername", Types.VARCHAR)
                    );

            // Execute the stored procedure
            Map<String, Object> result = simpleJdbcCall.execute();

            // Extract the result from the returned map
            Integer highestUserId = (Integer) result.get("p_highestUserId");
            BigDecimal highestTotalPayments = (BigDecimal) result.get("p_highestTotalPayments");  // Use BigDecimal

            String richestUsername = (String) result.get("p_richestUsername");  // Retrieve the value

            logger.info("Richest user is: " + richestUsername + " with total payments: " + highestTotalPayments);

            return richestUsername;
        } catch (Exception e) {
            // Handle exceptions
            logger.error("Error finding richest user", e);
            throw new RuntimeException("Error finding richest user", e);
        }
    }


    // Helper method to retrieve username by user ID
    private String getUsernameByUserId(Integer userId) {
        String query = "SELECT USERNAME FROM USERS WHERE USERID = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, String.class);
    }
}