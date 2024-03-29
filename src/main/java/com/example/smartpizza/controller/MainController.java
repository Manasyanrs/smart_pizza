package com.example.smartpizza.controller;

import com.example.smartpizza.entity.productEntity.ProductType;
import com.example.smartpizza.entity.userEntity.User;
import com.example.smartpizza.entity.userEntity.UserRole;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.LoadAndUploadImgService;
import com.example.smartpizza.service.ProductsService;
import com.example.smartpizza.utils.CurrentDirectory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final LoadAndUploadImgService loadAndUploadImgService;
    @Value("${products.images.path}")
    String productImgPath;

    @Value("${smartPizza.users.avatar.path}")
    private String userImgPath;

    private final ProductsService productsService;
    private final CurrentDirectory currentDirectory;

    @GetMapping("/")
    public String getHomePage(ModelMap modelMap) {
        ProductType[] values = ProductType.values();
        modelMap.addAttribute("productTypes", values);
        modelMap.addAttribute("fastFoodList", productsService.searchProductByProductType("FAST_FOOD"));
        modelMap.addAttribute("randomProducts", productsService.takeRandomProducts());
        return "home";
    }

    @GetMapping("/custom_success_login")
    public String customSuccessLogin(@AuthenticationPrincipal CurrentUser currentUser) {

        if (currentUser != null) {
            User user = currentUser.getUser();
            if (user.getUserRole() == UserRole.ADMIN) {
                return "redirect:/admin";
            } else if (user.getUserRole() == UserRole.USER) {
                return "redirect:/";
            } else if (user.getUserRole() == UserRole.COURIER) {
                return "redirect:/courier/delivery";
            } else if (user.getUserRole() == UserRole.MANAGER) {
                return "redirect:/manager";
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/get_product_image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("imageName") String imgName) throws IOException {
        return loadAndUploadImgService.getBytes(currentDirectory.getCurrentDirectory() + productImgPath, imgName);
    }

    @GetMapping(value = "/get_user_image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getUserImage(@RequestParam("imageName") String imgName) throws IOException {
        return loadAndUploadImgService.getBytes(currentDirectory.getCurrentDirectory() + userImgPath, imgName);
    }

}
