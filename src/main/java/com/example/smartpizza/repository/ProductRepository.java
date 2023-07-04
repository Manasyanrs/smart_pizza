package com.example.smartpizza.repository;

import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.entity.productEntity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;



public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * from products where product_type != 'FAST_FOOD'order by rand() limit 12",nativeQuery = true)
    List<Product> takeRandomProduct();

    List<Product> findProductsByProductType(ProductType productType);

    Product findProductsById(int id);

    Page<Product> findProductsByProductType(ProductType productType, Pageable pageable);




}
