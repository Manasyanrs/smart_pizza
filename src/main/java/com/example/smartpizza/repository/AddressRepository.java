package com.example.smartpizza.repository;

import com.example.smartpizza.entity.userEntity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> getAddressesByUserId(int id);

    Optional<Address> getAddressById(int addressId);
}
