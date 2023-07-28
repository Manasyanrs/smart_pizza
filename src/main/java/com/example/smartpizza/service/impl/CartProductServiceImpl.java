package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.Cart;
import com.example.smartpizza.entity.CartProduct;
import com.example.smartpizza.entity.Order;
import com.example.smartpizza.entity.OrderStatus;
import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.repository.*;
import com.example.smartpizza.service.CartProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements CartProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public void addProductToCart(int productId, int userId) {
        Product chosenProduct = productRepository.findById(productId).get();
        Cart cart = cartRepository.findCartByUserId(userId).get();
        List<CartProduct> products = cart.getCartProducts();
        Optional<CartProduct> cartProductByCartIdAndProductId = cartProductRepository.findCartProductByCartIdAndProductId(cart.getId(), productId);
        if (cartProductByCartIdAndProductId.isPresent()) {
            cartProductByCartIdAndProductId.get().setCountProduct(cartProductByCartIdAndProductId.get().getCountProduct() + 1);
            cartProductByCartIdAndProductId.get().setOrder(orderRepository.findByUserId(userId).get());
            cartProductRepository.save(cartProductByCartIdAndProductId.get());
        } else {

            Optional<Order> byUserId = orderRepository.findByUserId(userId);

            if (byUserId.isEmpty()){
                Order order = Order.builder()
                        .id(1)
                        .user(userRepository.findUserById(userId).get())
                        .deliveryAddress(addressRepository.getAddressByUserId(userId).get())
                        .dateTime(new Date())
                        .isPaymentDone(false)
                        .orderStatus(OrderStatus.UNDELIVERD)
                        .build();
                orderRepository.save(order);
            }
            CartProduct build = CartProduct.builder()
                    .cart(cart)
                    .product(chosenProduct)
                    .countProduct(1)
                    .order(orderRepository.findOrderByUserId(userId).get())
                    .build();
            products.add(build);
            cartProductRepository.save(build);
        }
    }

}
