package com.example.productstoreapp.security.authenticaton;


import com.example.productstoreapp.exception.ProductStoreAPIException;
import com.example.productstoreapp.exception.ResourceNotFoundException;
import com.example.productstoreapp.role.Role;
import com.example.productstoreapp.role.RoleRepository;
import com.example.productstoreapp.security.jwt.JwtTokenProvider;
import com.example.productstoreapp.user.User;
import com.example.productstoreapp.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements  AuthService{
   private final AuthenticationManager authenticationManager;
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
    Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if(userRepository.existsByUsername(registerDto.getEmail())){
            throw new ProductStoreAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
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
        return "Registration was successful!";
    }
}
