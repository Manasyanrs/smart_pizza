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
        List<CartProduct> allByCartId = cartProductRepository.findAllByCartIdAndOrderStatusIsFalse(cart.getId());
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
    public void addDeliveryAddressToCart(int checkedAddressId, int currentUserId) {
        Optional<Address> addressById = addressRepository.getAddressById(checkedAddressId);
        Optional<Cart> cartByUserId = cartRepository.findCartByUserId(currentUserId);

        if (cartByUserId.isPresent()) {
            List<CartProduct> allByCartId = cartProductRepository.findAllByCartId(cartByUserId.get().getId());
            if (addressById.isPresent()) {
                for (CartProduct cartProduct : allByCartId) {
                    cartProduct.setDeliveryAddress(addressById.get());
                }
            }
            cartProductRepository.saveAllAndFlush(allByCartId);
        }
    }

    @Override
    public void payForOrder(int id) {
        Optional<Cart> cartByUserId = cartRepository.findCartByUserId(id);
        if (cartByUserId.isPresent()) {
            Cart cart = cartByUserId.get();
            List<CartProduct> cartProductByCartIdAndOrderStatusIsFalse = cartProductRepository.findCartProductByCartIdAndOrderStatusIsFalse(cart.getId());
            Order order = Order.builder()
                    .user(cartByUserId.get().getUser())
                    .deliveryAddress(cartByUserId.get().getCartProducts().get(0).getDeliveryAddress())
                    .dateTime(new Date())
                    .cartProduct(cartProductByCartIdAndOrderStatusIsFalse)
                    .isPaymentDone(false)
                    .orderStatus(OrderStatus.UNDELIVERED)
                    .orderTotalCost(totalCost(cart))
                    .build();
            orderRepository.save(order);

            for (CartProduct cartProduct : cartProductByCartIdAndOrderStatusIsFalse) {
                cartProduct.setOrderStatus(true);

            }
            cartProductRepository.saveAllAndFlush(cartProductByCartIdAndOrderStatusIsFalse);
        }
    }

    @Override
    public List<CartProduct> getUserCartProductList(int cartId) {
        return cartProductRepository.findAllByCartIdAndOrderStatusIsFalse(cartId);
    }

}
