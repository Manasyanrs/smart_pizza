package com.example.smartpizza.repository;

import com.example.smartpizza.entity.userEntity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> getAddressesByUserId(int id);

}
