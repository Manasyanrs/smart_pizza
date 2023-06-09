package com.example.smartpizza.entity.userEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String password;
    private String avatar;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private final Date registrationDate = new Date();
    @OneToOne
    private Address address;
    @OneToOne
    private ContactData contactData;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
