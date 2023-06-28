package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.util.PhoneOperatorCode;
import com.example.smartpizza.repository.util.CodeOperatorRepository;
import com.example.smartpizza.service.CodeOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeOperatorServiceImpl implements CodeOperatorService {
    private final CodeOperatorRepository codeOperatorRepository;

    @Override
    public List<PhoneOperatorCode> getAll() {
        return codeOperatorRepository.findAll();
    }
}
