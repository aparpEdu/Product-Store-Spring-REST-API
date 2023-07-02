package com.example.productstoreapp.security.authenticaton;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
