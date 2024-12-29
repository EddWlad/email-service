package com.tidsec.mail_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name= "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateCreate = new Date();

    @NotBlank(message = "La identificación es requerida")
    @Size(min = 10, max = 13, message = "La identificación debe tener entre 10 y 13 dígitos")
    @Column(unique= true)
    private String identification;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank(message = "El correo electrónico no debe estar vacío")
    @Email(message = "Correo electrónico no válido")
    @Column(unique= true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private Integer status;

    @PrePersist
    protected void onCreate() {
        if (dateCreate == null) {
            dateCreate = new Date();
        }
    }
}