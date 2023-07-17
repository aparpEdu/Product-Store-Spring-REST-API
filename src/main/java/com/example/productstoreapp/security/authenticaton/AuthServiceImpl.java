package com.example.productstoreapp.security.authenticaton;


import com.example.productstoreapp.email.EmailBuilder;
import com.example.productstoreapp.email.EmailService;
import com.example.productstoreapp.exception.ProductStoreAPIException;
import com.example.productstoreapp.exception.ResourceNotFoundException;
import com.example.productstoreapp.role.Role;
import com.example.productstoreapp.role.RoleRepository;
import com.example.productstoreapp.security.authenticaton.token.ConfirmationToken;
import com.example.productstoreapp.security.authenticaton.token.ConfirmationTokenGenerator;
import com.example.productstoreapp.security.authenticaton.token.ConfirmationTokenService;
import com.example.productstoreapp.security.jwt.JwtTokenProvider;
import com.example.productstoreapp.user.User;
import com.example.productstoreapp.user.UserRepository;
import com.example.productstoreapp.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements  AuthService{
   private final AuthenticationManager authenticationManager;
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtTokenProvider tokenProvider;
   private final ConfirmationTokenService confirmationTokenService;
   private final EmailService emailService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           JwtTokenProvider tokenProvider,
                           ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    @Override
    public String login(LoginDto loginDto) {
        User userToVerify = userRepository
                .findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail())
                .orElseThrow(() -> new ProductStoreAPIException(HttpStatus.NOT_FOUND, ErrorMessages.NON_EXISTENT_USER));
        if(!userToVerify.isConfirmed()){
            throw new ProductStoreAPIException(HttpStatus.UNAUTHORIZED, ErrorMessages.NOT_CONFIRMED_EMAIL);
        }
         Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
         SecurityContextHolder.getContext().setAuthentication(authentication);
         return tokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        if(userRepository.existsByUsername(registerDto.getEmail())){
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->  new ResourceNotFoundException("Role", "Name", "ROLE_USER"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setUser(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String confirmationLink = "http://localhost:8080/api/v1/auth/confirm?token=" + token;
        emailService.send(registerDto.getEmail(), EmailBuilder.buildEmailConfirmation(registerDto.getName(), confirmationLink));
        return "User registered successfully! DEV TOKEN: "+ token;
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto, Long userId) {
        User userWithNewPassword = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userWithNewPassword.getUsername(), changePasswordDto.getOldPassword()));

        if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatedNewPassword()))
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, ErrorMessages.NEW_PASSWORD_NO_MATCH);

        userWithNewPassword.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(userWithNewPassword);
        emailService.send(userWithNewPassword.getEmail(), EmailBuilder.buildEmailChangePassword(userWithNewPassword.getName()));
        return "Password changed successfully";
    }

    @Override
    public String forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        User userToResetPassword = userRepository
                .findByUsernameOrEmail(forgotPasswordDto.getUsernameOrEmail(), forgotPasswordDto.getUsernameOrEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "usernameOrEmail", forgotPasswordDto.getUsernameOrEmail()));
        String userEmail = userToResetPassword.getEmail();
        String token =  ConfirmationTokenGenerator.generateToken();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setUser(userToResetPassword);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String confirmationLink = "http://localhost:8080/api/v1/auth/reset?token=" + token;
        emailService.send(userEmail, EmailBuilder.buildEmailForgotPassword(userToResetPassword.getName(), confirmationLink));
        return "Email was sent with additional steps to reset your password! DEV TOKEN:" + token;
    }

    @Override
    public String resetPassword(ResetPasswordDto resetPasswordDto, String token) {
        User userToResetPassword =  confirmationTokenService.confirmResetToken(token).getUser();
        if(!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, ErrorMessages.NEW_PASSWORD_NO_MATCH);
        }
        userToResetPassword.setPassword(passwordEncoder.encode(resetPasswordDto.getConfirmPassword()));
        userRepository.save(userToResetPassword);
        return "Password changed successfully";
    }
}
