package com.sergio.bakalarka.backend.service;

import com.sergio.bakalarka.backend.model.dto.ChangeRoleRequest;
import com.sergio.bakalarka.backend.model.dto.RegistrationUserRequest;
import com.sergio.bakalarka.backend.model.dto.UserDetailsDto;
import com.sergio.bakalarka.backend.model.dto.UserDto;
import com.sergio.bakalarka.backend.model.entity.User;
import com.sergio.bakalarka.backend.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;
    public void registration(RegistrationUserRequest user) {
        if(userDao.checkIsUserExistByUsername(user.getUsername())){
            throw new RuntimeException("Choose another username. This username already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addNewUser(user);
    }

    public UserDto getUserById(Integer userId) {
        return userDao.getUserById(userId).toUserDto();
    }

    public List<UserDetailsDto> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public ResponseEntity<UserDetailsDto> getUserDetailsByUsername(String username) {
        return userDao.getUserDetailsByUsername(username);
    }

    public void updateUserPicture(Integer id, byte[] picture) {
        try {
            // Additional business logic, if needed
            userDao.updateUserPicture(id, picture);
        } catch (Exception e) {
            // Log or handle exceptions specific to the service layer
            throw new ServiceException("Failed to update user picture", e);
        }
    }

    public void updateUserDetails(Integer id, UserDetailsDto userDetails) {
        userDao.updateUserDetails(id, userDetails);
    }

    public List<UserDetailsDto> changeUserRole(ChangeRoleRequest request) {
        return userDao.changeUserRole(request);
    }

    public String getUserAvatarUrl(Integer userId) {
        return userDao.getUserAvatarUrl(userId);
    }

    public byte[] getUserAvatarData(Integer userId) {
        return userDao.getUserAvatarData(userId);
    }

}
