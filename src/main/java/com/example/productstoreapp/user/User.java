package com.example.productstoreapp.user;

import com.example.productstoreapp.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotBlank(message = "Name cannot be left blank")
    private String name;
    @Column(nullable = false,  unique = true)
    @NotBlank(message = "Username should not be left blank!")
    @Size(min = 4, message = "Username should be at least 4 symbols")
    private String username;
    @Column(nullable = false, unique = true)
    @Email(message = "Should be an email")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Password should not be left blank!")
    @Size(min = 8, message = "Password should be at least 8 symbols")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id") )
    private Set<Role> roles =  new HashSet<>();
}
