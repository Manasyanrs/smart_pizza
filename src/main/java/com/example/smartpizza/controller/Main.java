package com.example.smartpizza.controller;

import com.example.smartpizza.entity.userEntity.User;
import com.example.smartpizza.entity.userEntity.UserRole;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.LoadAndUploadImgService;
import com.example.smartpizza.utils.CurrentDirectory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class Main {
    private final LoadAndUploadImgService loadAndUploadImgService;
    private final CurrentDirectory currentDirectory;
    @Value("${smartPizza.users.avatar.path}")
    private String imgPath;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/customSuccessLogin")
    public String customSuccessLogin(@AuthenticationPrincipal CurrentUser currentUser) {

        if (currentUser != null) {
            User user = currentUser.getUser();
            if (user.getUserRole() == UserRole.ADMIN) {
                return "redirect:/admin";
            } else if (user.getUserRole() == UserRole.USER) {
                return "redirect:/";
            } else if (user.getUserRole() == UserRole.COURIER) {
                return "redirect:/currier";
            } else if (user.getUserRole() == UserRole.MANAGER) {
                return "redirect:/manager";
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/getUserImage",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("imageName") String imgName) throws IOException {
        String imgAbsolutePath = currentDirectory.getCurrentDirectory() + imgPath;
        return loadAndUploadImgService.getBytes(imgAbsolutePath, imgName);
    }

}
