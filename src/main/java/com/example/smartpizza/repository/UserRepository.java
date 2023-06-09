package com.example.smartpizza.repository;

import com.example.smartpizza.entity.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByContactDataEmail(String email);
}
