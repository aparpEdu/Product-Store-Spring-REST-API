package com.example.productstoreapp.security.authenticaton.token;

import java.util.UUID;

public class ConfirmationTokenGenerator {
    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
