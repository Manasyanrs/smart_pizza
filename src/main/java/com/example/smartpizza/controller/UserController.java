package com.example.smartpizza.controller;


import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.entity.userEntity.User;
import com.example.smartpizza.service.CodeOperatorService;
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
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CodeOperatorService codeOperatorService;

    @GetMapping("/register")
    public String getRequestRegisterPage(ModelMap modelMap,
                                         @ModelAttribute User user,
                                         @ModelAttribute ContactData contactData,
                                         @ModelAttribute Address address) {
        modelMap.addAttribute("maxDate", LocalDate.now());
        modelMap.addAttribute("operatorCodes", codeOperatorService.getAll());
        return "signup";
    }

    @PostMapping("/register")
    public String postRequestRegisterPage(@ModelAttribute @Valid User user, BindingResult userBindingResult,
                                          @ModelAttribute @Valid ContactData contactData, BindingResult phoneBindingResult,
                                          @ModelAttribute @Valid Address address, BindingResult addressBindingResult,
                                          ModelMap modelMap,
                                          @RequestParam("profileImg") MultipartFile multipartFile,
                                          @RequestParam("operatorCode") String operatorCode,
                                          RedirectAttributes redirectAttributes) throws IOException {

        if (userBindingResult.hasErrors() || phoneBindingResult.hasErrors() || addressBindingResult.hasErrors()) {
            modelMap.addAttribute("operatorCodes", codeOperatorService.getAll());
            return "signup";
        }
        contactData.setPhoneNumber("+374 " + operatorCode + " " + contactData.getPhoneNumber());

        User save = userService.save(user, contactData, address, multipartFile);

        if (save == null) {
            redirectAttributes.addFlashAttribute("msg", "Username or password is exist");
            return "redirect:/user/register";
        }
        return "redirect:/";
    }

    @GetMapping("/verify")
    public String getVerifyUser(@RequestParam("email") String email,
                                @RequestParam("token") UUID token) {

        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty() || user.get().isEnabled()) {
            return "redirect:/";
        }
        if (user.get().getToken().equals(token.toString())) {
            user.get().setEnabled(true);
            user.get().setToken(null);
            userService.saveVerifyData(user.get());
        }
        return "redirect:/";
    }

}
