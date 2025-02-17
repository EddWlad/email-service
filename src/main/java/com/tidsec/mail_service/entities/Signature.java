package com.tidsec.mail_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name= "signature")
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = true, length = 255)
    private String photoUrl;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 100)
    private String positionCompany;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 100)
    private String phone;

    @NotBlank(message = "El correo electrónico no debe estar vacío")
    @Email(message = "Correo electrónico no válido")
    @Column(unique= true)
    private String email;

    @NotBlank
    @Column(unique= true)
    @Size(min = 3, max = 200)
    private String addressCompany;

    /*@OneToMany (mappedBy = "signature", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Mail> mails = new ArrayList<Mail>();*/

    @Column(nullable = false)
    private Integer status = 1;
}
