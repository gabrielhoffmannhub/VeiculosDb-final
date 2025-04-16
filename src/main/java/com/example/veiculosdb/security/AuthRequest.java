package com.example.veiculosdb.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
