package com.edoce.edoce_b.exceptions;

import com.edoce.edoce_b.dto.response.LoginResponse;
import com.edoce.edoce_b.dto.response.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExecptionHandler {
    @ExceptionHandler(UserIsRegisteredException.class)
    public ResponseEntity<RegisterResponse> handleUserIsRegistered(UserIsRegisteredException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new RegisterResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<RegisterResponse> handleInvalidFilds(InvalidFieldsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(InvalidEmailOrPasswordException.class)
    public ResponseEntity<LoginResponse> handleInvalidEmailOrPassword(InvalidEmailOrPasswordException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, ex.getMessage(),""));
    }
}
