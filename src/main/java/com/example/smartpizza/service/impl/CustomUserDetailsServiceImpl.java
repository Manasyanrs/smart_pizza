package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.userEntity.User;
import com.example.smartpizza.repository.UserRepository;
import com.example.smartpizza.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findUserByContactDataEmail(username);
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException("User does not exists");
        }

        return new CurrentUser(byEmail.get());
    }
}
