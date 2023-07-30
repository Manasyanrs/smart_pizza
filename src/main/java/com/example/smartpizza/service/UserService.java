package com.example.smartpizza.service;

import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.entity.userEntity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    int userListSize();

    User save(User user, ContactData contactData, Address address, MultipartFile multipartFile) throws IOException;

    Optional<User> getUserByEmail(String email);

    void saveVerifyData(User user);

    String updateUserData(int id, String name, String surname, String operatorCode, String phoneNumber);

    void saveUserObject(User user);

    void deleteUserById(int id);

    User getUserById(int id);
    void saveUser(User user);
}
