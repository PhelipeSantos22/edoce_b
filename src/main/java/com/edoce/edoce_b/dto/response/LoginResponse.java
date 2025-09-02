package com.edoce.edoce_b.dto.response;

public record LoginResponse (
        boolean success,
        String message,
        String token
){}
