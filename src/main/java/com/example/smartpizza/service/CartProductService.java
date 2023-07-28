package com.example.smartpizza.service;

public interface CartProductService {
    void addProductToCart(int productId, int userId);

//    List<CartProduct> getProductsByCartId(Optional<Cart> cartByUserId);
}
