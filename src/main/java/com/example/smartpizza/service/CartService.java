package com.example.smartpizza.service;


import com.example.smartpizza.entity.Cart;

import java.util.Optional;

public interface CartService {
    void addCart(Cart cart);

    Optional<Cart> getCartByUserId(int userId);

    int totalCost(Cart cart);

    void addProducts(Cart cart);

    void deleteProductById(Cart cart, int id);

}
