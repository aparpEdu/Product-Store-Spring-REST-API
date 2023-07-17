package com.example.productstoreapp.security.authenticaton.token;

import com.example.productstoreapp.exception.ProductStoreAPIException;
import com.example.productstoreapp.user.UserRepository;
import com.example.productstoreapp.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public String confirmToken(String token) {
        userRepository.confirmEmail(verifyToken(token).getUser().getEmail());
        return "Successfully confirmed";
    }

    @Override
    public ConfirmationToken confirmResetToken(String token) {
        return verifyToken(token);
    }

    private ConfirmationToken verifyToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ProductStoreAPIException(HttpStatus.NOT_FOUND, ErrorMessages.TOKEN_NOT_FOUND));
        if(confirmationToken.getConfirmedAt() != null){
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, ErrorMessages.EMAIL_ALREADY_CONFIRMED);
        }
        if(confirmationToken.getExpiresAt().isBefore((LocalDateTime.now()))){
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, ErrorMessages.EXPIRED_TOKEN);
        }
        setConfirmationDate(token);
        return  confirmationToken;
    }

    @Override
    public void setConfirmationDate(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
