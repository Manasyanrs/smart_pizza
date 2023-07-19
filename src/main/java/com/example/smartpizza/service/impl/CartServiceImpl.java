package com.example.smartpizza.service.impl;


import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.repository.CartRepository;
import com.example.smartpizza.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;


    @Override
    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartByUserId(int userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    public int totalCost(Cart cart) {
        int totalCost = 0;
        for (Product product : cart.getCartProducts()) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    @Override
    public void addProducts(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteProductById(Cart cart, int id) {
        List<Product> products = cart.getCartProducts();
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
                cartRepository.deleteById(id);
                break;
            }
        }
    }


}
