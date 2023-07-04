package com.example.smartpizza.service.impl;


import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.repository.AddressRepository;
import com.example.smartpizza.security.CurrentUser;
import com.example.smartpizza.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAddressesByUserId(int id) {
        return addressRepository.getAddressesByUserId(id);
    }

    @Override
    public void deleteAddressById(int id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Optional<Address> getAddressById(int id) {
        return addressRepository.findById(id);
    }

    @Override
    public void updateAddress(CurrentUser currentUser, Address address) {
        Optional<Address> byId = addressRepository.findById(address.getId());
        if (byId.isPresent()) {
            if (byId.get().getUser().getId() == currentUser.getUser().getId()) {
                address.setUser(currentUser.getUser());
                addressRepository.save(address);
            }
        }
    }
}
