package com.example.smartpizza.security;

import com.example.smartpizza.entity.userEntity.ContactData;
import com.example.smartpizza.repository.ContactDataRepository;
import com.example.smartpizza.repository.UserRepository;
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
    private final ContactDataRepository contactDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ContactData> hasEmail = contactDataRepository.findByEmail(username);
        if (hasEmail.isEmpty()) {
            throw new UsernameNotFoundException("User does not exists");
        }
        return new CurrentUser(userRepository.findUserByContactDataId(hasEmail.get().getId()).get());
    }
}
