package com.example.productstoreapp.security.authenticaton;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    @Size(min = 8, message = "password should be at least 8 symbols long")
    private String newPassword;
    private String confirmPassword;
}
