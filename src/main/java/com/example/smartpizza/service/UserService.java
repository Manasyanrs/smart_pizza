package com.example.smartpizza.service;

import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.entity.userEntity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    User save(User user, ContactData contactData, Address address, MultipartFile multipartFile) throws IOException;

    Optional<User> getUserByEmail(String email);

    void saveVerifyData(User user);
}
