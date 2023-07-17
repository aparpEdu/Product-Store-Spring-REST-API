package com.example.productstoreapp.security.authenticaton.token;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    String confirmToken(String token);

    ConfirmationToken confirmResetToken(String token);
    void setConfirmationDate(String token);
}
