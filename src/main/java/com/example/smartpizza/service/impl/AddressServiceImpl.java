package com.example.smartpizza.service.impl;


import com.example.smartpizza.entity.userEntity.Address;
import com.example.smartpizza.repository.AddressRepository;
import com.example.smartpizza.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }
}
