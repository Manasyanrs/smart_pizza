package com.example.smartpizza.service;


import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.security.CurrentUser;

import java.util.Optional;

public interface CartService {
    void addCart(Cart cart);

    Optional<Cart> getCartByUserId(int userId);

    double totalCost(Cart cart);

    void deleteProductById(Cart cart, int id);

    void createOrder(int checkedAddressId, CurrentUser currentUser);

    void payForOrder(int id);

}
