package com.edoce.edoce_b.security;

import com.edoce.edoce_b.exceptions.UserIsRegisteredException;
import com.edoce.edoce_b.model.User;
import com.edoce.edoce_b.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserIsRegisteredException("Usuário não encontrado com email: "+ email));
        return new CustomUserDetails(user);
    }
}
