package com.tidsec.mail_service.entities;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name= "role")
public class Role {
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer status = 1;
}
