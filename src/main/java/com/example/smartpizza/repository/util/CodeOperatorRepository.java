package com.example.smartpizza.repository.util;

import com.example.smartpizza.entity.util.PhoneOperatorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeOperatorRepository extends JpaRepository<PhoneOperatorCode, Integer> {
}
