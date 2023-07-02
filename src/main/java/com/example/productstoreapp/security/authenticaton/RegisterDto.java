package com.example.productstoreapp.security.authenticaton;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "RegisterDto Model Information")
public class RegisterDto {
    @Schema(description = "SignUp User Full Name")
    private String name;
    @Schema(description = "SignUp Username")
    private String username;
    @Schema(description = "SignUp User Email")
    private String email;
    private String password;
}
