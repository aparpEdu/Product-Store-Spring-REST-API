package com.example.productstoreapp.security.authenticaton;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    String changePassword(ChangePasswordDto changePasswordDto, Long userId);

    String forgotPassword(ForgotPasswordDto forgotPasswordDto);

    String resetPassword(ResetPasswordDto resetPasswordDto, String token);
}
