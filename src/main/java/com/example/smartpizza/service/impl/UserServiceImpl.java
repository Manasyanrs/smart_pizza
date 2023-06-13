package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.entity.userEntity.User;
import com.example.smartpizza.entity.userEntity.UserRole;
import com.example.smartpizza.repository.UserRepository;
import com.example.smartpizza.service.AddressService;
import com.example.smartpizza.service.ContactDataService;
import com.example.smartpizza.service.LoadAndUploadImgService;
import com.example.smartpizza.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoadAndUploadImgService loadAndUploadImgService;
    private final ContactDataService contactDataService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    @Value("${smartPizza.users.avatar.path}")
    private String userAvatarImagePath;


    @Override
    public User save(User user, ContactData contactData, Address address, MultipartFile multipartFile) throws IOException {

        String email = contactData.getEmail();
        Optional<ContactData> findUserEmail = contactDataService.findUserEmail(email);

        if (findUserEmail.isEmpty()) {
            String avatarName = loadAndUploadImgService.uploadImg(userAvatarImagePath, multipartFile);
            user.setAvatar(avatarName);
            user.setUserRole(UserRole.USER);

            Address addressObj = addressService.save(address);
            ContactData userContactData = contactDataService.save(contactData);

            user.setAddress(addressObj);
            user.setContactData(userContactData);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return userRepository.save(user);
        }
        return null;
    }
}
