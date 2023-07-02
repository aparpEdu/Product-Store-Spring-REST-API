package com.example.productstoreapp.security.jwt;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
