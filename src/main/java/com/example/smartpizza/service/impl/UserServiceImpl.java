package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.entity.userEntity.User;
import com.example.smartpizza.entity.userEntity.UserRole;
import com.example.smartpizza.repository.UserRepository;
import com.example.smartpizza.service.*;
import com.example.smartpizza.utils.CurrentDirectory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoadAndUploadImgService loadAndUploadImgService;
    private final ContactDataService contactDataService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final CurrentDirectory currentDirectory;

    @Value("${smartPizza.users.avatar.path}")
    private String userAvatarImagePath;

    @Value("${application.url}")
    private String applicationUrl;


    @Override
    public User save(User user, ContactData contactData, Address address, MultipartFile multipartFile) throws IOException {

        String email = contactData.getEmail();
        Optional<ContactData> findUserEmail = contactDataService.findUserEmail(email);

        if (findUserEmail.isEmpty()) {
            String avatarName = loadAndUploadImgService.uploadImg(
                    currentDirectory.getCurrentDirectory() + userAvatarImagePath, multipartFile);
            user.setAvatar(avatarName);
            user.setUserRole(UserRole.USER);

            Address addressObj = addressService.save(address);
            ContactData userContactData = contactDataService.save(contactData);

            user.setAddress(addressObj);
            user.setContactData(userContactData);
            user.setEnabled(false);
            user.setToken(UUID.randomUUID().toString());

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            mailService.sendOrderCheck(userContactData.getEmail(),
                    "Verify account", "\nPlease click url and verify your account.\n" +
                            applicationUrl + "/user/verify?email=" + user.getContactData().getEmail() +
                            "&token=" + user.getToken());
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByContactDataEmail(email);
    }

    @Override
    public void saveVerifyData(User user) {
        userRepository.save(user);
    }
}
