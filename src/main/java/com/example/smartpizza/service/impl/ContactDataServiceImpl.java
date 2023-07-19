package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.repository.ContactDataRepository;
import com.example.smartpizza.service.ContactDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactDataServiceImpl implements ContactDataService {
    private final ContactDataRepository contactDataRepository;


    @Override
    public ContactData save(ContactData contactData) {
        return contactDataRepository.save(contactData);
    }

    @Override
    public Optional<ContactData> findUserEmail(String email) {
        return contactDataRepository.findByEmail(email);
    }
}
