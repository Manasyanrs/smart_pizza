package com.example.smartpizza.entity;

import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private User user;

    @ManyToMany()
    private List<Product> cartProducts;
}
