package com.tidsec.mail_service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name= "user")
public class User {
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreate = LocalDateTime.now();

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

    @Column(nullable = false, length = 60, unique = true)
    private String username;

    @NotBlank(message = "El correo electrónico no debe estar vacío")
    @Email(message = "Correo electrónico no válido")
    @Column(unique= true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "user_role",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private List<Role> roles;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private Integer status = 1;

}
