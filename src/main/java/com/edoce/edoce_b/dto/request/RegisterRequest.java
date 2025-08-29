package com.edoce.edoce_b.dto.request;

public record RegisterRequest (
        String name,
        String email,
        String password
){}
