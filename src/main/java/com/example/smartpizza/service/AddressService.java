package com.example.smartpizza.service;

import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.security.CurrentUser;

import java.util.List;

public interface AddressService {
    Address save(Address address);

    List<Address> getAddressesByUserId(int id);

    void deleteAddressById(int id);

//    Optional<Address> getAddressById(int id);

    void updateAddress(CurrentUser currentUser, Address address);

    void updateOrAddNewAddress(CurrentUser currentUser, Address address);
}
