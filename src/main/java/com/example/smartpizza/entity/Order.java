package com.example.smartpizza.entity;

import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private User user;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> productList;
}
