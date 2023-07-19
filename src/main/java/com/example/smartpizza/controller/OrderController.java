package com.example.smartpizza.controller;


import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.entity.Order;
import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.CartService;
import com.example.smartpizza.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping()
    public String saveOrder(@AuthenticationPrincipal CurrentUser currentUser) {

        Optional<Cart> cartByUserId = cartService.getCartByUserId(currentUser.getUser().getId());
        List<Product> products = new ArrayList<>(cartByUserId.get().getCartProducts());
        orderService.save(
                Order.builder()
                        .user(currentUser.getUser())
                        .dateTime(new Date())
                        .productList(products)
                        .build()
        );
        for (Product product : products) {
            cartService.deleteProductById(cartByUserId.get(), product.getId());
        }

        return "redirect:/";
    }

}
