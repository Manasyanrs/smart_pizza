package com.example.smartpizza.controller;


import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.entity.userEntity.User;
import com.example.smartpizza.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/register")
    public String getRequestRegisterPage(ModelMap modelMap, @ModelAttribute User user, @ModelAttribute ContactData contactData) {
        modelMap.addAttribute("maxDate", LocalDate.now());
        return "signup";
    }

    @PostMapping("/register")
    public String postRequestRegisterPage(@ModelAttribute @Valid User user, BindingResult userBindingResult,
                                          @ModelAttribute @Valid ContactData contactData, BindingResult phoneBindingResult,
                                          @ModelAttribute Address address,
                                          @RequestParam("profileImg") MultipartFile multipartFile,
                                          RedirectAttributes redirectAttributes) throws IOException {

        if (userBindingResult.hasErrors() || phoneBindingResult.hasErrors()) {
            return "signup";
        }

        User save = userService.save(user, contactData, address, multipartFile);

        if (save == null) {
            redirectAttributes.addFlashAttribute("msg", "Username or password is exist");
            return "redirect:/user/register";
        }
        return "redirect:/";
    }

}
