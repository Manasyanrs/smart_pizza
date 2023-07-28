package com.example.smartpizza.controller;


import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.entity.Order;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.AddressService;
import com.example.smartpizza.service.CartService;
import com.example.smartpizza.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final AddressService addressService;

    @GetMapping()
    public String getOrderPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {

        Order order = orderService.getOrderByUserId(currentUser.getUser().getId()).get();
        modelMap.addAttribute("order", order);
        Optional<Cart> cartByUserId = cartService.getCartByUserId(currentUser.getUser().getId());
        modelMap.addAttribute("totalCoast", cartService.totalCost(cartByUserId.get()));

        return "checkout";
    }

    @GetMapping("/to_pay")
    public String payToOrder(@AuthenticationPrincipal CurrentUser currentUser) {
        cartService.payForOrder(currentUser.getUser().getId());

        return "redirect:/";
    }

}
