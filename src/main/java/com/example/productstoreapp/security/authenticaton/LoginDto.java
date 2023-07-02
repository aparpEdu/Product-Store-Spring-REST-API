package com.example.productstoreapp.security.authenticaton;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "LoginDto Model Information")
public class LoginDto {
    @Schema(description = "SignIn username or email")
    private String usernameOrEmail;
    private String password;
}
