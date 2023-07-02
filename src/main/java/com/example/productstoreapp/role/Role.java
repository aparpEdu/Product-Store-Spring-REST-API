package com.example.productstoreapp.role;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @NotBlank(message = "Role should not be blank")
    @Size(min = 2, message = "Role should be at least 2 symbols long")
    private String name;
}
