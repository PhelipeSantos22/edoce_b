package com.edoce.edoce_b.service;

import com.edoce.edoce_b.dto.request.LoginRequest;
import com.edoce.edoce_b.dto.request.RegisterRequest;
import com.edoce.edoce_b.dto.response.LoginResponse;
import com.edoce.edoce_b.dto.response.RegisterResponse;
import com.edoce.edoce_b.exceptions.InvalidEmailOrPasswordException;
import com.edoce.edoce_b.exceptions.InvalidFieldsException;
import com.edoce.edoce_b.model.Role;
import com.edoce.edoce_b.model.User;
import com.edoce.edoce_b.repository.UserRepository;
import com.edoce.edoce_b.exceptions.UserIsRegisteredException;
import com.edoce.edoce_b.security.CustomUserDetails;
import com.edoce.edoce_b.security.JwtService;
import com.edoce.edoce_b.utils.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements Validators {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request){
        if(!isValidEmail(request.email())){
            throw new InvalidFieldsException("Informe um Email válido.");//400
        }
        if(!isValidPassword(request.password())){
            throw new InvalidFieldsException("A senha deve seguir os padrões solicitados");//400
        }
        if (userRepository.existsByEmail(request.email())){
            throw new UserIsRegisteredException("Usuário já registrado.");//409
        }
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return new RegisterResponse(true, "Usuário registrado com sucesso");
    }
    public LoginResponse login(LoginRequest request){
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidEmailOrPasswordException("Email ou senha inválidos");//403
        }

        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new LoginResponse(true, "Login realizado com sucesso", jwtService.generateToken(user));
    }
}
