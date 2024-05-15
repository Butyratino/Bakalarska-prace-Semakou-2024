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

    @PutMapping(value = "/update/{id}")
    public void updateUserDetails(@PathVariable(value = "id") Integer id, @RequestBody UserDetailsDto userDetails){
        userService.updateUserDetails(id, userDetails);
    }


    @PostMapping(value = "/img/{id}")
    public ResponseEntity<String> updateUserPicture(@PathVariable("id") Integer id, @RequestBody String base64Image) {
        try {

            byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Image);


            userService.updateUserPicture(id, imageBytes);

            return new ResponseEntity<>("User picture updated successfully", HttpStatus.OK);
        } catch (Exception e) {

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


    @PostMapping(value = "/admin/changeRole")
    public List<UserDetailsDto> changeUserRole(@RequestBody ChangeRoleRequest request){
        return userService.changeUserRole(request);
    }

}
