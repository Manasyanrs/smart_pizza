package com.example.smartpizza.repository;

import com.example.smartpizza.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {

    List<CartProduct> findAllByCartId(int cartId);

    Optional<CartProduct> findCartProductByCartIdAndProductId(int cartId, int productId);

    @Transactional
    void removeByCartId(int cartId);

}
