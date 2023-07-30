package com.example.smartpizza.repository;

import com.example.smartpizza.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {

    List<CartProduct> findAllByCartId(int cartId);

    List<CartProduct> findCartProductByCartIdAndProductIdAndOrderStatusIsFalse(int cartId, int productId);

    List<CartProduct> findAllByCartIdAndOrderStatusIsFalse(int cartId);

    List<CartProduct> findCartProductByCartIdAndOrderStatusIsFalse(int cartId);

}
