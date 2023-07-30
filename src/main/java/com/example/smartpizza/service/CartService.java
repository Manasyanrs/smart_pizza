package com.example.smartpizza.service;


import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.entity.CartProduct;

import java.util.List;
import java.util.Optional;

public interface CartService {
    void addCart(Cart cart);

    Optional<Cart> getCartByUserId(int userId);

    double totalCost(Cart cart);

    void deleteProductById(Cart cart, int id);

    void addDeliveryAddressToCart(int checkedAddressId, int currentUserID);

    void payForOrder(int id);

    List<CartProduct> getUserCartProductList(int cartId);
}
