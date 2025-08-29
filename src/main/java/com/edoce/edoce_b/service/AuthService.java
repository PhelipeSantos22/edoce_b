package com.edoce.edoce_b.service;

import com.edoce.edoce_b.dto.request.RegisterRequest;
import com.edoce.edoce_b.dto.response.RegisterResponse;
import com.edoce.edoce_b.model.Role;
import com.edoce.edoce_b.model.User;
import com.edoce.edoce_b.repository.UserRepository;
import com.edoce.edoce_b.exceptions.UserIsRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public RegisterResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new UserIsRegisteredException("Usuário já registrado.");//409
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .role(Role.SALLER)
                .build();

        userRepository.save(user);
        return new RegisterResponse(true, "Usuário registrado com sucesso");
    }
}
