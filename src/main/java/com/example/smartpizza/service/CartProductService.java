package com.example.smartpizza.service;

import com.example.smartpizza.entity.CartProduct;
import com.example.smartpizza.security.CurrentUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartProductService {
    void addProductToCart(int productId, int userId);

//    List<CartProduct> getProductsByCartId(Optional<Cart> cartByUserId);
}
