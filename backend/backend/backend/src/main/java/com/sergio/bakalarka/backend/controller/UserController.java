package com.sergio.bakalarka.backend.controller;

import com.sergio.bakalarka.backend.model.dto.ChangeRoleRequest;
import com.sergio.bakalarka.backend.model.dto.UserDetailsDto;
import com.sergio.bakalarka.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Define a logger for the UserController class
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable("username") String username){
        return userService.getUserDetailsByUsername(username);
    }
    @GetMapping(value = "/all")
    public List<UserDetailsDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/update/{id}")
    public void updateUserDetails(@PathVariable(value = "id") Integer id, @RequestBody UserDetailsDto userDetails){
        userService.updateUserDetails(id, userDetails);
    }

    @PostMapping(value = "/add/{id}")
    public void addUserDetails(@PathVariable(value = "id") Integer id, @RequestBody UserDetailsDto userDetails){
        userService.addUserDetails(id, userDetails);
    }
//    @PostMapping(value = "/img/{id}")
//    public void updateUserPicture(@PathVariable("id") Integer id, String picture){
//        userService.updateUserPicture(id, picture);
//    }

    @PostMapping(value = "/img/{id}")
    public ResponseEntity<String> updateUserPicture(@PathVariable("id") Integer id, @RequestBody String base64Image) {
        try {
            // Decode the base64 image string to byte[]
            byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Image);

            // Update user picture in the database
            userService.updateUserPicture(id, imageBytes);

            return new ResponseEntity<>("User picture updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions (e.g., invalid base64 format, database errors)
            return new ResponseEntity<>("Failed to update user picture: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable("id") Integer userId) {
        try {
            System.out.println("getUserAvatar func was called !!!");
            // Retrieve the user avatar as a byte array from the database or storage
            byte[] avatarData = userService.getUserAvatarData(userId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(avatarData);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @PostMapping("/img/{id}")
//    public ResponseEntity<String> updateUserPicture(
//            @PathVariable("id") Long userId,
//            @RequestPart("picture") MultipartFile picture) {
//
//        try {
//            // Save the image data to the BinaryContentTable
//            binaryContentService.saveImage(userId, picture);
//
//            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to upload image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping(value = "/admin/changeRole")
    public List<UserDetailsDto> changeUserRole(@RequestBody ChangeRoleRequest request){
        return userService.changeUserRole(request);
    }

    @GetMapping("/calcTotalPayments/{id}")
    public ResponseEntity<Double> calculateTotalPayments(@PathVariable("id") Integer userId) {
        try {
            // Log the received userId
            logger.info("Received request for userId: {}", userId);

            // Calculate total payments for the user
            double totalPayments = userService.calculateTotalPayments(userId);

            // Log the calculated totalPayments
            logger.info("Calculated total payments: {}", totalPayments);

            return ResponseEntity.ok(totalPayments);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.error("Error calculating total payments", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findRichestUser")
    public ResponseEntity<String> findRichestUser() {
        try {
            String richestUsername = userService.findRichestUser();
            return new ResponseEntity<>(richestUsername, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to find the richest user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @GetMapping(value = "/get/{userId}")
//    public ResponseEntity<UserDetailsDto> getUserDetailsById(@PathVariable("userId") Integer userId) {
//        return userService.getUserDetailsById(userId);
//    }

}
