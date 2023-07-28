package com.example.smartpizza.service.impl;


import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.entity.CartProduct;
import com.example.smartpizza.entity.Order;
import com.example.smartpizza.entity.OrderStatus;
import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.repository.AddressRepository;
import com.example.smartpizza.repository.CartProductRepository;
import com.example.smartpizza.repository.CartRepository;
import com.example.smartpizza.repository.OrderRepository;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;


    @Override
    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartByUserId(int userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    public double totalCost(Cart cart) {
        List<CartProduct> allByCartId = cartProductRepository.findAllByCartId(cart.getId());
        double totalCost = 0;
        if (!allByCartId.isEmpty()) {
            for (CartProduct cartProduct : allByCartId) {
                totalCost += cartProduct.getProduct().getPrice() * cartProduct.getCountProduct();
            }
        }
        return totalCost;
    }

    @Override
    public void deleteProductById(Cart cart, int id) {
        List<CartProduct> products = cart.getCartProducts();
        for (CartProduct product : products) {
            if (product.getProduct().getId() == id) {
                products.remove(product);
                cartRepository.save(cart);
                cartProductRepository.deleteById(product.getId());
                break;
            }
        }
    }

    @Override
    public void createOrder(int checkedAddressId, CurrentUser currentUser) {
        Optional<Order> orderByUserId = orderRepository.findByUserId(currentUser.getUser().getId());
        Address deliveryAddress = addressRepository.getById(checkedAddressId);

        if (orderByUserId.isEmpty()) {
            int userId = currentUser.getUser().getId();
            Cart cart = cartRepository.findCartByUserId(userId).get();
            List<CartProduct> allByCartId = cartProductRepository.findAllByCartId(cart.getId());
            Order order = Order.builder()
                    .user(currentUser.getUser())
                    .deliveryAddress(deliveryAddress)
                    .dateTime(new Date())
                    .cartProduct(allByCartId)
                    .isPaymentDone(false)
                    .orderStatus(OrderStatus.UNDELIVERD)
                    .build();
            orderRepository.save(order);
        } else {
            orderByUserId.get().setDeliveryAddress(deliveryAddress);
            orderRepository.save(orderByUserId.get());
        }
    }

    @Override
    public void payForOrder(int id) {
        Cart cart = cartRepository.findCartByUserId(id).get();
        cartProductRepository.removeByCartId(cart.getId());
    }

}
