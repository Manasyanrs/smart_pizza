package com.example.smartpizza.controller;


import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.CartService;
import com.example.smartpizza.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final ProductsService productsService;
    private final CartService cartService;

    @GetMapping("/{id}")
    public String hasUserCart(@PathVariable("id") int id,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            Optional<Cart> cartByUserId = cartService.getCartByUserId(currentUser.getUser().getId());
            if (cartByUserId.isEmpty()) {
                Cart cart = new Cart();
                cart.setUser(currentUser.getUser());
                cartService.addCart(cart);
            }
        }
        return "redirect:/cart/add/" + id;
    }


    @GetMapping("/add/{id}")
    public String saveOrder(@PathVariable("id") int id,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        Product chosenProduct = productsService.searchProductById(id);

        if (currentUser != null) {
            Cart cart = cartService.getCartByUserId(currentUser.getUser().getId()).get();
            List<Product> products = cart.getCartProducts();
            products.add(chosenProduct);
            cartService.addProducts(cart);
        }
        return "redirect:/";
    }


    @GetMapping("/cartProductList")
    public String getOrderList(@AuthenticationPrincipal CurrentUser currentUser,
                               ModelMap modelMap) {
        Cart cart = cartService.getCartByUserId(currentUser.getUser().getId()).get();
        modelMap.addAttribute("productList", cart.getCartProducts());
        int totalCost = cartService.totalCost(cart);
        modelMap.addAttribute("totalCost", totalCost);
        modelMap.addAttribute("currentUser", currentUser);
        return "cartProductList";
    }


    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int id,
                                @AuthenticationPrincipal CurrentUser currentUser) {
        Cart cart = cartService.getCartByUserId(currentUser.getUser().getId()).get();
        cartService.deleteProductById(cart, id);

        return "redirect:/cart/cartProductList";
    }
}
