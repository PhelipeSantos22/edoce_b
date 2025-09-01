package com.edoce.edoce_b.dto.response;

public record LoginResponse (
        boolean status,
        String message,
        String token
){}
