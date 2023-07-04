package com.example.smartpizza.controller;

import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;


    @GetMapping("/user/address")
    public String getUserAddress(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Address> addressList = addressService.getAddressesByUserId(currentUser.getUser().getId());
        modelMap.addAttribute("addressList", addressList);
        return "my_address";
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id") int id) {
        addressService.deleteAddressById(id);
        return "redirect:/address/user/address";
    }

    @GetMapping("/update/{id}")
    public String updateUserAddress(@AuthenticationPrincipal CurrentUser currentUser,
                                    @ModelAttribute Address address) {
        addressService.updateAddress(currentUser, address);
        return "redirect:/address/user/address";
    }
}
